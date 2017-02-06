package com.example.nowingo.mobilesteward.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.AppInfo;
import com.example.nowingo.mobilesteward.entity.FileInfo;
import com.example.nowingo.mobilesteward.entity.FileManagerEntity;
import com.example.nowingo.mobilesteward.tools.CommonUtil;
import com.example.nowingo.mobilesteward.tools.FileTypeUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NowINGo on 2016/12/2.
 */
public class FileMessageListViewAdapter extends BaseAdapter {
    Context context;
    List<FileInfo> arrayList;
    Boolean Fling = false;

    public FileMessageListViewAdapter(Context context) {
        this.context = context;
        arrayList = new ArrayList<>();
    }

    public List<FileInfo> getArrayList() {
        return arrayList;
    }

    public void setArrayList(List<FileInfo> arrayList) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.file_mesage_listview_item,null);
            vh.checkBox = (CheckBox) convertView.findViewById(R.id.file_message_listview_item_check);
            vh.imageView = (ImageView) convertView.findViewById(R.id.file_message_listview_item_img);
            vh.tv_name = (TextView) convertView.findViewById(R.id.file_message_listview_item_name);
            vh.tv_time = (TextView) convertView.findViewById(R.id.file_message_listview_item_time);
            vh.tv_size = (TextView) convertView.findViewById(R.id.file_message_listview_item_size);
            convertView.setTag(vh);
        }else {
            vh = (Viewholder) convertView.getTag();
        }

            vh.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    arrayList.get(position).setSelect(isChecked);
                }
            });
            vh.checkBox.setChecked(arrayList.get(position).isSelect());
            if (getFling() == true) {
                vh.imageView.setImageResource(R.mipmap.ic_launcher);
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.translate_for_listview);
                convertView.startAnimation(animation);
            } else {
                vh.imageView.setImageBitmap(getFileIcon(arrayList.get(position)));

            }
            //vh.imageView.setBackgroundResource(R.mipmap.ic_launcher);
            vh.tv_name.setText(arrayList.get(position).getFile().getName());
            vh.tv_time.setText(CommonUtil.getStrTime(arrayList.get(position).getFile().lastModified()));
            vh.tv_size.setText(CommonUtil.getFileSize(arrayList.get(position).getFile().length()));

        return convertView;

    }
    public void Remove(){
        for (int i = 0; i <arrayList.size() ; i++) {
            if (arrayList.get(i).isSelect()){
                getArrayList().remove(i);
            }
        }
    }

    private Bitmap getFileIcon(FileInfo fileInfo) {
        Bitmap bitmap = null;
        String fielPath = fileInfo.getFile().getPath();

        // 如果是图像，根据图像路径加载图像
        if (fileInfo.getFileType().equals(FileTypeUtil.TYPE_IMAGE)) {
            BitmapFactory.Options options = new BitmapFactory.Options();
//            Log.i("msg","图像");
            return BitmapFactory.decodeFile(fielPath, options);
        }
        // 剩下的用Res内的指定图像资源做为图标图像
        Resources res = context.getResources();
        int icon = res.getIdentifier(fileInfo.getIconName(), "drawable", context.getPackageName());
        if (icon<0) {
            icon = R.drawable.icon_file;
        }
        bitmap = BitmapFactory.decodeResource(context.getResources(), icon);
//            Log.i("msg","内存指定");


        return bitmap;
    }

    class Viewholder{
        CheckBox checkBox;
        ImageView imageView;
        TextView tv_name,tv_time,tv_size;
    }
}
