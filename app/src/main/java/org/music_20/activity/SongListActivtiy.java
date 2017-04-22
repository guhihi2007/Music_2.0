package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.InitView;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SongListActivtiy extends Activity implements InitView, View.OnClickListener, CommonClickListener {
    private RecyclerView recyclerView;
    public ArrayList<Song> list;
    private ImageView search_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private ScanFile.CheckFileCallBack callback;
    private CheckDialog dialog;
    private SearchAdapter adapter;
    private Handler handler;
    private String path;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dire);
        intent = getIntent();
        findView();
        setListener();
        serachData();
    }

    private void serachData() {
        //读取数据库保存的song
    }

    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.dire_reclv);
        adapter = new SearchAdapter(this, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        action_tv.setText(intent.getStringExtra("dirname"));
        search_btn.setOnClickListener(this);
        action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

        //点击音乐后播放


//        String mp3 = ".mp3";
//        String type = list.get(position).getType();
//        String dirpath = list.get(position).getPath();
//        String dirname = list.get(position).getName();
//        Log.v("gpp", "Dir___OnCommonClickListener:" + dirpath);
//
//        if (mp3.equals(type)) return;
//        Intent intent = new Intent();
//        intent.setClass(this, SongListActivtiy.class);
//        intent.putExtra("dirpath", dirpath);
//        intent.putExtra("dirname", dirname);
//        Log.v("gpp", "进入文件夹:" + dirname);
//        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_btn:
                finish();
                break;
            case R.id.search_btn:
                Intent intent = new Intent();
                intent.setClass(this, SearchActivtiy.class);
                intent.putExtra("dirname", "添加歌曲");
                intent.putExtra("dirpath", path);
                startActivity(intent);
                break;
        }
    }
}
