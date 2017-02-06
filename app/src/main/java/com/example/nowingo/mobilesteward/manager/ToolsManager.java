package com.example.nowingo.mobilesteward.manager;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nowingo.mobilesteward.R;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class ToolsManager {

    public static void setTitle(View view, final Activity activity, int imgid, String title){
        ImageView imageView = (ImageView) view.findViewById(R.id.top_left_iv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });
        imageView.setImageResource(imgid);
        TextView textView = (TextView) view.findViewById(R.id.top_center_tv);
        textView.setText(title);
    }


}
