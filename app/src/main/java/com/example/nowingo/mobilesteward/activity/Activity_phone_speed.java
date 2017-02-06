package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.PhoneSpeedListViewAdapter;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.RuningAppInfo;
import com.example.nowingo.mobilesteward.manager.AppInfoManager;
import com.example.nowingo.mobilesteward.manager.MemoryManager;
import com.example.nowingo.mobilesteward.manager.PhoneManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_phone_speed extends BaseActivity {
    TextView textView_phonename,textView_phonemodename,textView_phonesystemversion,textView_space;
    ProgressBar progressBar_space,progressBar_pro;
    Button button_clean,button_show;
    CheckBox checkBox;
    ListView listView_appinfo;

    ArrayList<RuningAppInfo> arrayList;

    PhoneSpeedListViewAdapter phoneSpeedListViewAdapter;

        View top;
        String title;

    Boolean is = true;
    Boolean ok = false;

    MyReciver mr;//自己的自定义广播接收器
        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mr);
    }

    @Override
        public void setLayout() {
            setContentView(R.layout.activity_phone_speed);
        }

        @Override
        public void inidata() {
            Intent intent =getIntent();
            title = intent.getStringExtra("name");
//            loadData(1);

//            AppInfoManager appInfoManager = AppInfoManager.getAppInfoManager(this);
//            arrayList = (ArrayList<RuningAppInfo>) appInfoManager.getRuningAppInfos().get(1);
        }

        @Override
        public void iniview() {
            top = findViewById(R.id.top_phone_speed);
            textView_phonename = (TextView) findViewById(R.id.activity_phone_speed_phonename);
            textView_phonemodename = (TextView) findViewById(R.id.activity_phone_speed_phonemodename);
            textView_phonesystemversion = (TextView) findViewById(R.id.activity_phone_speed_phonesystemversion);
            textView_space = (TextView) findViewById(R.id.activity_phone_speed_tv_space);
            progressBar_space = (ProgressBar) findViewById(R.id.activity_phone_speed_pro_space);
            button_clean = (Button) findViewById(R.id.activity_phone_speed_btn_clean);
            button_show = (Button) findViewById(R.id.activity_phone_speed_btn_show);
            checkBox = (CheckBox) findViewById(R.id.activity_phone_speed_checkbox);
            listView_appinfo = (ListView) findViewById(R.id.activity_phone_speed_lv_processesmessage);
            progressBar_pro = (ProgressBar) findViewById(R.id.activity_phone_speed_pro_list);

        }

        @Override
        public void setview() {

            //在Oncreate里面注册
            mr=new MyReciver();
            //绑定意图过滤器
            IntentFilter intentFilter=new IntentFilter();
            intentFilter.addAction("nsw");
            //执行注册
            registerReceiver(mr,intentFilter);

            ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);

            button_show.setText("显示系统进程");

            PhoneManager phoneManager = PhoneManager.getPhoneManage(this);
            textView_phonename.setText(phoneManager.getPhoneName1());
            textView_phonemodename.setText(phoneManager.getPhoneModelName());
            textView_phonesystemversion.setText("Android"+phoneManager.getPhoneSystemVersion());

            updateProgressBar();

            updateListView(1);
