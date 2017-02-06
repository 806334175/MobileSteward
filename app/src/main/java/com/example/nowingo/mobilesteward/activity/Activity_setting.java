package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.manager.IsChecked;
import com.example.nowingo.mobilesteward.manager.MyNotificationManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_setting extends BaseActivity{

    CheckBox checkBox_inform;
    View top;
    RelativeLayout rl_help,rl_aboutus;
    MyNotificationManager mnm;

    //NotificationManager nm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_setting);
    }

    @Override
    public void inidata() {

    }

    @Override
    public void iniview() {
        //nm = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        top = findViewById(R.id.activity_setting_top);
        rl_help = (RelativeLayout) findViewById(R.id.activity_setting_help);
        rl_aboutus = (RelativeLayout) findViewById(R.id.activity_setting_aboutus);
        checkBox_inform = (CheckBox) findViewById(R.id.activity_setting_inform_check);
    }

    @Override
    public void setview() {
        checkBox_inform.setChecked(IsChecked.load(this));
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,"设置");
        rl_help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putInt("OK",1);
                startActivity(Activity_Lead.class,bundle);
                overridePendingTransition(R.anim.from_right_to_left,R.anim.from_right_to_left_2);
            }
        });
        rl_aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(Activity_aboutus.class);
                overridePendingTransition(R.anim.from_right_to_left,R.anim.from_right_to_left_2);
            }
        });

        mnm = new MyNotificationManager(this);
        checkBox_inform.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                IsChecked.save(Activity_setting.this,isChecked);
                if (isChecked){
                    mnm.sent(buttonView);

                }else {
                    mnm.del(buttonView);
                }
            }
        });
    }

//    private void sent(View view){
//        //设置点击后启动的activity
//        Intent intent = new Intent(Activity_setting.this,Activity_Home.class);
//        //将Intent封装进PendingIntent中，点击通知的消息后，就会启动对应的程序
//        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
////        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
////        Notification notification =nb.build();
//
//        Notification notification = new Notification.Builder(this)
//                .setAutoCancel(false)//点击通知后，自动消失
//                .setOngoing(true)
//                .setTicker("在顶部状态栏的提示信息")//在顶部状态栏中的提示信息
//                .setSmallIcon(R.mipmap.ic_launcher)//设置顶部状态栏的小图标
//                .setContentTitle("这是内容的标题")//设置通知中心的标题
//                .setContentText("这是通知的内容")//设置通知中心中的内容
//                .setDefaults(Notification.DEFAULT_SOUND|//设置使用默认的声音
//                        Notification.DEFAULT_LIGHTS|//设置使用默认的LED
//                        Notification.DEFAULT_VIBRATE)//设置使用默认的振动
//                //.setVibrate(new Long[] {0,50,100,150}) 设置自定义的振动
//        /*.setSound(Uri.parse("file:///sdcard/click.mp3"))*/
//                .setWhen(System.currentTimeMillis())
//                .setContentIntent(pIntent)//该通知要启动的Intent
//                .build();
//        //发送该通知
//        nm.notify(0,notification);
//    }
//    private void del(View view){
//        //取消通知
//        nm.cancel(0);
//    }
}
