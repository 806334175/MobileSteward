package com.example.nowingo.mobilesteward.tools;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by NowINGo on 2016/12/12.
 */
public class FrameAnimationUtil {
   public static void frame(View view, Drawable background){
       view.setBackground(background);
       AnimationDrawable animationDrawable = (AnimationDrawable) view.getBackground();
       animationDrawable.start();
   }
}
