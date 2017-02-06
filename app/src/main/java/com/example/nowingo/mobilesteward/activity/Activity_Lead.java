package com.example.nowingo.mobilesteward.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.ViewPagerAdapter;
import com.example.nowingo.mobilesteward.manager.DbClearPathManager;
import com.example.nowingo.mobilesteward.manager.FileManager;
import com.example.nowingo.mobilesteward.manager.IsChecked;
import com.example.nowingo.mobilesteward.manager.IsFirst;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Activity_Lead extends AppCompatActivity {
    TextView btn_skip;
    ViewPager viewPager;
    ImageView iv_first,iv_second,iv_third;
    RelativeLayout rl_first,rl_second,rl_third;

    ArrayList<View> arrayList;
    ViewPagerAdapter viewPagerAdater;

    IsFirst is;
    int isOK = 0;
    static boolean flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        Intent intent_out = getIntent();
        isOK = intent_out.getIntExtra("OK",0);
        is = new IsFirst();
        if (!is.load(this)&&(isOK==0)){
            Intent intent = new Intent(Activity_Lead.this, Activity_Home.class);
            startActivity(intent);
            finish();
        }else {

            if (isOK == 0){
                IsChecked.save(this,false);
            }
            FileManager fileManager = new FileManager();
            try {
                File file = fileManager.createFile(this);
                fileManager.copyFile(this,file,"commonnum.db");
            } catch (IOException e) {
                e.printStackTrace();
            }

            DbClearPathManager dbClearPathManager = new DbClearPathManager();
            try {
                dbClearPathManager.createFile(this);
            } catch (IOException e) {
                e.printStackTrace();
            }

            btn_skip = (TextView) findViewById(R.id.activity_guidviewpager_btn_skip);
            viewPager = (ViewPager) findViewById(R.id.activity_guidviewpager_vp);
            iv_first = (ImageView) findViewById(R.id.activity_guidviewpager_iv_first);
            iv_second = (ImageView) findViewById(R.id.activity_guidviewpager_iv_second);
            iv_third = (ImageView) findViewById(R.id.activity_guidviewpager_iv_third);

            rl_first = (RelativeLayout) findViewById(R.id.activity_guidviewpager_rl_first);
            rl_second = (RelativeLayout) findViewById(R.id.activity_guidviewpager_rl_second);
            rl_third = (RelativeLayout) findViewById(R.id.activity_guidviewpager_rl_third);
            ArrayList<RelativeLayout> rl = new ArrayList<>();
            rl.add(rl_first);
            rl.add(rl_second);
            rl.add(rl_third);

            final ArrayList<ImageView> img = new ArrayList<>();
            img.add(iv_first);
            img.add(iv_second);
            img.add(iv_third);

            viewPagerAdater = new ViewPagerAdapter(this);

            arrayList = new ArrayList<>();
            View view1 = LayoutInflater.from(this).inflate(R.layout.one, null);
            arrayList.add(view1);
            view1.setBackgroundResource(R.mipmap.adware_style_applist);
            View view2 = LayoutInflater.from(this).inflate(R.layout.one, null);
            arrayList.add(view2);
            view2.setBackgroundResource(R.mipmap.adware_style_banner);
            View view3 = LayoutInflater.from(this).inflate(R.layout.one, null);
            arrayList.add(view3);
            view3.setBackgroundResource(R.mipmap.adware_style_creditswall);
            viewPagerAdater.setList(arrayList);
            viewPager.setAdapter(viewPagerAdater);
            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }
                @Override
                public void onPageSelected(int position) {
                    if (viewPager.getCurrentItem() == arrayList.size() - 1) {
                        btn_skip.setVisibility(View.VISIBLE);
                        is.save(Activity_Lead.this);
                    } else {
                        btn_skip.setVisibility(View.INVISIBLE);
                    }
                    for (int i = 0; i < img.size(); i++) {
                        if (i == viewPager.getCurrentItem()) {
                            img.get(i).setBackgroundResource(R.mipmap.adware_style_selected);
                        } else {
                            img.get(i).setBackgroundResource(R.mipmap.adware_style_default);
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    if (viewPager.getCurrentItem() == arrayList.size() - 1) {
                        switch (state) {
                            case 0:
                                if (flag) {
                                    if (isOK==0) {
                                        Intent intent = new Intent(Activity_Lead.this, Activity_Home.class);
                                        startActivity(intent);
                                        finish();
                                    }else {
                                        finish();
                                    }
                                }
                                break;
                            case 1:
                                flag = true;
                                break;
                            case 2:
                                flag = false;
                                break;
                        }
                    }
                }
            });

            for (int i = 0; i < rl.size(); i++) {
                final int x = i;
                rl.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewPager.setCurrentItem(x);
                    }
                });
            }

//        iv_first.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(0);
//            }
//        });
//        iv_second.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(1);
//            }
//        });
//        iv_third.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewPager.setCurrentItem(2);
//            }
//        });

            btn_skip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isOK==0) {
                        Intent intent = new Intent(Activity_Lead.this, Activity_Home.class);
                        startActivity(intent);
                        finish();
                    }else {
                        finish();
                    }
                }
            });
        }







    }
}
