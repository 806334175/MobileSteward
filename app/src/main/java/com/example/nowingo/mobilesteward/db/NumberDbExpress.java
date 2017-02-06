package com.example.nowingo.mobilesteward.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nowingo.mobilesteward.entity.ClassList;
import com.example.nowingo.mobilesteward.entity.PhoneNumber;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class NumberDbExpress {
    Context context;


    public static ArrayList<ClassList> getClasslist(File file){
        ArrayList<ClassList> arrayList = new ArrayList<>();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor = database.rawQuery("select * from classlist",null);
        if (cursor!=null){
            ClassList classList = null;
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                int idx = cursor.getInt(cursor.getColumnIndex("idx"));
                classList = new ClassList(name,idx);
                arrayList.add(classList);
            }
        }
        return arrayList;
    }

    public static ArrayList<PhoneNumber> getNumber(File file,int id){
        ArrayList<PhoneNumber> arrayList = new ArrayList<>();
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor = database.rawQuery("select * from table"+id,null);
        if (cursor!=null){
            PhoneNumber phoneNumber = null;
            while (cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex("name"));
                long number = cursor.getLong(cursor.getColumnIndex("number"));
                phoneNumber = new PhoneNumber(name,number);
                arrayList.add(phoneNumber);
            }
        }
        return arrayList;
    }



}
