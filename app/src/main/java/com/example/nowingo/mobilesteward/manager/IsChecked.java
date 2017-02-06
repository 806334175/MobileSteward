package com.example.nowingo.mobilesteward.manager;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class IsChecked {
    public static void save(Context context,Boolean ischeck){
        SharedPreferences sdf = context.getSharedPreferences("Checked",context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sdf.edit();
        editor.putBoolean("ischeck",ischeck);
        editor.commit();
    }

    public static boolean load(Context context){
        SharedPreferences sdf = context.getSharedPreferences("Checked",context.MODE_PRIVATE);
        return sdf.getBoolean("ischeck",true);
    }
}
