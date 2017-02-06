package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.MenuGridViewAdapter;
import com.example.nowingo.mobilesteward.adapter.PhoneSpeedListViewAdapter;
import com.example.nowingo.mobilesteward.entity.Menu_gridview;
import com.example.nowingo.mobilesteward.entity.RuningAppInfo;
import com.example.nowingo.mobilesteward.manager.AppInfoManager;
import com.example.nowingo.mobilesteward.manager.IsChecked;
import com.example.nowingo.mobilesteward.manager.MemoryManager;
import com.example.nowingo.mobilesteward.manager.MyNotificationManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;
import com.example.nowingo.mobilesteward.view.Progress_for_Soft;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_Home extends Activity {
    ImageView iv_top_left,iv_top_right;
    TextView tv_top_center;
    RelativeLayout iv_img;
    Progress_for_Soft progressForSoft;
    TextView tv_speed_1,tv_speed_2;
    ImageView iv_speed;

    GridView gv_menu;
    ArrayList<Menu_gridview> arrayList;
    MenuGridViewAdapter menuGridViewAdapter;
    MyNotificationManager mnm;

    Boolean flag = true;
    private long exitTime = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        iv_top_left = (ImageView) findViewById(R.id.top_left_iv);
        iv_top_right = (ImageView) findViewById(R.id.top_right_iv);
        tv_top_center = (TextView) findViewById(R.id.top_center_tv);
        gv_menu = (GridView) findViewById(R.id.activity_menu_gv);
        iv_img = (RelativeLayout) findViewById(R.id.activity_home_img);
        progressForSoft = (Progress_for_Soft) findViewById(R.id.activity_menu_self_view);
        tv_speed_1 = (TextView) findViewById(R.id.activity_home_tv_speed);
        tv_speed_2 = (TextView) findViewById(R.id.activity_home_tv_speed_2);
        iv_speed = (ImageView) findViewById(R.id.activity_home_speed_iv);


        iv_top_left.setImageResource(R.mipmap.ic_launcher);
        tv_top_center.setText("安卓管家");
        iv_top_right.setImageResource(R.mipmap.ic_child_configs);
        mnm = new MyNotificationManager(this);
        updateProgressBar();
        if (IsChecked.load(this)){
            mnm.sent(null);
        }

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.alpha_for_home);
        iv_img.startAnimation(animation);



        Menu_gridview menu_gridview1 = new Menu_gridview(R.mipmap.icon_rocket,"手机加速",Activity_phone_speed.class);
        Menu_gridview menu_gridview2 = new Menu_gridview(R.mipmap.icon_softmgr,"软件管理",Activity_software_manage.class);
        Menu_gridview menu_gridview3 = new Menu_gridview(R.mipmap.icon_phonemgr,"手机检测",Activity_phone_delection.class);
        Menu_gridview menu_gridview4 = new Menu_gridview(R.mipmap.icon_telmgr,"通讯大全",Activity_communication.class);
        Menu_gridview menu_gridview5 = new Menu_gridview(R.mipmap.icon_filemgr,"文件管理",Activity_file_manage.class);
        Menu_gridview menu_gridview6 = new Menu_gridview(R.mipmap.icon_sdclean,"垃圾清理",Activity_junk_clean.class);
        arrayList = new ArrayList<>();
        arrayList.add(menu_gridview1);
        arrayList.add(menu_gridview2);
        arrayList.add(menu_gridview3);
        arrayList.add(menu_gridview4);
        arrayList.add(menu_gridview5);
        arrayList.add(menu_gridview6);

        menuGridViewAdapter = new MenuGridViewAdapter(this,arrayList);
        gv_menu.setAdapter(menuGridViewAdapter);

        gv_menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Menu_gridview menu_gridview = arrayList.get(position);
                Intent intent = new Intent(Activity_Home.this,menu_gridview.getaClass());
                intent.putExtra("name",arrayList.get(position).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.from_bottom_to_top,R.anim.from_bottom_to_top_2);
            }
        });

        iv_top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Home.this,Activity_aboutus.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_left_to_right,R.anim.from_left_to_right_2);
            }
        });
        iv_top_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Activity_Home.this,Activity_setting.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_right_to_left,R.anim.from_right_to_left_2);
            }
        });

        iv_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoManager.getAppInfoManager(Activity_Home.this).killALLProcesses();
                updateProgressBar();
            }
        });
        tv_speed_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppInfoManager.getAppInfoManager(Activity_Home.this).killALLProcesses();
                updateProgressBar();
            }
        });

    }
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int  number= (int) msg.obj;
                    tv_speed_1.setText(number+"");
                    progressForSoft.setProgress(number);
                    break;
            }
        }
    };

    public void updateProgressBar(){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (flag) {
                        flag = false;
                        long all = MemoryManager.getPhoneTotalRamMemory();
                        long free = MemoryManager.getPhoneFreeRamMemory(Activity_Home.this);

                        int to = (int) (((all - free) * 100) / all);
                        int now = progressForSoft.getProgress();

                        while (now > 0) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            now--;
                            Message ms = new Message();
                            ms.obj = now;
                            ms.what = 0;
                            handler.sendMessage(ms);
                        }

                        while (now < to) {
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            now++;
                            Message ms = new Message();
                            ms.obj = now;
                            ms.what = 0;
                            handler.sendMessage(ms);
                            if (now == to){
                                flag = true;
                            }
                        }
                    }
                }
            }).start();


    }
    float x1 = 0;
    float y1 = 0;
    float x2 = 0;
    float y2 = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN){
            x1 = event.getX();
            y1 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE){
            x2 = event.getX();
            y2 = event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if ((x2!=0)&&(y2!=0)) {
                if (x1 - x2 > 200) {
                    Intent intent = new Intent(Activity_Home.this, Activity_setting.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_right_to_left, R.anim.from_right_to_left_2);
                } else if (x2 - x1 > 200) {
                    Intent intent = new Intent(Activity_Home.this, Activity_aboutus.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.from_left_to_right, R.anim.from_left_to_right_2);
                }
            }
             x1 = 0;
             y1 = 0;
             x2 = 0;
             y2 = 0;
        }
            return super.onTouchEvent(event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            System.exit(0);
        }
    }
}
