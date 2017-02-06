package com.example.nowingo.mobilesteward.entity;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class ClassList {
    private String name;
    private int idx;

    public ClassList(String name, int idx) {
        this.name = name;
        this.idx = idx;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }
}
