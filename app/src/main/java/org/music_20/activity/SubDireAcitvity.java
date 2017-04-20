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
 * Created by Administrator on 2017/4/20.
 */

public class SubDireAcitvity extends Activity implements InitView, CommonClickListener {
    private RecyclerView recyclerView;
    private ArrayList<Data> list, dirs;
    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private ScanFile scanFile;
    private Intent intent;
    private CommonRecycleAdapter adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subdire);
        Log.v("gpp", "SubDireActivity_onCreate方法");
        intent = getIntent();
        list = (ArrayList) intent.getSerializableExtra("dir");
//        Log.v("gpp", "intent获取文件list:" + list.size());
        findView();
        setListener();
    }

    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        serach_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.dire_reclv);
        if (list==null|| list.size()==0){
        }
        adapter = new DireAdapter(this, list, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
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
        scanFile = new ScanFile();
        scanFile.setCallBack(new ScanFile.CallBack() {
            @Override
            public void getData(ArrayList<Data> datas) {
                dirs = datas;
                Log.v("gpp", "Sub___OnCommonClickListener:" + datas.size());
            }
        });
        scanFile.startScan(dirpath);
        Intent intent = new Intent();
        intent.setClass(this, new SubDireAcitvity().getClass());
        Bundle bundle = new Bundle();
        bundle.putSerializable("dir", dirs);
        intent.putExtras(bundle);
        intent.putExtra("dirname", dirname);
        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if (dirs != null) {
//            dirs.clear();
//            Log.v("gpp", "list有数据，清空list");
//        }
    }
}

