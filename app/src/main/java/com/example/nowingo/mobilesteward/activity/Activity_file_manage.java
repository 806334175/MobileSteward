package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.FileMessageListViewAdapter;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.FileInfo;
import com.example.nowingo.mobilesteward.manager.FileManager2;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;
import com.example.nowingo.mobilesteward.tools.FileTypeUtil;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_file_manage extends BaseActivity implements View.OnClickListener {
    View top;
    String title;
    TextView textView_tv;
    RelativeLayout relativeLayout_all,relativeLayout_file,relativeLayout_video,relativeLayout_vioce,relativeLayout_picture,relativeLayout_RAR,relativeLayout_program;
    TextView textView_allnumber,textView_filenumber,textView_videonumber,textView_viocenumber,textView_picture,textView_RARnumber,textView_programnumber;
    ProgressBar progressBar_all,progressBar_file,progressBar_video,progressBar_vioce,progressBar_picture,progressBar_RAR,progressBar_apk;

    FileManager2 fm;

    private long anyFileSize; // 任意文件总大小(在搜索过程中，实时迭加，计算总大小)

    private long txtFileSize; // 文档文件总大小(同上)

    private long videoFileSize; // 视频文件总大小(同上)

    private long audioFileSize; // 音乐文件总大小(同上)

    private long imageFileSize; // 图像文件总大小(同上)

    private long zipFileSize; // ZIP文件总大小(同上)

    private long apkFileSize; // APK文件总大小(同上)




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        anyFileSize=fm.getAnyFileSize();

        txtFileSize = fm.getTxtFileSize();

        videoFileSize = fm.getVideoFileSize();

        audioFileSize = fm.getAudioFileSize();

        imageFileSize = fm.getImageFileSize();

        zipFileSize = fm.getZipFileSize();

        apkFileSize = fm.getApkFileSize();

        textView_tv.setText(CommonUtil.getFileSize(anyFileSize));
        textView_allnumber.setText(CommonUtil.getFileSize(anyFileSize));
        textView_filenumber.setText(CommonUtil.getFileSize(txtFileSize));
        textView_videonumber.setText(CommonUtil.getFileSize(videoFileSize));
        textView_viocenumber.setText(CommonUtil.getFileSize(audioFileSize));
        textView_picture.setText(CommonUtil.getFileSize(imageFileSize));
        textView_RARnumber.setText(CommonUtil.getFileSize(zipFileSize));
        textView_programnumber.setText(CommonUtil.getFileSize(apkFileSize));

        FileMessageListViewAdapter fileMessageListViewAdapter = new FileMessageListViewAdapter(this);
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_file_manager);
    }

    @Override
    public void inidata() {
        Intent intent =getIntent();
        title = intent.getStringExtra("name");
    }

    @Override
    public void iniview() {
        top = findViewById(R.id.top_file_manage);

        textView_tv = (TextView) findViewById(R.id.activity_file_manager_tv);

        relativeLayout_all = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_all);
        relativeLayout_file = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_file);
        relativeLayout_video = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_video);
        relativeLayout_vioce = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_vioce);
        relativeLayout_picture = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_picture);
        relativeLayout_RAR = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_RAR);
        relativeLayout_program = (RelativeLayout) findViewById(R.id.file_manager_listview_item_rl_program);

        textView_allnumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_allnumber);
        textView_filenumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_filenumber);
        textView_videonumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_videonumber);
        textView_viocenumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_voicenumber);
        textView_picture = (TextView) findViewById(R.id.file_manager_listview_item_tv_picturenumber);
        textView_RARnumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_RARnumber);
        textView_programnumber = (TextView) findViewById(R.id.file_manager_listview_item_tv_programnumber);

        progressBar_all = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_all);
        progressBar_file = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_file);
        progressBar_video = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_video);
        progressBar_vioce = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_vioce);
        progressBar_picture = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_picture);
        progressBar_RAR = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_RAR);
        progressBar_apk = (ProgressBar) findViewById(R.id.file_manager_listview_item_pro_apk);
    }


    @Override
    public void setview() {
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);
        updateListView();
