package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.manager.PhoneManager;
import com.example.nowingo.mobilesteward.manager.ToolsManager;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_aboutus extends BaseActivity{
    TextView tv_name,tv_version;
    View top;

    String name;
    String version;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setLayout() {
        setContentView(R.layout.activity_aboutus);
    }

    @Override
    public void inidata() {
        PackageInfo p = null;
        try {
            p = this.getApplicationContext().getPackageManager().getPackageInfo(this.getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        version = p.versionName;
        name = (String) p.applicationInfo.loadLabel(this.getPackageManager());
    }
    @Override
    public void iniview() {
        top = findViewById(R.id.activity_aboutus_top);
        tv_name = (TextView) findViewById(R.id.activity_aboutus_tv_name);
        tv_version = (TextView) findViewById(R.id.activity_aboutus_tv_version);
    }

    @Override
    public void setview() {
        ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,"关于我们");
        tv_name.setText(name);
        tv_version.setText(version);
    }

}
