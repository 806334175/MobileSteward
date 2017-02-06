package com.example.nowingo.mobilesteward.manager;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.activity.Activity_Home;

/**
 * Created by NowINGo on 2016/12/13.
 */
public class MyNotificationManager {
    android.app.NotificationManager nm;
    Context context;


    public MyNotificationManager(Context context) {
        this.context = context;
        nm = (android.app.NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
    }

    public void sent(View view){
        //设置点击后启动的activity
        Intent intent = new Intent(context,Activity_Home.class);
        //将Intent封装进PendingIntent中，点击通知的消息后，就会启动对应的程序
        PendingIntent pIntent = PendingIntent.getActivity(context, 0, intent, 0);
//        NotificationCompat.Builder nb = new NotificationCompat.Builder(this);
//        Notification notification =nb.build();

        Notification notification = new Notification.Builder(context)
                .setAutoCancel(false)//点击通知后，自动消失
                .setOngoing(true)
                .setTicker("安卓流氓正在污染你的手机")//在顶部状态栏中的提示信息
                .setSmallIcon(R.mipmap.nb)//设置顶部状态栏的小图标
                .setContentTitle("我是流氓")//设置通知中心的标题
                .setContentText("你TM干掉我啊！")//设置通知中心中的内容
                .setDefaults(Notification.DEFAULT_SOUND|//设置使用默认的声音
                        Notification.DEFAULT_LIGHTS|//设置使用默认的LED
                        Notification.DEFAULT_VIBRATE)//设置使用默认的振动
                //.setVibrate(new Long[] {0,50,100,150}) 设置自定义的振动
        /*.setSound(Uri.parse("file:///sdcard/click.mp3"))*/
                .setWhen(System.currentTimeMillis())
                .setContentIntent(pIntent)//该通知要启动的Intent
                .build();
        //发送该通知
        nm.notify(0,notification);
    }
    public void del(View view){
        //取消通知
        nm.cancel(0);
    }
}
