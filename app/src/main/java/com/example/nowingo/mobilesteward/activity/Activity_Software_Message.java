package com.example.nowingo.mobilesteward.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.SoftwareMessageAdapter;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.AppInfo;
import com.example.nowingo.mobilesteward.manager.AppInfoManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;

import java.util.List;

/**
 * Created by NowINGo on 2016/11/30.
 */
public class Activity_Software_Message extends BaseActivity {
    View top;
    ListView listView;
    CheckBox checkBox_all;
    Button button_delete;
    ProgressBar pro;

    String title;
    List<AppInfo> list;
    AppInfoManager appInfoManager;
    SoftwareMessageAdapter softwareMessageAdapter;

    Boolean ok = false;
    MyReciver mr;//自己的自定义广播接收器
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_software_message);
    }

    @Override
    public void inidata() {
        Intent intent =getIntent();
        title = intent.getStringExtra("title");
    }

    @Override
    public void iniview() {
        top = findViewById(R.id.activity_software_message_top);
        listView = (ListView) findViewById(R.id.activity_software_message_listview);
        checkBox_all = (CheckBox) findViewById(R.id.activity_software_message_checkall);
        button_delete = (Button) findViewById(R.id.activity_software_message_btn_delete);
        pro = (ProgressBar) findViewById(R.id.activity_software_message_pro_list);
    }
    private AppDelRecevice adr;
    @Override
    public void setview() {
        //在Oncreate里面注册
        mr=new MyReciver();
        //绑定意图过滤器
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("nsw");
        //执行注册
        registerReceiver(mr,intentFilter);

        //注册广播
        adr = new AppDelRecevice();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        filter.addDataScheme("package");
        filter.addAction(AppDelRecevice.ACTION_APPDEL);
        registerReceiver(adr, filter);

        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);

        checkBox_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ok == false){
                    softwareMessageAdapter.checkall(isChecked);
                    softwareMessageAdapter.notifyDataSetChanged();
                }
            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AppInfo a:list) {
                    if (a.isDel()){
                        Uri u = Uri.parse("package:"+a.getPackageInfo().packageName);
                        Intent intent = new Intent(Intent.ACTION_DELETE,u);
                        startActivity(intent);
                    }
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时
                        softwareMessageAdapter.setFling(true);
                        //softwareMessageAdapter.setFling2(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
                        softwareMessageAdapter.setFling(false);
                        //softwareMessageAdapter.setFling2(false);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时
                        softwareMessageAdapter.setFling(false);
                        //softwareMessageAdapter.setFling2(true);
                        softwareMessageAdapter.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateListView(title);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateListView(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(adr);
        unregisterReceiver(mr);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            List<AppInfo> list;
            switch (msg.what){
                case 0:
                    list = (List<AppInfo>) msg.obj;
                    softwareMessageAdapter = new SoftwareMessageAdapter(Activity_Software_Message.this,list);
                    listView.setAdapter(softwareMessageAdapter);
                    pro.setVisibility(View.GONE);
                    break;
            }
        }
    };

    public void updateListView(final String title){
        new Thread(new Runnable() {
            @Override
            public void run() {
                appInfoManager = AppInfoManager.getAppInfoManager(Activity_Software_Message.this);
                switch (title){
                    case "所有软件":
                        list = appInfoManager.getAllPackageInfo(true);
                        break;
                    case "系统软件":
                        list = appInfoManager.getSystemPackageInfo(true);
                        break;
                    case "用户软件":
                        list = appInfoManager.getUserPackageInfo(true);
                        break;
                }
                Message msg = new Message();
                msg.what = 0;
                msg.obj = list;
                handler.sendMessage(msg);
            }
        }).start();
    }


    /** 应用删除广播接收器 */
    public class AppDelRecevice extends BroadcastReceiver {
        public static final String ACTION_APPDEL = "com.androidy.app.phone.del";

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_PACKAGE_REMOVED) || action.equals(ACTION_APPDEL)) {
                updateListView(title);
            }
        }
    }

    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ok = true;
            //是你响应了这个广播 要执行操作
            String Action=intent.getAction();//获得Action
            if(Action.equals("nsw")){//判断Action
                Bundle bundle=intent.getExtras();
                if (bundle!=null){
                    boolean id=bundle.getBoolean("id");
                    checkBox_all.setChecked(id);
                    ok = false;
                }
            }
        }
    }

}
