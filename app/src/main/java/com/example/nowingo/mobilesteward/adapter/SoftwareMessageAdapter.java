package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.nowingo.mobilesteward.entity.AppInfo;
import com.example.nowingo.mobilesteward.entity.Software_Message_ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NowINGo on 2016/11/30.
 */
public class SoftwareMessageAdapter extends BaseAdapter {

    Context context;
    List<AppInfo> arrayList;
    Boolean Fling = false;

    public SoftwareMessageAdapter(Context context, List<AppInfo> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public Boolean getFling() {
        return Fling;
    }

    public void setFling(Boolean fling) {
        Fling = fling;
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
        Viewholder vh = null;
        if (convertView == null){
            vh = new Viewholder();
            convertView = LayoutInflater.from(context).inflate(R.layout.software_mesage_listview_item,null);
            vh.checkBox = (CheckBox) convertView.findViewById(R.id.software_message_listview_item_check);
            vh.imageView = (ImageView) convertView.findViewById(R.id.software_message_listview_item_img);
            vh.tv_name = (TextView) convertView.findViewById(R.id.software_message_listview_item_name);
            vh.tv_path = (TextView) convertView.findViewById(R.id.software_message_listview_item_path);
            vh.tv_version = (TextView) convertView.findViewById(R.id.software_message_listview_item_version);
            convertView.setTag(vh);
        }else {
            vh = (Viewholder) convertView.getTag();
        }
        vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                arrayList.get(position).setDel(isChecked);
                boolean flag = true;
                for (int i = 0; i <arrayList.size() ; i++) {
                    if (arrayList.get(i).isDel()==false){
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
        vh.checkBox.setChecked(arrayList.get(position).isDel());
        if (getFling() == true){
            vh.imageView.setImageResource(R.mipmap.ic_launcher);
            Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_for_listview);
            convertView.startAnimation(animation);
        }else {
            vh.imageView.setImageDrawable(arrayList.get(position).getPackageInfo().applicationInfo.loadIcon(context.getPackageManager()));
        }
        vh.tv_name.setText(arrayList.get(position).getPackageInfo().applicationInfo.loadLabel(context.getPackageManager()).toString());
        vh.tv_path.setText(arrayList.get(position).getPackageInfo().packageName);
        vh.tv_version.setText(arrayList.get(position).getPackageInfo().versionName);
//        if ((getFling() == true)&&(getFling2() == true)){
//            Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_for_listview);
//            convertView.startAnimation(animation);
//            animation.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//                    setFling2(false);
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    setFling2(true);
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//        }
        return convertView;
    }

    public void checkall(boolean flag){
        for (int i = 0; i <arrayList.size() ; i++) {
            arrayList.get(i).setDel(flag);
        }
    }

    class Viewholder{
        CheckBox checkBox;
        ImageView imageView;
        TextView tv_name,tv_path,tv_version;
    }
}
