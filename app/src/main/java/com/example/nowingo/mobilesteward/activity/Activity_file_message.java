package com.example.nowingo.mobilesteward.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nowingo.mobilesteward.R;
import com.example.nowingo.mobilesteward.adapter.FileMessageListViewAdapter;
import com.example.nowingo.mobilesteward.base.BaseActivity;
import com.example.nowingo.mobilesteward.entity.FileInfo;
import com.example.nowingo.mobilesteward.manager.FileManager;
import com.example.nowingo.mobilesteward.manager.FileManager2;
import com.example.nowingo.mobilesteward.manager.ToolsManager;
import com.example.nowingo.mobilesteward.tools.CommonUtil;
import com.example.nowingo.mobilesteward.tools.FileTypeUtil;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by NowINGo on 2016/11/28.
 */
public class Activity_file_message extends BaseActivity {
        TextView textView_number,textView_size;
        ListView listView;
        Button button_delete;

        View top;
        String title;

        ArrayList<FileInfo> arrayList;
        FileMessageListViewAdapter fileMessageListViewAdapter;

        int number;
        long size;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public void setLayout() {
            setContentView(R.layout.activity_file_message);
        }

        @Override
        public void inidata() {
            Intent intent =getIntent();
            title = intent.getStringExtra("title");
            switch (title){
                case "全部":
                    arrayList=FileManager2.getFileManager().getAnyFileList();
                    size=FileManager2.getFileManager().getAnyFileSize();
                    break;
                case "文档":
                    arrayList=FileManager2.getFileManager().getTxtFileList();
                    size=FileManager2.getFileManager().getTxtFileSize();
                    break;
                case "视频":
                    arrayList=FileManager2.getFileManager().getVideoFileList();
                    size= FileManager2.getFileManager().getVideoFileSize();
                    break;
                case "音频":
                    arrayList=FileManager2.getFileManager().getAudioFileList();
                    size= FileManager2.getFileManager().getAudioFileSize();
                    break;
                case "图像":
                    arrayList=FileManager2.getFileManager().getImageFileList();
                    size= FileManager2.getFileManager().getImageFileSize();
                    break;
                case "压缩包":
                    arrayList=FileManager2.getFileManager().getZipFileList();
                    size= FileManager2.getFileManager().getZipFileSize();
                    break;
                case "程序包":
                    arrayList=FileManager2.getFileManager().getApkFileList();
                    size= FileManager2.getFileManager().getApkFileSize();
                    break;
            }
        }

        @Override
        public void iniview() {
            top = findViewById(R.id.top_phone_file_message);
            listView = (ListView) findViewById(R.id.activity_file_message_lv);
            textView_number = (TextView) findViewById(R.id.activity_file_message_tv_number);
            textView_size = (TextView) findViewById(R.id.activity_file_message_tv_size);
            button_delete = (Button) findViewById(R.id.activity_file_message_btn_delete);
        }

