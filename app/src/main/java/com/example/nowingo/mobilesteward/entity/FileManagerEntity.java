package com.example.nowingo.mobilesteward.entity;

/**
 * Created by NowINGo on 2016/12/2.
 */
public class FileManagerEntity {
    private String name;
    private String number;
    private int imgid;

    public FileManagerEntity(String name, String number, int imgid) {
        this.name = name;
        this.number = number;
        this.imgid = imgid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
