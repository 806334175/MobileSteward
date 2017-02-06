package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.PhoneNumberListViewAdapter;
import com.example.nowingo.mobilesteward.db.NumberDbExpress;
import com.example.nowingo.mobilesteward.entity.PhoneNumber;
import com.example.nowingo.mobilesteward.manager.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class Activity_PhoneNumber extends Activity {
    ImageView iv_top_left,iv_top_right;
    TextView tv_top_center;
    ListView lv_number;

    ArrayList<PhoneNumber> arrayList;
    PhoneNumberListViewAdapter phoneNumberListViewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phonenumber);
        iv_top_left = (ImageView) findViewById(R.id.top_left_iv);
        iv_top_right = (ImageView) findViewById(R.id.top_right_iv);
        tv_top_center = (TextView) findViewById(R.id.top_center_tv);
        lv_number = (ListView) findViewById(R.id.activity_phonenumber_lv);

        iv_top_left.setImageResource(R.mipmap.btn_homeasup_default);



        Intent intent = getIntent();
        int id = intent.getIntExtra("id",-1);
        String name = intent.getStringExtra("name");
        tv_top_center.setText(name);

        try {
            File file = FileManager.createFile(this);
            arrayList = NumberDbExpress.getNumber(file,id);
            phoneNumberListViewAdapter = new PhoneNumberListViewAdapter(Activity_PhoneNumber.this,arrayList);
            lv_number.setAdapter(phoneNumberListViewAdapter);

//            Animation animation = AnimationUtils.loadAnimation(this,R.anim.translate_for_listview);
//            LayoutAnimationController animationController = new LayoutAnimationController(animation);
//            lv_number.setLayoutAnimation(animationController);
        } catch (IOException e) {
            e.printStackTrace();
        }

        lv_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent1 = new Intent();
                intent1.setAction(intent1.ACTION_CALL);
                intent1.setData(Uri.parse("tel:"+arrayList.get(position).getNumber()));
                startActivity(intent1);
            }
        });

        iv_top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