        @Override
        public void setview() {
            ToolsManager.setTitle(top,this,R.mipmap.btn_homeasup_default,title);

            fileMessageListViewAdapter = new FileMessageListViewAdapter(Activity_file_message.this);
            ArrayList<FileInfo> arrayListfinal = new ArrayList<>();
            for (int i = 0; i <arrayList.size() ; i++) {
                if (arrayList.get(i).getFile().length()>0){
                    arrayListfinal.add(arrayList.get(i));
                }
            }
            fileMessageListViewAdapter.setArrayList(arrayListfinal);
//            for (int i = 0; i <arrayList.size() ; i++) {
//                fileMessageListViewAdapter.Remove();
//            }
            listView.setAdapter(fileMessageListViewAdapter);
            number = arrayList.size();
            textView_number.setText(number+"个");
            textView_size.setText(CommonUtil.getFileSize(size));


            button_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<FileInfo> fileInfos= (ArrayList<FileInfo>) fileMessageListViewAdapter.getArrayList();
                    ArrayList<FileInfo> delFileInfos=new ArrayList<FileInfo>();
                    for (int i=0;i<fileInfos.size();i++){
                        FileInfo fi=fileInfos.get(i);
                        if(fi.isSelect()){
                            delFileInfos.add(fi);
                        }
                    }

                    // 删除所选中的文件
                    for (int i = 0; i < delFileInfos.size(); i++) {
                        FileInfo fileInfo = delFileInfos.get(i);
                        File file = fileInfo.getFile();
                        long filesize = file.length();
                        if (file.delete()) {
                            fileMessageListViewAdapter.getArrayList().remove(fileInfo);
                            FileManager2.getFileManager().getAnyFileList().remove(fileInfo);
                            FileManager2.getFileManager().setAnyFileSize(FileManager2.getFileManager().getAnyFileSize() - filesize);
                            size=FileManager2.getFileManager().getAnyFileSize();
                            switch (fileInfo.getFileType()) {
                                case "txt":
                                    FileManager2.getFileManager().getTxtFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setTxtFileSize(FileManager2.getFileManager().getTxtFileSize() - filesize);
                                    size=FileManager2.getFileManager().getTxtFileSize();
                                    break;
                                case  "audio":
                                    FileManager2.getFileManager().getAudioFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setAudioFileSize(FileManager2.getFileManager().getAudioFileSize() - filesize);
                                    size=FileManager2.getFileManager().getAudioFileSize();
                                    break;
                                case  "video":
                                    FileManager2.getFileManager().getVideoFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setVideoFileSize(FileManager2.getFileManager().getVideoFileSize() - filesize);
                                    size=FileManager2.getFileManager().getVideoFileSize();
                                    break;
                                case  "image":
                                    FileManager2.getFileManager().getImageFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setImageFileSize(FileManager2.getFileManager().getImageFileSize() - filesize);
                                    size=FileManager2.getFileManager().getImageFileSize();
                                    break;
                                case  "apk":
                                    FileManager2.getFileManager().getApkFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setApkFileSize(FileManager2.getFileManager().getApkFileSize() - filesize);
                                    size=FileManager2.getFileManager().getApkFileSize();
                                    break;
                                case  "zip":
                                    FileManager2.getFileManager().getZipFileList().remove(fileInfo);
                                    FileManager2.getFileManager().setZipFileSize(FileManager2.getFileManager().getZipFileSize() - filesize);
                                    size=FileManager2.getFileManager().getZipFileSize();
                                    break;
                            }
                            if (title.equals("全部")){
                                size=FileManager2.getFileManager().getAnyFileSize();
                            }
                        }
                    }
//                    for (int i = 0; i <arrayList.size() ; i++) {
//                        if (arrayList.get(i).isSelect()){
//                            long filesize = arrayList.get(i).getFile().length();
//                            FileManager2.getFileManager().deleteFile(arrayList.get(i).getFile());
//
//                            FileInfo fileInfo = arrayList.get(i);
//                            for (int i = 0; i <arrayList.size() ; i++) {
//                                fileMessageListViewAdapter.Remove();
//                            }
//
//                            switch (title) {
//                                case "全部":
//                                    FileManager2.getFileManager().getAnyFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setAnyFileSize(FileManager2.getFileManager().getAnyFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getAnyFileSize();
//                                    break;
//                                case "文档":
//                                    FileManager2.getFileManager().getTxtFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setTxtFileSize(FileManager2.getFileManager().getTxtFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getTxtFileSize();
//                                    break;
//                                case  "音频":
//                                    FileManager2.getFileManager().getVideoFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setVideoFileSize(FileManager2.getFileManager().getVideoFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getVideoFileSize();
//                                    break;
//                                case  "视频":
//                                    FileManager2.getFileManager().getAudioFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setAudioFileSize(FileManager2.getFileManager().getAudioFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getAudioFileSize();
//                                    break;
//                                case  "图像":
//                                    Log.i("msg",filesize+"");
//                                    Log.i("msg",size+"");
//                                    FileManager2.getFileManager().getImageFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setImageFileSize(FileManager2.getFileManager().getImageFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getImageFileSize();
//                                    Log.i("msg",size+"");
//                                    break;
//                                case  "程序包":
//                                    FileManager2.getFileManager().getApkFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setApkFileSize(FileManager2.getFileManager().getApkFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getApkFileSize();
//                                    break;
//                                case  "压缩包":
//                                    FileManager2.getFileManager().getZipFileList().remove(fileInfo);
//                                    FileManager2.getFileManager().setZipFileSize(FileManager2.getFileManager().getZipFileSize() - filesize);
//                                    size=FileManager2.getFileManager().getZipFileSize();
//                                    break;
//                            }
//                        }
//                    }

                    fileMessageListViewAdapter.notifyDataSetChanged();
                    number = arrayList.size();
                    textView_number.setText(number+"个");
                    textView_size.setText(CommonUtil.getFileSize(size));
                }
            });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //getFileType(arrayList.get(position).getFile());
                    String type1 = FileTypeUtil.getMIMEType(arrayList.get(position).getFile());
                    Intent intent = new Intent();
                    intent.setAction(intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.fromFile(arrayList.get(position).getFile()),type1);
                    Toast.makeText(Activity_file_message.this,type1+"",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            });

            listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView view, int scrollState) {
                    switch (scrollState){
                        case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时
                            fileMessageListViewAdapter.setFling(true);
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
                            fileMessageListViewAdapter.setFling(false);
                            break;
                        case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时
                            fileMessageListViewAdapter.setFling(false);
                            fileMessageListViewAdapter.notifyDataSetChanged();
                            break;
                    }
                }

                @Override
                public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                }
            });


        }

 }


