package com.example.nowingo.mobilesteward.adapter;
        import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.animation.Animation;
        import android.view.animation.AnimationUtils;
        import android.widget.BaseAdapter;
        import android.widget.CheckBox;
        import android.widget.ImageView;
        import android.widget.TextView;


        import com.example.nowingo.mobilesteward.R;
        import com.example.nowingo.mobilesteward.entity.RubbishFileInfo;
        import com.example.nowingo.mobilesteward.tools.CommonUtil;

        import java.util.ArrayList;

/**
 * Created by 王小川 on 2016/12/9.
 */
public class ClearAdapter extends BaseAdapter {
    ArrayList<RubbishFileInfo> list;
    Context context;
    boolean isFiling;

    public ClearAdapter(Context context) {
        this.context = context;
        list=new ArrayList<RubbishFileInfo>();
        isFiling=false;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public ArrayList<RubbishFileInfo> getList() {
        return list;
    }

    public boolean isFiling() {
        return isFiling;
    }

    public void setFiling(boolean filing) {
        isFiling = filing;
    }

    public void setList(ArrayList<RubbishFileInfo> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.junk_clean_list_item,null);
            vh.cb= (CheckBox) view.findViewById(R.id.junk_clean_cb);
            vh.iv= (ImageView) view.findViewById(R.id.junk_clean_iv);
            vh.tv_name= (TextView) view.findViewById(R.id.junk_clean_tv_name);
            vh.tv_size= (TextView) view.findViewById(R.id.junk_clean_tv_size);

            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.cb.setVisibility(View.GONE);//隐藏哪个选择框
        RubbishFileInfo rubbishFileInfo=getList().get(position);
        vh.tv_name.setText(rubbishFileInfo.getSoftChinesename());
        vh.tv_size.setText(CommonUtil.getFileSize(rubbishFileInfo.getSize()));
//        iv_icon.setImageDrawable(getItem(position).getIcon());
        if(isFiling){
            vh.iv.setImageResource(R.mipmap.ic_launcher);
        }else{
            vh.iv.setImageDrawable(rubbishFileInfo.getIcon());
        }
        Animation animation = AnimationUtils.loadAnimation(context,R.anim.translate_for_listview);
        view.startAnimation(animation);
        return view;
    }
    class ViewHolder{
        CheckBox cb;
        ImageView iv;
        TextView tv_name,tv_size;
    }
}