//        FileManager2 fileManager2 = FileManager2.getFileManager();
//        fileManager2.searchINSDCardFile();
//        fileManager2.searchOUTSDCardFile();
//        fileManager2.searchSDCardFile();
//
//        textView_tv.setText(CommonUtil.getFileSize(fileManager2.getAnyFileSize())+"");
//
//        textView_allnumber.setText(CommonUtil.getFileSize(fileManager2.getAnyFileSize())+"");
//        textView_filenumber.setText(CommonUtil.getFileSize(fileManager2.getTxtFileSize())+"");
//        textView_videonumber.setText(CommonUtil.getFileSize(fileManager2.getVideoFileSize())+"");
//        textView_viocenumber.setText(CommonUtil.getFileSize(fileManager2.getAudioFileSize())+"");
//        textView_picture.setText(CommonUtil.getFileSize(fileManager2.getImageFileSize())+"");
//        textView_RARnumber.setText(CommonUtil.getFileSize(fileManager2.getZipFileSize())+"");
//        textView_programnumber.setText(CommonUtil.getFileSize(fileManager2.getApkFileSize())+"");



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.file_manager_listview_item_rl_all:
                Bundle bundle1 = new Bundle();
                bundle1.putString("title","全部");
                startActivity(Activity_file_message.class,bundle1);
                break;
            case R.id.file_manager_listview_item_rl_file:
                Bundle bundle2 = new Bundle();
                bundle2.putString("title","文档");
                startActivity(Activity_file_message.class,bundle2);
                break;
            case R.id.file_manager_listview_item_rl_video:
                Bundle bundle3 = new Bundle();
                bundle3.putString("title","视频");
                startActivity(Activity_file_message.class,bundle3);
                break;
            case R.id.file_manager_listview_item_rl_vioce:
                Bundle bundle4 = new Bundle();
                bundle4.putString("title","音频");
                startActivity(Activity_file_message.class,bundle4);
                break;
            case R.id.file_manager_listview_item_rl_picture:
                Bundle bundle5 = new Bundle();
                bundle5.putString("title","图像");
                startActivity(Activity_file_message.class,bundle5);
                break;
            case R.id.file_manager_listview_item_rl_RAR:
                Bundle bundle6 = new Bundle();
                bundle6.putString("title","压缩包");
                startActivity(Activity_file_message.class,bundle6);
                break;
            case R.id.file_manager_listview_item_rl_program:
                Bundle bundle7 = new Bundle();
                bundle7.putString("title","程序包");
                startActivity(Activity_file_message.class,bundle7);
                break;
        }
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateListView();
//    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            textView_tv.setText(CommonUtil.getFileSize(anyFileSize));
            textView_allnumber.setText(CommonUtil.getFileSize(anyFileSize));
            switch (msg.what){
                case 0:
                    textView_filenumber.setText(CommonUtil.getFileSize(txtFileSize));
                    break;
                case 1:
                    textView_videonumber.setText(CommonUtil.getFileSize(videoFileSize));
                    break;
                case 2:
                    textView_viocenumber.setText(CommonUtil.getFileSize(audioFileSize));
                    break;
                case 3:
                    textView_picture.setText(CommonUtil.getFileSize(imageFileSize));
                    break;
                case 4:
                    textView_RARnumber.setText(CommonUtil.getFileSize(zipFileSize));
                    break;
                case 5:
                    textView_programnumber.setText(CommonUtil.getFileSize(apkFileSize));
                    break;
                case 6:

                    anyFileSize=fm.getAnyFileSize();

                    txtFileSize = fm.getTxtFileSize();

                    videoFileSize = fm.getVideoFileSize();

                    audioFileSize = fm.getAudioFileSize();

                    imageFileSize = fm.getImageFileSize();

                    zipFileSize = fm.getZipFileSize();

                    apkFileSize = fm.getApkFileSize();

                    textView_tv.setText(CommonUtil.getFileSize(anyFileSize));
                    textView_allnumber.setText(CommonUtil.getFileSize(anyFileSize));
                    textView_filenumber.setText(CommonUtil.getFileSize(txtFileSize));
                    textView_videonumber.setText(CommonUtil.getFileSize(videoFileSize));
                    textView_viocenumber.setText(CommonUtil.getFileSize(audioFileSize));
                    textView_picture.setText(CommonUtil.getFileSize(imageFileSize));
                    textView_RARnumber.setText(CommonUtil.getFileSize(zipFileSize));
                    textView_programnumber.setText(CommonUtil.getFileSize(apkFileSize));

                    progressBar_all.setVisibility(View.GONE);
                    progressBar_file.setVisibility(View.GONE);
                    progressBar_video.setVisibility(View.GONE);
                    progressBar_vioce.setVisibility(View.GONE);
                    progressBar_picture.setVisibility(View.GONE);
                    progressBar_RAR.setVisibility(View.GONE);
                    progressBar_apk.setVisibility(View.GONE);

                    relativeLayout_all.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_file.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_video.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_vioce.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_picture.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_RAR.setOnClickListener(Activity_file_manage.this);
                    relativeLayout_program.setOnClickListener(Activity_file_manage.this);
                    break;
            }
        }
    };

    private FileManager2.SearchFileListener searchFileListener=new FileManager2.SearchFileListener() {
        @Override
        public void searching(String typeName) {
            //搜索中
            anyFileSize=fm.getAnyFileSize();
//            Log.i("msg",anyFileSize+"");
            Message msg = new Message();
            if (typeName.equals(FileTypeUtil.TYPE_TXT)){
                txtFileSize = fm.getTxtFileSize();
                msg.what = 0;
            }else if (typeName.equals(FileTypeUtil.TYPE_VIDEO)){
                videoFileSize = fm.getVideoFileSize();
                msg.what = 1;
            }else if (typeName.equals(FileTypeUtil.TYPE_AUDIO)){
                audioFileSize = fm.getAudioFileSize();
                msg.what = 2;
            }else if (typeName.equals(FileTypeUtil.TYPE_IMAGE)){
                imageFileSize = fm.getImageFileSize();
                msg.what = 3;
            }else if (typeName.equals(FileTypeUtil.TYPE_ZIP)){
                zipFileSize = fm.getZipFileSize();
                msg.what = 4;
            }else if (typeName.equals(FileTypeUtil.TYPE_APK)){
                apkFileSize = fm.getApkFileSize();
                msg.what = 5;
            }
            handler.sendMessage(msg);
        }

        @Override
        public void end(boolean isExceptionEnd) {
            //搜索结束
                Message ms=new Message();
                ms.what=6;
                handler.sendMessage(ms);
        }
    };

    public void updateListView(){
        fm = FileManager2.getFileManager();
        fm.setSearchFileListener(searchFileListener);//回调
        new Thread(new Runnable() {
            @Override
            public void run() {
                fm.searchSDCardFile();
//                fm.searchOUTSDCardFile();//获取外置sdcard存储数据
//                fm.searchINSDCardFile();//获取内置sdcard存储数据
            }
        }).start();

    }

}
