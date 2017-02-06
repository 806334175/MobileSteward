package com.example.nowingo.mobilesteward.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by NowINGo on 2016/12/14.
 */
public class View_for_Soft extends View {
    RectF oval;
    Paint paint;
    float sweepArg;
    float sweepArg_2;
    public View_for_Soft(Context context, AttributeSet attrs) {
        super(context, attrs);
        inidata();
    }

    public void inidata(){
        paint = new Paint();
        paint.setAntiAlias(true);
        sweepArg = 0;
        sweepArg_2 = 0;
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        oval = new RectF(0,0,width,height);
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(android.graphics.Color.parseColor("#ff8d02"));
        canvas.drawArc(oval,-90,360,true,paint);
        paint.setColor(android.graphics.Color.parseColor("#02aa02"));
        canvas.drawArc(oval,-90,sweepArg_2,true,paint);
        paint.setColor(android.graphics.Color.parseColor("#02029a"));
        canvas.drawArc(oval,-90,sweepArg,true,paint);
    }
    public void setArg(final float sweep){
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(sweepArg<=sweep){
                    sweepArg+=1;
                    sweepArg_2+=1;
                    postInvalidate();//重绘
                }
                if(sweepArg_2>=360){
                    //停止
                    timer.cancel();
                    postInvalidate();//重绘
                }
                if (sweepArg>=sweep){
                    sweepArg=sweep;
                }
            }
        };
        timer.schedule(timerTask,20,10);
    }

}
