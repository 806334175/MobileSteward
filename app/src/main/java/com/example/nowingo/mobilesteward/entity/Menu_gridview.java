package com.example.nowingo.mobilesteward.entity;

import android.widget.ImageView;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Menu_gridview {
    private int imageView;
    private String name;
    private Class aClass;

    public Menu_gridview(int imageView, String name, Class aClass) {
        this.imageView = imageView;
        this.name = name;
        this.aClass = aClass;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Class getaClass() {
        return aClass;
    }

    public void setaClass(Class aClass) {
        this.aClass = aClass;
    }
}
