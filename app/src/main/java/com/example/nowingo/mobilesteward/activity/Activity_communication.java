package com.example.nowingo.mobilesteward.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.CommunicationGridViewAdapter;
import com.example.nowingo.mobilesteward.db.NumberDbExpress;
import com.example.nowingo.mobilesteward.entity.ClassList;
import com.example.nowingo.mobilesteward.manager.FileManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_communication extends Activity {
    ImageView iv_top_left,iv_top_right;
    TextView tv_top_center;
    GridView gridView_comm;

    ArrayList<ClassList> arrayList;
    CommunicationGridViewAdapter communicationGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);
        iv_top_left = (ImageView) findViewById(R.id.top_left_iv);
        iv_top_right = (ImageView) findViewById(R.id.top_right_iv);
        tv_top_center = (TextView) findViewById(R.id.top_center_tv);
        gridView_comm = (GridView) findViewById(R.id.activity_communication_gv);

        iv_top_left.setImageResource(R.mipmap.btn_homeasup_default);
        tv_top_center.setText("通讯大全");

        iv_top_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        arrayList = new ArrayList<>();
        try {
            File file = FileManager.createFile(this);
            arrayList = NumberDbExpress.getClasslist(file);
            communicationGridViewAdapter = new CommunicationGridViewAdapter(this,arrayList);
            gridView_comm.setAdapter(communicationGridViewAdapter);
        } catch (IOException e) {
            e.printStackTrace();
        }


        gridView_comm.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Activity_communication.this,Activity_PhoneNumber.class);
                intent.putExtra("id",arrayList.get(position).getIdx());
                intent.putExtra("name",arrayList.get(position).getName());
                startActivity(intent);
            }
        });

    }
}
