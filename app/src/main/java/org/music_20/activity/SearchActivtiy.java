package org.music_20.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.database.modify.DB_ModifyPlayList;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SearchActivtiy extends Activity implements InitView, CommonClickListener {
    public static final int SearchMusicCode = 2;
    private RecyclerView recyclerView;
    public ArrayList<Data> list;
    private ImageView serach_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private ScanFile.CheckFileCallBack callback;
    private CheckDialog dialog;
    private SearchAdapter adapter;
    private Handler handler;
    private String mp3 = ".mp3";
    public static String  playlist_name;
    private Context context;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dire);
        intent = getIntent();
        findView();
        setListener();
        serachData();
        this.context=this;
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
//                            Log.v("gpp", "对话框dismiss");
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        playlist_name=intent.getStringExtra("title_name");
        action_tv.setText(intent.getStringExtra("dirname"));
        serach_btn.setVisibility(View.INVISIBLE);
        serach_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Song> chosesonglist = new ArrayList<>();
                //遍历map，存入ArrayList
                Map map = adapter.getChoseMap();
                for (Object music : map.values()) {
                    Song song = (Song) music;
                    chosesonglist.add(song);
                    DB_ModifyPlayList dbModifyPlayList = new DB_ModifyPlayList(SearchActivtiy.this,playlist_name);
                    dbModifyPlayList.add_songToList(song);
                }
                /**
                 * 未存入数据库，未完待续
                 */
                Intent back = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("song", chosesonglist);
                back.putExtras(bundle);
                back.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//设置activity启动模式，这里用的模式是回到已经启动过的activity界面，并清除他上面所有的activity
                back.putExtra("title_name", playlist_name);
                back.setClass(SearchActivtiy.this, SongListActivtiy.class);
                startActivity(back);
//                Log.v("gpp", "返回播放列表："+playlist_name);
            }
        });
        action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnCommonClickListener(View v, int position) {
        String type = list.get(position).getType();
        String dirpath = list.get(position).getPath();
        String dirname = list.get(position).getName();
        if (mp3.equals(type)) {
            adapter.setSelected(position);//点击选中或不选中
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, SearchActivtiy.class);
        intent.putExtra("dirpath", dirpath);
        intent.putExtra("dirname", dirname);
        intent.putExtra("title_name",playlist_name);
        Log.v("gpp", "进入文件夹:" + dirname);
//        Log.v("gpp", "发送播放列表来源:" + playlist_name);
        startActivity(intent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        if (adapter.getHaxbox()) {
            serach_btn.setImageResource(R.mipmap.ok);
            serach_btn.setVisibility(View.VISIBLE);
            adapter.setShowbox();//长按设置显示checkbox
//        adapter.setSelected(position);//当前长按item被选中
            adapter.notifyDataSetChanged();
        }
        return true;
    }

}
