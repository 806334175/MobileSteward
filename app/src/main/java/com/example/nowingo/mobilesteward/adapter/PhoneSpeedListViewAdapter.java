package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.entity.RuningAppInfo;
import com.example.nowingo.mobilesteward.tools.CommonUtil;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/12/5.
 */
public class PhoneSpeedListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<RuningAppInfo> arrayList;

    public PhoneSpeedListViewAdapter(Context context, ArrayList<RuningAppInfo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        SpeedHolder speedHolder = null;
        if (convertView == null){
            speedHolder = new SpeedHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.phone_speed_listview_item,null);
            speedHolder.checkBox = (CheckBox) convertView.findViewById(R.id.phone_speed_listview_item_check);
            speedHolder.imageView = (ImageView) convertView.findViewById(R.id.phone_speed_listview_item_img);
            speedHolder.tv_name = (TextView) convertView.findViewById(R.id.phone_speed_listview_item_name);
            speedHolder.tv_size = (TextView) convertView.findViewById(R.id.phone_speed_listview_item_space);
            speedHolder.tv_type = (TextView) convertView.findViewById(R.id.phone_speed_listview_item_type);
            convertView.setTag(speedHolder);
        }else {
            speedHolder = (SpeedHolder) convertView.getTag();
        }
        speedHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                arrayList.get(position).setClear(isChecked);
                boolean flag = true;
                for (int i = 0; i <arrayList.size() ; i++) {
                    if (arrayList.get(i).isClear()==false){
                        flag = false;
                    }
                }
                if (flag==true){
                    Intent intent=new Intent();//声明一个intent
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("id",true);
                    intent.putExtras(bundle);
                    intent.setAction("nsw");//设置广播的Action
                    context.sendBroadcast(intent);
                }else {
                    Intent intent=new Intent();//声明一个intent
                    Bundle bundle=new Bundle();
                    bundle.putBoolean("id",false);
                    intent.putExtras(bundle);
                    intent.setAction("nsw");//设置广播的Action
                    context.sendBroadcast(intent);
                }
            }
        });
        speedHolder.checkBox.setChecked(arrayList.get(position).isClear());
        speedHolder.imageView.setBackground(arrayList.get(position).getIcon());
        speedHolder.tv_name.setText(arrayList.get(position).getLableName());
        speedHolder.tv_size.setText(CommonUtil.getFileSize(arrayList.get(position).getSize()));
        if (arrayList.get(position).isSystem()) {
            speedHolder.tv_type.setText("系统进程");
        }else {
            speedHolder.tv_type.setText("");
        }
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_for_listview);
        convertView.startAnimation(animation);
        return convertView;
    }

    public void checkall(boolean flag){
        for (int i = 0; i <arrayList.size() ; i++) {
        arrayList.get(i).setClear(flag);
    }
}

    class SpeedHolder {
        CheckBox checkBox;
        ImageView imageView;
        TextView tv_name,tv_size,tv_type;
    }
}
