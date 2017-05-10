package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.music_20.Gpp;
import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.InitView;
import org.music_20.database.modify.DB_ModifyPlayList;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/25.
 */

public class SetActivity extends Activity implements InitView, View.OnClickListener, CommonClickListener {

    public static final int RequestCode = 1;
    private ImageView set_delete, search_btn, back_btn;
    private RecyclerView recyclerView;
    public static String title_name = null;
    private ArrayList<Folder> dblist;
    private TextView action_tv;
    private CommonRecycleAdapter adapter;
    private Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set);
        findView();
        setListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:

                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }

    @Override
    public void findView() {
        intent = getIntent();
        search_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.set_ry);
        back_btn = (ImageView) findViewById(R.id.back_btn);
        action_tv = (TextView) findViewById(R.id.action_tv);
    }

    @Override
    public void setListener() {
        title_name = intent.getStringExtra("title_name");
        action_tv.setText(title_name);
        search_btn.setImageResource(R.mipmap.ok);
        back_btn.setOnClickListener(this);
        DB_ModifyPlayList dbModifyPlayList = new DB_ModifyPlayList(this, null);
        dblist = dbModifyPlayList.getPlayList();//从数据库取播放列表数据
        adapter = new SetAdapter(this, this);
        adapter.setDatas(dblist);
        Gpp.v("SetActivityList大小:" + dblist.size());
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }
}
