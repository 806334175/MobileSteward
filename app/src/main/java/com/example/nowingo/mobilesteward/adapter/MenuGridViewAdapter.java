package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.entity.Menu_gridview;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class MenuGridViewAdapter extends BaseAdapter {
    Context context;
    ArrayList<Menu_gridview> arrayList;

    public MenuGridViewAdapter(Context context, ArrayList<Menu_gridview> arrayList) {
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
        ViewHolder vh = null;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.menu_gridview_item,null);
            vh.iv = (ImageView) convertView.findViewById(R.id.menu_gridview_item_iv);
            vh.tv = (TextView) convertView.findViewById(R.id.menu_gridview_item_tv);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.iv.setImageResource(arrayList.get(position).getImageView());
        vh.tv.setText(arrayList.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        ImageView iv;
        TextView tv;
    }

}
