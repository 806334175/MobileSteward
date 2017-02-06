package com.example.nowingo.mobilesteward.entity;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class PhoneNumber {
    private String name;
    private long number;

    public PhoneNumber(String name, long number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
