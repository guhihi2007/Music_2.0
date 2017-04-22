package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import org.music_20.base.CommonClickListener;
import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.base.CommonRecycleAdapter;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ListActivity extends Activity implements InitView, View.OnClickListener ,CommonClickListener{

    private ImageView back_btn, search_btn;
    private RecyclerView recyclerView;
    private FloatingActionButton float_btn;
    private String path;
    private ArrayList<Data>  songslist;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Intent intent = getIntent();
        songslist = (ArrayList) intent.getSerializableExtra("dir");
        findView();
        setListener();
    }


    @Override
    public void findView() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        back_btn = (ImageView) findViewById(R.id.back_btn);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.list_reclv);
        float_btn = (FloatingActionButton) findViewById(R.id.float_btn);
    }

    @Override
    public void setListener() {
        back_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        float_btn.setOnClickListener(this);
        CommonRecycleAdapter adapter = new DireAdapter(this,this);
        adapter.setDatas(songslist);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
                intent.putExtra("dirname", "查找文件");
                intent.putExtra("dirpath", path);
                startActivity(intent);
                break;
            case R.id.float_btn:
                break;
        }
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }
}
