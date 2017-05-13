package org.music_20.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.music_20.Gpp;
import org.music_20.R;
import org.music_20.base.CommonClickListener;
import org.music_20.base.InitView;
import org.music_20.database.modify.DB_ModifyPlayList;
import org.music_20.service.MusicService;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/18.
 */

public class SongListActivtiy extends Activity implements InitView, View.OnClickListener, CommonClickListener {
    public static final int SearchMusicCode = 2;
    private RecyclerView recyclerView;
    public ArrayList<Song> list;
    private ImageView search_btn, action_back;
    private TextView action_tv;
    private Intent intent;
    private SearchAdapter adapter;
    public static String path, title_name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dire);
        intent = getIntent();
        findView();
        setListener();
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
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setListener() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            path = Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        title_name = intent.getStringExtra("title_name");
        action_tv.setText(title_name);
        DB_ModifyPlayList dbModifyPlayList = new DB_ModifyPlayList(this, title_name);
        list = dbModifyPlayList.getSongList();
        adapter.setSong(list);
        search_btn.setOnClickListener(this);
        action_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

        /**
         * 点击音乐后播放,回到控制页面
         */
//        String name = list.get(position).getName();
//        Song song = list.get(position);
//        Gpp.v("点击音乐:" + name);

        Intent activity = new Intent();
        activity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        activity.setClass(this, MainActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("play_list", list);
        activity.putExtra("title_name",title_name);
        activity.putExtra("position",position);
        activity.putExtras(bundle);
        startActivity(activity);

        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, MusicService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable("play_list", list);
        String aa = String.valueOf(position);
        serviceIntent.putExtra("position",aa);
        serviceIntent.putExtras(serviceBundle);
        startService(serviceIntent);

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
                intent.putExtra("title_name", title_name);
                startActivity(intent);
//                startActivityForResult(intent, SearchMusicCode);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == resultCode) {
            String result = data.getStringExtra("playlist_name");//从返回的activity Intent中取数据
            Gpp.v("数据返回添加到播放列表" + result);
        }
    }

}
