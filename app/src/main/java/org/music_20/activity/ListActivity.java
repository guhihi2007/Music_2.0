package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
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
    private ImageView back_btn, addlist_btn;
    private RecyclerView recyclerView;
    public static String title_name=null;
    private  ArrayList<Folder> dblist;
    private TextView action_tv;
    private CommonRecycleAdapter adapter;
    private Intent intent;
    private FloatingActionButton set_btn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        findView();
        setListener();
    }


    @Override
    public void findView() {
        intent =getIntent();
        dblist=(ArrayList<Folder>)intent.getSerializableExtra("dblist");
        back_btn = (ImageView) findViewById(R.id.back_btn);
        addlist_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.list_reclv);
        action_tv =(TextView) findViewById(R.id.action_tv);
        set_btn=(FloatingActionButton)findViewById(R.id.set_btn);
    }

    @Override
    public void setListener() {
        title_name=intent.getStringExtra("title_name");
        action_tv.setText(title_name);
        back_btn.setOnClickListener(this);
        set_btn.setOnClickListener(this);
        addlist_btn.setImageResource(R.mipmap.add);
        addlist_btn.setOnClickListener(this);
        adapter = new ListAdapter(this,this);
        adapter.setDatas(dblist);//把数据库拿出的list添加到adapter
        if (dblist.size()>0)set_btn.setVisibility(View.VISIBLE);
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
                Intent add = new Intent();
                add.setClass(this,AddListAcitvity.class);
                startActivityForResult(add,RequestCode);//startActivityForResult
                break;
            case R.id.set_btn:
                Intent set = new Intent();
                set.setClass(this,SetActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("dblist",dblist);
//                set.putExtras(bundle);
                set.putExtra("title_name", "设置");
                startActivity(set);
                break;
        }
    }

    @Override
    public void OnCommonClickListener(View v, int position) {
        String dirname = dblist.get(position).getName();
        Intent intent = new Intent();
        intent.setClass(this, SongListActivtiy.class);
        intent.putExtra("title_name", dirname);
        Log.v("gpp", "进入播放列表:" + dirname);
        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {


        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data==null) return;
        String result = data.getStringExtra("list_name");//从返回的activity Intent中取数据
        Folder file = new Folder(result);
        dblist.add(file);
        adapter.setDatas(dblist);
        if (dblist.size()>0)set_btn.setVisibility(View.VISIBLE);
        Log.v("gpp", "新建播放列表后List大小:" + dblist.size());
    }
}
