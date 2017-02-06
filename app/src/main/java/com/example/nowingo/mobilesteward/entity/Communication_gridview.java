package com.example.nowingo.mobilesteward.entity;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Communication_gridview {
    private String name;
    private Class aClass;

    public Communication_gridview(String name, Class aClass) {
        this.name = name;
        this.aClass = aClass;
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
