package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.entity.PhoneNumber;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class PhoneNumberListViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<PhoneNumber> arrayList;

    public PhoneNumberListViewAdapter(Context context, ArrayList<PhoneNumber> arrayList) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder1 vh = null;
        if (convertView == null){
            vh = new ViewHolder1();
            convertView = LayoutInflater.from(context).inflate(R.layout.phonenumber_listview_item,null);
            vh.tv_left = (TextView) convertView.findViewById(R.id.phonenumber_listbiew_item_left_tv);
            vh.tv_right = (TextView) convertView.findViewById(R.id.phonenumber_listbiew_item_right_tv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder1) convertView.getTag();
        }
        vh.tv_left.setText(arrayList.get(position).getName());
        vh.tv_right.setText( arrayList.get(position).getNumber()+"");

        Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_for_listview);
        convertView.startAnimation(animation);
        return convertView;
    }
    class ViewHolder1{
        TextView tv_left,tv_right;
    }
}
