package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.manager.MemoryManager;
import com.example.nowingo.mobilesteward.manager.PhoneManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_phone_delection extends BaseActivity {
    View top;
    String title;

    TextView textView_phonename,textView_phoneversion,textView_allspace,
            textView_lastspace,textView_cpuname,textView_cpunumber,textView_phoneresolution,
            textView_cameraresolution,textView_basebandversion,textView_isroot;

    ProgressBar progressBar;
    TextView textView_electric;
    LinearLayout ll;
    MyReciver mr;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_phone_delection);
    }

    @Override
    public void inidata() {

        Intent intent =getIntent();
        title = intent.getStringExtra("name");
        mr = new MyReciver();
        IntentFilter inf = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mr,inf);
    }

    @Override
    public void iniview() {
        top = findViewById(R.id.top_phone_delection);
        textView_phonename = (TextView) findViewById(R.id.activity_phone_delection_phonename);
        textView_phoneversion = (TextView) findViewById(R.id.activity_phone_delection_phoneversion);
        textView_allspace = (TextView) findViewById(R.id.activity_phone_delection_allspace);
        textView_lastspace = (TextView) findViewById(R.id.activity_phone_delection_lastspace);
        textView_cpuname = (TextView) findViewById(R.id.activity_phone_delection_cpuname);
        textView_cpunumber = (TextView) findViewById(R.id.activity_phone_delection_cpunumber);
        textView_phoneresolution = (TextView) findViewById(R.id.activity_phone_delection_phoneresolution);
        textView_cameraresolution = (TextView) findViewById(R.id.activity_phone_delection_cameraresolution);
        textView_basebandversion = (TextView) findViewById(R.id.activity_phone_delection_basebandversion);
        textView_isroot = (TextView) findViewById(R.id.activity_phone_delection_isroot);
        progressBar = (ProgressBar) findViewById(R.id.phone_delection_peogressbar);
        textView_electric = (TextView) findViewById(R.id.phone_delection_peogressbar_tv);
        ll = (LinearLayout) findViewById(R.id.phone_delection_peogressbar_ll);


    }

    @Override
    public void setview() {
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);
        PhoneManager phoneManager = PhoneManager.getPhoneManage(Activity_phone_delection.this);
        textView_phonename.setText(phoneManager.getPhoneName1());
        textView_phoneversion.setText(phoneManager.getPhoneSystemVersion());
        String allspace = CommonUtil.getFileSize(MemoryManager.getPhoneTotalRamMemory());
        textView_allspace.setText(allspace);
        String lastspace = CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(this));
        textView_lastspace.setText(lastspace);
        textView_cpuname.setText(phoneManager.getPhoneCpuName());
        textView_cpunumber.setText(phoneManager.getPhoneCpuNumber()+"");
        textView_phoneresolution.setText(phoneManager.getResolution());
        textView_cameraresolution.setText(phoneManager.getMaxPhotoSize());
        textView_basebandversion.setText(phoneManager.getPhoneSystemBasebandVersion());
        if (phoneManager.isRoot()) {
            textView_isroot.setText("是");
        }else {
            textView_isroot.setText("否");
        }

//        Thread uptvthread = new Thread(new uptextView());
//        uptvthread.start();


//        textView_electric.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                textView_electric.setText(progressBar.getProgress()+"");
//            }
//        });
//        textView_electric.setText(progressBar.getProgress()+"");



    }

    private void showListDialog(final int maxelectric,final int nowelectric,final int cell_tem) {
        String[] items = { "当前电量："+((nowelectric*100)/maxelectric),"电池温度："+cell_tem};
        AlertDialog.Builder listDialog = new AlertDialog.Builder(Activity_phone_delection.this);
        listDialog.setTitle("电池电量信息");
        listDialog.setItems(items,null);
        listDialog.show();
    }

    class MyReciver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Bundle bundle = intent.getExtras();
                final int maxBattery = (Integer) bundle.get(BatteryManager.EXTRA_SCALE); // 总电量
                final int currtbattery = (Integer) bundle.get(BatteryManager.EXTRA_LEVEL); // 当前电量
                final int currttemp = (Integer) bundle.get(BatteryManager.EXTRA_TEMPERATURE); // 电池温度
                uptextView(maxBattery,currtbattery,progressBar.getProgress());
                ll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showListDialog(maxBattery,currtbattery,currttemp);
                    }
                });

            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mr);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int  number= (int) msg.obj;
                    progressBar.setProgress(number);
                    textView_electric.setText(number+"%");
                    break;
                case 1:
                    int  number1= (int) msg.obj;
                    progressBar.setProgress(number1);
                    textView_electric.setText(number1+"%");
                    break;
            }
        }
    };

    public void uptextView(final int maxelectric,final int nowelectric,final int nownumber){
        new Thread(new Runnable() {
            @Override
            public void run() {

                int to=((nowelectric*100)/maxelectric);
                int now=nownumber;

                if(to>now){//代表电量增加
                    while(now<to){
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        now++;
                        //告知主线程进行修改
                        //发送消息
                        Message ms=new Message();
                        ms.obj=now;//传递过去的电量
                        ms.what=0;
                        handler.sendMessage(ms);
                    }
                }else if(to<now){
                    while(to<now){
                        //告知主线程修改
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        now--;
                        //告知主线程进行修改
                        //发送消息
                        Message ms=new Message();
                        ms.obj=now;//传递过去的电量
                        ms.what=1;
                        handler.sendMessage(ms);

                    }
                }


//                int i = 0;
//                while (true){
//                    SystemClock.sleep(80);
//                    i++;
//                    if (i>30){
//                        i++;
//                    }
//                    if (i>50){
//                        i++;
//                    }
//                    if (i>=(nowelectric/maxelectric)*100){
//                        i=(nowelectric/maxelectric)*100;
//                    }
//                    Message msg = new Message();
//                    msg.what = 0;
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("number",i);
//                    msg.setData(bundle);
//                    handler.sendMessage(msg);
//                }
            }
        }).start();
    }

}
