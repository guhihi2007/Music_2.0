package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.base.CommonClickListener;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/20.
 */

public class SubDireAcitvity extends Activity implements InitView, CommonClickListener{
    private RecyclerView recyclerView;

    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private DireAdapter adapter;
    private ArrayList<Data> list;
    private Handler handler;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdire);
        Log.v("gpp", "SubDireActivity_onCreate方法");
        intent = getIntent();
        findView();
        setListener();
        requestData();
    }

    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        serach_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.dire_reclv);
        adapter = new DireAdapter(this,this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    private void requestData() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle=msg.getData();
                list=(ArrayList<Data>) bundle.get("threadList");
                adapter.setDatas(list);//更新Recycleview数据
                Log.v("gpp", "Handler:" + list.size());
            }
        };
        final String path=intent.getStringExtra("dirpath");
        ScanFile scanFile= new ScanFile();
        scanFile.startScan(path,handler);
    }

    @Override
    public void setListener() {
        action_tv.setText(intent.getStringExtra("dirname"));
        serach_btn.setVisibility(View.INVISIBLE);
        action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void OnCommonClickListener(View v, int position) {
        String dirpath = list.get(position).getPath();
        String dirname = list.get(position).getName();
        Log.v("gpp", "Sub___OnCommonClickListener:" + dirpath);
        Intent intent = new Intent();
        intent.setClass(this, SubDireAcitvity.class);
        intent.putExtra("dirpath", dirpath);
        intent.putExtra("dirname", dirname);
        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }


}