//            phoneSpeedListViewAdapter = new PhoneSpeedListViewAdapter(this,arrayList);
//            listView_appinfo.setAdapter(phoneSpeedListViewAdapter);


            button_show.setOnClickListener(new View.OnClickListener() {
                AppInfoManager appInfoManager = AppInfoManager.getAppInfoManager(Activity_phone_speed.this);
                @Override
                public void onClick(View v) {
                    if (is){
                        is = false;
                        button_show.setText("只显示用户进程");
//                        loadData(0);
                        updateListView(0);
//                        arrayList = (ArrayList<RuningAppInfo>) appInfoManager.getRuningAppInfos().get(0);
//                        phoneSpeedListViewAdapter = new PhoneSpeedListViewAdapter(Activity_phone_speed.this,arrayList);
//                        listView_appinfo.setAdapter(phoneSpeedListViewAdapter);
                    }else {
                        is = true;
                        button_show.setText("显示系统进程");
//                        loadData(1);
                        updateListView(1);
//                        arrayList = (ArrayList<RuningAppInfo>) appInfoManager.getRuningAppInfos().get(1);
//                        phoneSpeedListViewAdapter = new PhoneSpeedListViewAdapter(Activity_phone_speed.this,arrayList);
//                        listView_appinfo.setAdapter(phoneSpeedListViewAdapter);
                    }
                }
            });

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (ok == false){
                        phoneSpeedListViewAdapter.checkall(isChecked);
                        phoneSpeedListViewAdapter.notifyDataSetChanged();
                    }

                }
            });

            button_clean.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean flag2 = true;
                    for (RuningAppInfo appInfos : arrayList){
                        if (appInfos.isClear()){
                            String packname = appInfos.getPackageName();
                            AppInfoManager.getAppInfoManager(Activity_phone_speed.this).killProcesses(packname);
//                            loadData(1);
                            flag2 = false;
                        }
                    }
                    if (flag2 == false) {
                        if (is == true) {
                            updateListView(1);
                            checkBox.setChecked(false);
                        } else {
                            updateListView(0);
                            checkBox.setChecked(false);
                        }
                        phoneSpeedListViewAdapter.checkall(false);
                        updateProgressBar();
                    }else {
                        Toast.makeText(Activity_phone_speed.this,"没有选择",Toast.LENGTH_SHORT).show();
                    }
                }
            });



        }
//    public void loadData(int a){
//
//        AppInfoManager appInfoManager = AppInfoManager.getAppInfoManager(Activity_phone_speed.this);
//        arrayList = (ArrayList<RuningAppInfo>) appInfoManager.getRuningAppInfos().get(a);
//        phoneSpeedListViewAdapter = new PhoneSpeedListViewAdapter(Activity_phone_speed.this,arrayList);
//        listView_appinfo.setAdapter(phoneSpeedListViewAdapter);
//    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    arrayList = (ArrayList<RuningAppInfo>) msg.obj;
                    phoneSpeedListViewAdapter = new PhoneSpeedListViewAdapter(Activity_phone_speed.this,arrayList);
                    listView_appinfo.setAdapter(phoneSpeedListViewAdapter);
                    progressBar_pro.setVisibility(View.GONE);
                    break;
                case 1:
                    Bundle bundle = new Bundle();
                    bundle = msg.getData();
                    long all = bundle.getLong("all");
                    long free = bundle.getLong("free");
                    textView_space.setText("已用内存："+ CommonUtil.getFileSize(all-free)+"/"+CommonUtil.getFileSize(all));
                    progressBar_space.setProgress((int) (((all-free)*100)/all));
                    break;
            }
        }
    };

    public void updateListView(final int b){
        new Thread(new Runnable() {
            ArrayList<RuningAppInfo> appInfos = new ArrayList<RuningAppInfo>();
            @Override
            public void run() {
                AppInfoManager appInfoManager = AppInfoManager.getAppInfoManager(Activity_phone_speed.this);
                appInfos = (ArrayList<RuningAppInfo>) appInfoManager.getRuningAppInfos().get(b);
                Message msg = new Message();
                msg.what = 0;
                msg.obj = appInfos;
                handler.sendMessage(msg);
            }
        }).start();
    }

    public void updateProgressBar(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                long all = MemoryManager.getPhoneTotalRamMemory();
                long free = MemoryManager.getPhoneFreeRamMemory(Activity_phone_speed.this);
                Bundle bundle = new Bundle();
                bundle.putLong("all",all);
                bundle.putLong("free",free);
                Message msg = new Message();
                msg.what = 1;
                msg.setData(bundle);
                handler.sendMessage(msg);
            }
        }).start();
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
                    checkBox.setChecked(id);
                    ok = false;
                }
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        updateProgressBar();
        updateListView(1);
    }
}


