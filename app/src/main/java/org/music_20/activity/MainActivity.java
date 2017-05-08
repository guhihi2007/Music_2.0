package org.music_20.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.music_20.base.CommonClickListener;
import org.music_20.base.CommonRecycleAdapter;
import org.music_20.base.InitView;
import org.music_20.R;
import org.music_20.database.modify.DB_ModifyPlayList;
import org.music_20.service.MusicService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements InitView, View.OnClickListener, CommonClickListener {

    private ImageView play, next, previous, model, list;
    private RecyclerView mani_recycle;
    private ArrayList<Folder> dblist;
    private ArrayList<Song> songlist, last_song;
    private MusicService musicService;
    private Intent intent;
    private TextView show_name, start_time, end_time;
    private CommonRecycleAdapter adapter;
    private songReceiver songReceiver;
    private SeekBar seekbar;
    private SimpleDateFormat format = new SimpleDateFormat("mm:ss");
    private String title_name;
    private Boolean isplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = getIntent();
        songReceiver = new songReceiver();
        registerReceive();
        findView();
        startCoreService();
        setListener();
        bindCoreService();
    }


    @Override
    public void findView() {
        play = (ImageView) findViewById(R.id.play);
        next = (ImageView) findViewById(R.id.next);
        previous = (ImageView) findViewById(R.id.previous);
        model = (ImageView) findViewById(R.id.model);
        list = (ImageView) findViewById(R.id.list);
        start_time = (TextView) findViewById(R.id.start_time);
        end_time = (TextView) findViewById(R.id.end_time);
        show_name = (TextView) findViewById(R.id.show_name);
        seekbar = (SeekBar) findViewById(R.id.seekbar);
        mani_recycle = (RecyclerView) findViewById(R.id.mani_recycle);
    }

    @Override
    public void setListener() {
        play.setOnClickListener(this);
        next.setOnClickListener(this);
        previous.setOnClickListener(this);
        model.setOnClickListener(this);
        list.setOnClickListener(this);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    musicService.seekTo(progress * 1000);
                    Log.v("gpp", "SeekBarChanged:" + progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        adapter = new SearchAdapter(this, this);
        mani_recycle.setLayoutManager(new LinearLayoutManager(this));
        mani_recycle.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mani_recycle.setItemAnimator(new DefaultItemAnimator());
        mani_recycle.setHasFixedSize(true);
        mani_recycle.setAdapter(adapter);
        songlist = (ArrayList<Song>) intent.getSerializableExtra("play_list");
        DB_ModifyPlayList dmp = new DB_ModifyPlayList(this, null);
        last_song = dmp.getLast();
        if (songlist != null) {
            play.setImageResource(R.mipmap.pause);
            adapter.setDatas(songlist);
            Log.v("gpp", "上一页面传来List:" + songlist.size());
        } else {
            if (last_song != null) {
                adapter.setDatas(last_song);
                Log.v("gpp", "最后一次播放List:" + last_song.size());
            }
        }
    }

    private void bindCoreService() {
        final Intent server = new Intent();
        server.setClass(this, MusicService.class);
        bindService(server, conn, BIND_AUTO_CREATE);
        Log.v("gpp", "bindCoreService:" + musicService);

    }

    private void registerReceive() {
        Log.v("gpp", "注册Receiver");
        IntentFilter filter = new IntentFilter();
        filter.addAction("songDuration");
        registerReceiver(songReceiver, filter);
    }

    @Override
    protected void onResume() {
        int postion = intent.getIntExtra("position", 0);
        title_name = intent.getStringExtra("title_name");
        if (title_name != null) {
            DB_ModifyPlayList dmp = new DB_ModifyPlayList(this, title_name);
            songlist = dmp.getSongList();
            adapter.setDatas(songlist);
        }
        if (songlist != null)
            show_name.setText(songlist.get(postion).getName());
        Log.v("gpp", "onResume:" + musicService);

//        if (musicService != null && musicService.isPlay())
//            play.setImageResource(R.mipmap.pause);
        super.onResume();
    }

    @Override
    public void OnCommonClickListener(View v, int position) {

        play.setImageResource(R.mipmap.pause);
        Intent serviceIntent = new Intent();
        serviceIntent.setClass(this, MusicService.class);
        Bundle serviceBundle = new Bundle();
        if (songlist == null) {
            show_name.setText(last_song.get(position).getName());
            serviceBundle.putSerializable("play_list", last_song);
        } else {
            show_name.setText(songlist.get(position).getName());
            serviceBundle.putSerializable("play_list", songlist);
        }
        serviceIntent.putExtras(serviceBundle);
        serviceIntent.putExtra("position", position);
        startService(serviceIntent);
    }

    @Override
    public boolean OnCommonLongClickListener(View v, int position) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play:
//                Notify();
                Log.v("gpp", "play");
                if (songlist != null || last_song != null) {
                    if (musicService.isPlay()) {
                        musicService.pause();
                        play.setImageResource(R.mipmap.paly);
                        Toast.makeText(this, "暂停", Toast.LENGTH_LONG).show();
                    } else {
                        musicService.play();
                        if (songlist == null) {
                            musicService.startPlay(last_song.get(0));
                        }
                        play.setImageResource(R.mipmap.pause);
                        Toast.makeText(this, "播放", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.next:
                Log.v("gpp", "next");
                if (songlist != null || last_song != null) {
                    musicService.next();
                    play.setImageResource(R.mipmap.pause);
                }
                break;
            case R.id.previous:
                Log.v("gpp", "previous");
                if (songlist != null || last_song != null) {
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
                Intent intent = new Intent();
                intent.setClass(this, ListActivity.class);
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
            musicService.setPlayMesssageCallBack(new MusicService.PlayMesssageCallBack() {
                @Override
                public void currentPosition(int current) {
                    Message msg = new Message();
                    msg.what = current;
                    handler.sendMessage(msg);
                }
//                @Override
//                public void songDuration(int duration) {
//                    end_time.setText(format.format(duration));
//                    Log.v("gpp", "Override:" + duration);
//                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicService = null;
        }
    };
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int time = msg.what;
            start_time.setText(format.format(time));
            seekbar.setProgress(time / 1000);
        }
    };

    public void startCoreService() {
        if (musicService == null) {
            Intent serviceIntent = new Intent();
            serviceIntent.setClass(this, MusicService.class);
            Bundle serviceBundle = new Bundle();
            if (last_song != null && songlist == null) {
                serviceBundle.putSerializable("play_list", last_song);
                Log.v("gpp", "startCoreService:" + last_song.size());
            }
            serviceIntent.putExtras(serviceBundle);
            startService(serviceIntent);
        }
    }

    private void Notify() {
        RemoteViews rv = new RemoteViews(this.getPackageName(), R.layout.notification_layout);
        rv.setTextViewText(R.id.notify_tv, "song_name");

        NotificationCompat.Builder NCB = new NotificationCompat.Builder(this);
        NCB.setSmallIcon(R.id.music_item_im);
        NCB.setContent(rv);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, NCB.build());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
        unregisterReceiver(songReceiver);
        if (songlist != null && songlist.size() > 0) {
            DB_ModifyPlayList dmp = new DB_ModifyPlayList(this, title_name);
            dmp.saveLast(songlist);
        }
        Log.v("gpp", "onDestroy:" + title_name);
    }

    public class songReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
//            if (intent.getAction().equals("songDuration")) {
            int Duration = intent.getIntExtra("songDuration", 0);
            String name = intent.getStringExtra("song_name");
            end_time.setText(format.format(Duration));
            seekbar.setMax(Duration / 1000);
            show_name.setText(name);
            Log.v("gpp", "onReceive:" + Duration);
//            }
        }
    }
}
