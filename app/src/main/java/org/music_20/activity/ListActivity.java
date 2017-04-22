package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.base.CommonClickListener;
import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.base.CommonRecycleAdapter;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class ListActivity extends Activity implements InitView, View.OnClickListener ,CommonClickListener{
    public static final int RequestCode=1;
    private ImageView back_btn, search_btn;
    private RecyclerView recyclerView;
    private FloatingActionButton float_btn;
    private String path;
    private ArrayList<Folder> dblist;
    private TextView action_tv;
    private CommonRecycleAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findView();
        setListener();
    }


    @Override
    public void findView() {
        Intent intent =getIntent();
        dblist=(ArrayList<Folder>)intent.getSerializableExtra("dblist");
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        back_btn = (ImageView) findViewById(R.id.back_btn);
        search_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.list_reclv);
        float_btn = (FloatingActionButton) findViewById(R.id.float_btn);
        action_tv =(TextView) findViewById(R.id.action_tv);
    }

    @Override
    public void setListener() {
        action_tv.setText("播放列表");
        back_btn.setOnClickListener(this);
        search_btn.setOnClickListener(this);
        float_btn.setOnClickListener(this);
        adapter = new ListAdapter(this,this);
        adapter.setDatas(dblist);//把数据库拿出的list添加到adapter
        RecyclerView.LayoutManager manager = new GridLayoutManager(this,4);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
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
                Intent add = new Intent();
                add.setClass(this,AddListAcitvity.class);
                startActivityForResult(add,RequestCode);//startActivityForResult
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        String result=data.getStringExtra("list_name");//从返回的activity Intent中取数据
        Folder file= new Folder();
        file.setName(result);
        dblist.add(file);
        adapter.setDatas(dblist);
        Log.v("gpp","List大小:"+dblist.size());
    }


    @Override
    public void OnCommonClickListener(View v, int position) {

    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

}
