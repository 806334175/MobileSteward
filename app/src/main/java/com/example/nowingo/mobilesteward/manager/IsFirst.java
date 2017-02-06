package com.example.nowingo.mobilesteward.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class IsFirst {
    public void save(Context context){
        SharedPreferences sdf = context.getSharedPreferences("First",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sdf.edit();
        editor.putBoolean("isfirst",false);
        editor.commit();
    }

    public boolean load(Context context){
        SharedPreferences sdf = context.getSharedPreferences("First",context.MODE_PRIVATE);
        return sdf.getBoolean("isfirst",true);
    }
}
