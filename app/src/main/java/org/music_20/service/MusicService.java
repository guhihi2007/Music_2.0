package org.music_20.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.music_20.activity.Song;
import org.music_20.database.modify.DB_ModifyPlayList;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/4/29.
 */

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private ArrayList<Song> list;
    private Song song;
    private int positon;
    int temp = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        Log.v("gpp", "MusicService启动");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("gpp", "Service_onStartCommand激活");
        list = (ArrayList<Song>) intent.getSerializableExtra("play_list");
        positon = intent.getIntExtra("position", 0);
        if (player != null) {
            player.reset();
            try {
                song = list.get(positon);
                player.setDataSource(song.getPath());
                player.prepare();
                player.start();
                Log.v("gpp", "list中第 " + positon + " 个播放");
            } catch (IOException e) {
                Log.v("gpp", "song.getPath获取为空");
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (list != null)
            recyclePlay();
    }

    @Override
    public void onDestroy() {
        player.release();
        player = null;
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CoreServiceBinder();
    }


    public class CoreServiceBinder extends Binder {
        public MusicService getService() {
            return MusicService.this;
        }
    }

    public void recyclePlay() {
        Log.v("gpp", "list中第 " + positon + " 个播放完成");
        int temp = 0;
        // 如果没有设置单曲循环，则播放下一个
//        if (!player.isLooping()) {
        try {
            if (positon < (list.size() - 1)) {
                positon++;
                Log.v("gpp", "list中第 temp=" + positon + " 个即将播放");
                player.reset();
                player.setDataSource(list.get(positon).getPath());
            }
            if (positon == list.size() - 1) {
                Log.v("gpp", "list播放完成，即将播放第temp=" + positon + "个");
                player.reset();
                player.setDataSource(list.get(positon).getPath());
                positon = 0;
            }
            player.prepare();
            player.start();
        } catch (IOException e) {
            Log.v("gpp", "song.getPath获取为空");
        }
//        }
    }

    public void play() {
        if (!player.isPlaying())
            player.start();
    }

    public void pause() {
        if (player.isPlaying())
            player.pause();

    }

    public void setLoop() {
        player.setLooping(true);
    }

    public boolean isPlay() {
        if (list != null)
            return player.isPlaying();
        return false;
    }

    public void next() {
        temp = positon;
        Log.v("gpp", "上一曲:" + temp + " 结束");
        if (temp == list.size() - 1 || positon>list.size()-1) {
            positon = -1;
            temp = 0;
        }
        try {
            if (temp < list.size() - 1) {
                positon++;
//                if (positon > list.size() - 1) {
//                    positon = -1;
//                    temp = 0;
//
//                } else {
                    temp = positon;
//                }
                Log.v("gpp", "下一曲:" + temp + " 开始播放");
                player.reset();
                player.setDataSource(list.get(temp).getPath());
                player.prepare();
                player.start();
            }
        } catch (IOException e) {
            Log.v("gpp", "song.getPath获取为空");
        }
    }

    public void pre() {
        if (player.isPlaying()) {
            player.release();
        }
    }
}
