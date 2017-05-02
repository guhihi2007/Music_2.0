package org.music_20.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by Administrator on 2017/4/19.
 */

public class ScanFile {
    //接口回调成员变量
    private CallBack callBack;
    private ArrayList<Data> threadList;
    //开启线程扫描，并且把数据传给adapter
    public void startScan(final String path, final Handler handler, final CheckFileCallBack callBack) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callBack.checkCallBack(CheckFileCallBack.Result.doing);
                threadList = scan(path);
                Message msg = new Message();
                Bundle bundle = new Bundle();
                bundle.putSerializable("threadList", threadList);
                msg.setData(bundle);
                handler.sendMessage(msg);
                callBack.checkCallBack(CheckFileCallBack.Result.done);
            }
        }).start();
    }
    //回调返回结果
    public void CallbackScan(final String path) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                callBack.getData(scan(path));
            }
        }).start();
    }

    public ScanFile() {
    }

    public ArrayList<Data> scan(String path) {
        Data data=null;
        java.util.ArrayList<Data> list = new java.util.ArrayList<>();
        File file = new File(path);
        File[] files = file.listFiles();
        if (files != null && files.length > 0) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory() && !files[i].isHidden()) {
                    String filename = files[i].getName();
                    String filepath = files[i].getAbsolutePath();
//                    int count = scan(filepath).size();
                    int count =0;
                    String type = "dir";
                    data = new Folder(filename, filepath,count);
                    list.add(data);
                } else if (files[i].isFile() && files[i].getName().endsWith(".mp3") && !(files[i].isHidden())) {
                    String fall_name = files[i].getName();
                    String name = fall_name.substring(0, fall_name.lastIndexOf("."));
                    String songpath = files[i].getAbsolutePath();
                    long length = files[i].length();
                    String size = calculateSizeMB(length);
                    String type = ".mp3";
                    data = new Song(name, songpath, size,fall_name);
                    list.add(data);
                }
            }
            Collections.sort(list, new sortByName());
        }
        return list;
    }


    //接口回调赋值
    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    //扫描结束的接口回调
    interface CallBack {
        void getData(ArrayList<Data> datas);
    }


    //是否正在扫描文件接口
    interface CheckFileCallBack {
        void checkCallBack(Result result);

        enum Result {
            doing,//正在扫描
            done//扫描结束
        }
    }


    //对list排序的实现类，根据名字排序
    static class sortByName implements Comparator<Data> {

        @Override
        public int compare(Data o1, Data o2) {
            return o1.getName().compareTo(o2.getName());
        }
    }

    //把文件的大小file.length转换成MB
    public static String calculateSizeMB(long size) {
        if (size == 0) {
            return 0 + "MB";
        }
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(1d * size / (1024 * 1024)) + "MB";

    }
}
// for (File file1: files){
//                if (file1.isDirectory()) {
//                    String filename=file1.getName();
//                    String filepath=file1.getAbsolutePath();
//                    Data data= new Data(filename,filepath);
//                    list.add(data);
//                    Log.v("gpp",filename);
//                }
//            }