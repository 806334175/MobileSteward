package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.entity.ClassList;
import com.example.nowingo.mobilesteward.entity.Communication_gridview;

import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class CommunicationGridViewAdapter extends BaseAdapter {

    Context context;
    ArrayList<ClassList> arrayList;
    int[] colors = {R.drawable.blue_item,R.drawable.red_item,R.drawable.green_item};

    public CommunicationGridViewAdapter(Context context, ArrayList<ClassList> arrayList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.communication_gridview_item,null);
            vh.tv = (TextView) convertView.findViewById(R.id.communication_gridview_item_name);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        convertView.setBackgroundResource(colors[position%3]);
        vh.tv.setText(arrayList.get(position).getName());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
    }
}
