package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import org.music_20.base.CommonRecycleAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class DireActivtiy extends Activity implements InitView, CommonClickListener {
    private RecyclerView recyclerView;
    public ArrayList<Data> list, dirs;
    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private ScanFile scanFile;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dire);
        getMyData();
        findView();
        setListener();
    }

    public void getMyData(){
        intent = getIntent();
        list = (ArrayList) intent.getSerializableExtra("dir");
        Log.v("gpp", "DireActivity_intent获取文件list:" + (list==null?"list为空":list.size()));

    }
    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        serach_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.dire_reclv);
        CommonRecycleAdapter adapter = new DireAdapter(this, list, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        action_tv.setText("查找文件");
        serach_btn.setVisibility(View.INVISIBLE);
        action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnCommonClickListener(View v, int position) {
        String dirpath = list.get(position).getPath();
        String dirname = list.get(position).getName();
        Log.v("gpp", "Dir___OnCommonClickListener:" + dirpath);
        Intent intent = new Intent();
        intent.setClass(this, SubDireAcitvity.class);
        intent.putExtra("dirpath", dirpath);
        intent.putExtra("dirname", dirname);
        Log.v("gpp", "进入文件夹:"+dirname);
        startActivity(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (dirs != null) {
            dirs.clear();
            Log.v("gpp", "dir有数据，清空dir");
        }
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }
}
