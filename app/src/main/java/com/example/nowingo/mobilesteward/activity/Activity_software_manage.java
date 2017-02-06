package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import com.example.nowingo.mobilesteward.manager.MemoryManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;
import com.example.nowingo.mobilesteward.view.Progress_for_Soft;
import com.example.nowingo.mobilesteward.view.View_for_Soft;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_software_manage extends BaseActivity {
    View top;
    RelativeLayout rl_allsoft,rl_systemsoft,rl_usersoft;
    ProgressBar progressBar_phoneself,progressBar_outphone;
    TextView textView_phoneself,textView_outphone;
    View_for_Soft viewForSoft;

    String title;

    String maxphone;
    String freephone;
    String maxphoneout;
    String freephoneout;
    int self;
    int out;

    private int mTotalProgress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new Thread(new MyThread()).start();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        inidata();
        setview();
    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_software_manage);
    }

    @Override
    public void inidata() {
        Intent intent =getIntent();
        title = intent.getStringExtra("name");
        long a = MemoryManager.getPhoneAllSize();
        long b = MemoryManager.getPhoneAllFreeSize();
        long c = MemoryManager.getPhoneOutSDCardSize(this);
        long d = MemoryManager.getPhoneOutSDCardFreeSize(this);
        maxphone = CommonUtil.getFileSize(a);
        freephone = CommonUtil.getFileSize(b);
        maxphoneout = CommonUtil.getFileSize(c);
        freephoneout = CommonUtil.getFileSize(d);
        self = (int) (((a-b)*100)/a);
        if (c == 0){
            out = 0;
        }else {
            out = (int) ((((c - d)* 100) / c) );
        }
        mTotalProgress = (int)((a*360)/(a+c));
    }
    @Override
    public void iniview() {
        top = findViewById(R.id.top_software_manage);
        rl_allsoft = (RelativeLayout) findViewById(R.id.activity_software_manage_allsoft);
        rl_systemsoft = (RelativeLayout) findViewById(R.id.activity_software_manage_systemsoft);
        rl_usersoft = (RelativeLayout) findViewById(R.id.activity_software_manage_usersoft);
        progressBar_phoneself = (ProgressBar) findViewById(R.id.activity_software_manage_pro_phoneself);
        progressBar_outphone = (ProgressBar) findViewById(R.id.activity_software_manage_pro_outphone);
        textView_phoneself = (TextView) findViewById(R.id.activity_software_manage_tv_phoneself);
        textView_outphone = (TextView) findViewById(R.id.activity_software_manage_tv_outphone);

        viewForSoft = (View_for_Soft) findViewById(R.id.activity_software_manage_self_view);

    }

    @Override
    public void setview() {

        viewForSoft.setArg(mTotalProgress);
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);
        progressBar_phoneself.setProgress(self);
        textView_phoneself.setText("可用"+freephone+"/"+maxphone);
        progressBar_outphone.setProgress(out);
        textView_outphone.setText("可用"+freephoneout+"/"+maxphoneout);

        rl_allsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title","所有软件");
                startActivity(Activity_Software_Message.class,bundle);
            }
        });
        rl_systemsoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title","系统软件");
                startActivity(Activity_Software_Message.class,bundle);
            }
        });
        rl_usersoft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("title","用户软件");
                startActivity(Activity_Software_Message.class,bundle);
            }
        });
    }

//    class  MyThread implements Runnable{
//
//        @Override
//        public void run() {
//            while (mCurrentProgress < mTotalProgress) {
//                mCurrentProgress += 1;
//
//                progressForSoft.setProgress(mCurrentProgress);
//
//                try {
//                    Thread.sleep(50);
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
}
