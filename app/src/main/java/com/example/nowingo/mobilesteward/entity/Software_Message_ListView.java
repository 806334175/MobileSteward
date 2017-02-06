package com.example.nowingo.mobilesteward.entity;

import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by NowINGo on 2016/11/30.
 */
public class Software_Message_ListView {
    private boolean checkBox;
    private int imgid;
    private String tv_name;
    private String tv_path;
    private String tv_version;

    public Software_Message_ListView(boolean checkBox, int imgid, String tv_name, String tv_path, String tv_version) {
        this.checkBox = checkBox;
        this.imgid = imgid;
        this.tv_name = tv_name;
        this.tv_path = tv_path;
        this.tv_version = tv_version;
    }

    public boolean isCheckBox() {
        return checkBox;
    }

    public void setCheckBox(boolean checkBox) {
        this.checkBox = checkBox;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }

    public String getTv_name() {
        return tv_name;
    }

    public void setTv_name(String tv_name) {
        this.tv_name = tv_name;
    }

    public String getTv_path() {
        return tv_path;
    }

    public void setTv_path(String tv_path) {
        this.tv_path = tv_path;
    }

    public String getTv_version() {
        return tv_version;
    }

    public void setTv_version(String tv_version) {
        this.tv_version = tv_version;
    }
}

