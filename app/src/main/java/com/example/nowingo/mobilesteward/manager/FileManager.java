package com.example.nowingo.mobilesteward.manager;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by NowINGo on 2016/11/29.
 */
public class FileManager {

    public static File createFile(Context context) throws IOException {
        File file = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){

            file = new File(Environment.getExternalStorageDirectory()+File.separator+"MyDB"+File.separator+"number.db");
        }else {

            file = new File("data"+File.separator+"data"+File.separator+context.getPackageName()+File.separator+"MyDB"+File.separator+"number.db");
        }
        if (!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if (!file.exists()){

            file.createNewFile();
        }
        return file;
    }

    //复制数据
    public static void copyFile(Context context,File file,String filename) throws IOException {
        AssetManager am=context.getAssets();
        InputStream is=am.open(filename);//得到输入流
        //执行复制操作
        BufferedInputStream bis=null;//声明缓冲输入流
        BufferedOutputStream bos=null;//声明缓冲输出流
        byte[] b=new byte[1024];//声明数组
        int len=0;//声明每次的长度
        bis=new BufferedInputStream(is);
        bos=new BufferedOutputStream(new FileOutputStream(file));
        while((len=bis.read(b))!=-1){
            bos.write(b,0,len);
        }
        if(bis!=null){
            bis.close();
        }
        if(bos!=null){
            bos.flush();
            bos.close();
        }
    }
}
