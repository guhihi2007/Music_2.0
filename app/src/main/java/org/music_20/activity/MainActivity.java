package org.music_20.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.database.modify.DB_ModifyPlayList;
import org.music_20.service.MusicService;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InitView, View.OnClickListener, CommonClickListener{

    private ImageView play, next, previous, model, list;
    private RecyclerView mani_recycle;
    private ArrayList<Folder> dblist;
    private ArrayList<Song> songlist;
    private MusicService musicService;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        findView();
        setListener();
        startCoreService();
    }


    @Override
    public void findView() {
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        model = (ImageView) findViewById(R.id.model);
        list = (ImageView) findViewById(R.id.list);
        mani_recycle = (RecyclerView) findViewById(R.id.mani_recycle);
    }

    @Override
    public void setListener() {
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        model.setOnClickListener(this);
        list.setOnClickListener(this);
        CommonRecycleAdapter adapter = new SearchAdapter(this, this);
        mani_recycle.setLayoutManager(new LinearLayoutManager(this));
        mani_recycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mani_recycle.setItemAnimator(new DefaultItemAnimator());
        mani_recycle.setHasFixedSize(true);
        mani_recycle.setAdapter(adapter);
        songlist = (ArrayList<Song>) intent.getSerializableExtra("play_list");
        adapter.setDatas(songlist);
        if (songlist != null)
            play.setImageResource(R.mipmap.pause);
    }

    @Override
    public void OnCommonClickListener(View v, int position) {
        play.setImageResource(R.mipmap.pause);
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, MusicService.class);
        Bundle serviceBundle = new Bundle();
        serviceBundle.putSerializable("play_list", songlist);
        serviceIntent.putExtras(serviceBundle);
        serviceIntent.putExtra("position", position);
        startService(serviceIntent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

    private void startCoreService() {
        final Intent server = new Intent();
        server.setClass(this, MusicService.class);
        bindService(server, conn, BIND_AUTO_CREATE);
//        bindService(server, new CoreConnection(), BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
                if (songlist != null) {
                    if (musicService.isPlay()) {
                        musicService.pause();
                        play.setImageResource(R.mipmap.paly);
                    } else {
                        musicService.play();
                        play.setImageResource(R.mipmap.pause);
                    }
                }
                break;
            case R.id.next:
                if (songlist != null) {
                    musicService.next();
                    play.setImageResource(R.mipmap.pause);
                }
                break;
            case R.id.previous:
                if (songlist != null) {
                    musicService.pre();
                    play.setImageResource(R.mipmap.pause);
                }
                break;
            case R.id.model:
                if (musicService.isListrecycle()) {
                    musicService.setListrecycle(false);
                    musicService.setLoop(true);
                    model.setImageResource(R.mipmap.single);
                    Toast.makeText(this, "单曲循环", Toast.LENGTH_LONG).show();

                } else if (musicService.isLoop()) {
                    musicService.setLoop(false);
                    musicService.setRandom(true);
                    model.setImageResource(R.mipmap.random);
                    Toast.makeText(this, "随机播放", Toast.LENGTH_LONG).show();

                } else if (musicService.isRandom()) {
                    musicService.setRandom(false);
                    musicService.setListrecycle(true);
                    model.setImageResource(R.mipmap.recycle);
                    Toast.makeText(this, "列表循环", Toast.LENGTH_LONG).show();


                }
                break;
            case R.id.list:
                Log.v("gpp", "点击list_btn");
                DB_ModifyPlayList dbModifyPlayList = new DB_ModifyPlayList(this, null);
                dblist = dbModifyPlayList.getPlayList();//从数据库取播放列表数据
                Log.v("gpp", "读取DB播放列表:" + (dblist == null ? null : dblist.size()));
                Intent intent = new Intent();
                intent.setClass(this, ListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("dblist", dblist);//用bundle存储传递到下一个activity
                intent.putExtras(bundle);
                intent.putExtra("title_name", "播放列表");
                startActivity(intent);
                break;
        }
    }

    public ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.CoreServiceBinder serviceBinder = (MusicService.CoreServiceBinder) service;
            musicService = serviceBinder.getService();
            musicService.setPauseCallBack(new MusicService.pauseCallBack() {
                @Override
                public void paused() {
                    play.setImageResource(R.mipmap.paly);
                }
            });

        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

}
