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
 * Created by Administrator on 2017/4/18.
 */

public class SearchActivtiy extends Activity implements InitView, CommonClickListener {
    private RecyclerView recyclerView;
    public ArrayList<Data> list;
    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private ScanFile.CheckFileCallBack callback;
    private CheckDialog dialog;
    private SearchAdapter adapter;
    private Handler handler;

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
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle bundle = msg.getData();
                list = (ArrayList<Data>) bundle.get("threadList");
                adapter.setDatas(list);//更新Recycleview数据

                Log.v("gpp", "Handler接收数据:" + list.size());
            }
        };
        if (callback == null) {
            callback = new ScanFile.CheckFileCallBack() {
                @Override
                public void checkCallBack(Result result) {
                    switch (result) {
                        case doing:
                            break;
                        case done:
                            dialog.dismiss();
                            Log.v("gpp", "对话框dismiss");
                            break;
                    }
                }
            };
        }
        if (dialog == null) {
            dialog = new CheckDialog(this, "扫描中...");
        }
        dialog.show();
        ScanFile ss = new ScanFile();
        ss.startScan(intent.getStringExtra("dirpath"), handler, callback);
    }

    @Override
    public void findView() {
        action_tv = (TextView) findViewById(R.id.action_tv);
        action_back = (ImageView) findViewById(R.id.back_btn);
        serach_btn = (ImageView) findViewById(R.id.search_btn);
        recyclerView = (RecyclerView) findViewById(R.id.dire_reclv);
        adapter = new SearchAdapter(this, this);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        action_tv.setText("查找文件");
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
    public void OnCommonClickListener(View v, int position) {
        String mp3 = ".mp3";
        String type = list.get(position).getType();
        String dirpath = list.get(position).getPath();
        String dirname = list.get(position).getName();
        Log.v("gpp", "Dir___OnCommonClickListener:" + dirpath);

        if (mp3.equals(type)) return;
        Intent intent = new Intent();
        intent.setClass(this, SearchActivtiy.class);
        intent.putExtra("dirpath", dirpath);
        intent.putExtra("dirname", dirname);
        Log.v("gpp", "进入文件夹:" + dirname);
        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }
}
