package com.example.nowingo.mobilesteward.entity;

import android.content.pm.PackageInfo;

/**
 * Created by NowINGo on 2016/11/30.
 */
public class AppInfo {
    private PackageInfo packageInfo;//具体信息
    private boolean isDel; // 是否清理 (是否被选中)

    public AppInfo(PackageInfo packageInfo) {
        this(packageInfo, false);
    }

    public AppInfo(PackageInfo packageInfo, boolean isDel) {
        this.packageInfo = packageInfo;
        this.isDel = isDel;
    }

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(PackageInfo packageInfo) {
        this.packageInfo = packageInfo;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean isDel) {
        this.isDel = isDel;
    }
}
