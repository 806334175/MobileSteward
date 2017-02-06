package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.ClearAdapter;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.RubbishFileInfo;
import com.example.nowingo.mobilesteward.manager.DbClearPathManager;
import com.example.nowingo.mobilesteward.manager.FileManager;
import com.example.nowingo.mobilesteward.manager.FileManager2;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_junk_clean extends BaseActivity {
    View top;
    String title;

    RelativeLayout loading_rl;//加载中的动画布局
    ListView lv;//下方列表布局
    TextView total_tv;//顶部总大小的文本框
    Button clear_btn;//清理的按钮
    ClearAdapter ca;//适配器
    long totalsize;//垃圾总大小

    ArrayList<RubbishFileInfo> rubbishFileInfos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inidata();
        setLayout();
        iniview();
        setview();
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_junk_clean);
    }

    @Override
    public void inidata() {
        Intent intent =getIntent();
        title = intent.getStringExtra("name");

        //        rubbishFileInfos=new ArrayList<RubbishFileInfo>();
        try {
            //先创建和复制文件
            DbClearPathManager.createFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据源
        rubbishFileInfos = DbClearPathManager.getPhoneRubbishfile(this);
    }
    public void loadData() throws IOException {


        totalsize=0;
        loading_rl.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (RubbishFileInfo rubbishFileInfo : rubbishFileInfos) {
                    File file = new File(rubbishFileInfo.getFilepath());
                    long size = FileManager2.getFileSize(file);
                    rubbishFileInfo.setSize(size);
                    // 更新全部大小
                    Message msg = handler.obtainMessage();//取出默认的消息
                    //等同于
//                    Message ms=new Message();
                    msg.what = 1;
                    msg.obj = size;
                    handler.sendMessage(msg);
                }
                // 全部加载完毕 更新UI
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = rubbishFileInfos;
                handler.sendMessage(msg);
            }
        }).start();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    long size = (Long) msg.obj;
                    Log.i("nihao",size+"");
                    totalsize += size;
                    total_tv.setText(CommonUtil.getFileSize(totalsize));//设置上方状态
                    break;
                case 2:
                    total_tv.setText(CommonUtil.getFileSize(totalsize));//设置上方状态
                    rubbishFileInfos = (ArrayList<RubbishFileInfo>) msg.obj;
                    ArrayList<RubbishFileInfo> lists=new ArrayList<RubbishFileInfo>();
                    for(int i=0;i<rubbishFileInfos.size();i++){
                        RubbishFileInfo rubbishFileInfo=rubbishFileInfos.get(i);
                        Log.i("size",rubbishFileInfo.getSize()+"");
                        if(rubbishFileInfo.getSize()>0){
                            lists.add(rubbishFileInfo);
                        }
                    }
                    loading_rl.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    ca.setList(lists);
                    ca.notifyDataSetChanged();
                    //快速滑动监听
                    lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                            switch (scrollState){
                                case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时
                                    ca.setFiling(true);
                                    break;
                                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
                                    ca.setFiling(false);
                                    break;
                                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时
                                    ca.setFiling(false);
                                    ca.notifyDataSetChanged();
                                    break;
                            }
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        }
                    });
                    break;
            }
        }
    };
    @Override
    public void iniview() {
        top = findViewById(R.id.top_junk_clean);

        loading_rl= (RelativeLayout) findViewById(R.id.activity_junk_clean_loading);
        lv= (ListView) findViewById(R.id.activity_junk_clean_lv);
        total_tv= (TextView) findViewById(R.id.activity_junk_clean_size);
        clear_btn= (Button) findViewById(R.id.activity_junk_clean_btn);
    }

    @Override
    public void setview() {
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);

        ca = new ClearAdapter(Activity_junk_clean.this);
        lv.setAdapter(ca);
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RubbishFileInfo> list=ca.getList();
                for (int i=0;i<list.size();i++){
                    RubbishFileInfo rubbishFileInfo=list.get(i);
                    File file=new File(rubbishFileInfo.getFilepath());
                    Log.i("file",file.getPath());
//
                    deltefile(file);
                }

                try {
                    //重新加载数据
                    loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    public void deltefile(File file){
        if(file.isFile()){
            file.delete();
        }else if(file.isDirectory()){
            File[] files=file.listFiles();
            for (int i=0;i<files.length;i++){
                if(files[i]!=null){
                    deltefile(files[i]);
                }
            }
        }
    }
}
