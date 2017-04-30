package org.music_20.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import org.music_20.activity.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/29.
 */

public class MusicService extends Service implements MediaPlayer.OnCompletionListener {
    private MediaPlayer player;
    private ArrayList<Song> list;
    private Song song;
    private int positon, temp = 0;
    private boolean isListrecycle = true, isRandom = false;

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
            String path = list.get(positon).getPath();
            startPlay(path);
            Log.v("gpp", "list中第 " + positon + " 个播放");
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        player.reset();
        if (isListrecycle) {
            Log.v("gpp", "onCompletion--列表循环模式");
            if (list != null)
                recyclePlay();
        }
        if (isRandom) {
            Log.v("gpp", "onCompletion--随机播放模式");
            if (list != null)
                randomPlay();
        }
    }

    private void randomPlay() {
        Random r = new Random();
        int number = r.nextInt(list.size());
        String path = list.get(number).getPath();
        startPlay(path);
        Log.v("gpp", "list中第 " + number + " 个Random播放");
    }

    public void recyclePlay() {
        next();
//        int temp = 0;
//        try {
//            if (positon < (list.size() - 1)) {
//                positon++;
//                Log.v("gpp", "list中第 temp=" + positon + " 个即将播放");
//                player.reset();
//                player.setDataSource(list.get(positon).getPath());
//            }
//            if (positon == list.size() - 1) {
//                Log.v("gpp", "list播放完成，即将播放第temp=" + positon + "个");
//                player.reset();
//                player.setDataSource(list.get(positon).getPath());
//                positon = 0;
//            }
//            player.prepare();
//            player.start();
//        } catch (IOException e) {
//            Log.v("gpp", "song.getPath获取为空");
//        }
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


    public void play() {
        player.start();
    }

    public void pause() {
        player.pause();
    }

    private void startPlay(String path) {
        player.reset();
        try {
            player.setDataSource(path);
            player.prepare();
            player.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void next() {
        if (positon > list.size() - 1) positon = 0;
        positon++;
        temp = positon;
        if (temp > list.size() - 1) temp = 0;
        Log.v("gpp", "temp:" + temp);
        String path = list.get(temp).getPath();
        startPlay(path);

//        temp = positon;
//        Log.v("gpp", "上一曲:" + temp + " 结束");
//        if (temp == list.size() - 1 || positon > list.size() - 1) {
//            positon = -1;
//            temp = 0;
//        }
//        try {
//            if (temp < list.size() - 1) {
//                positon++;
//                temp = positon;
//                Log.v("gpp", "下一曲:" + temp + " 开始播放");
//                player.reset();
//                player.setDataSource(list.get(temp).getPath());
//                player.prepare();
//                player.start();
//            }
//        } catch (IOException e) {
//            Log.v("gpp", "song.getPath获取为空");
//        }
    }

    public void pre() {
        if (positon == 0) positon = list.size();
        positon--;
        temp = positon;
        Log.v("gpp", "temp:" + temp);
        String path = list.get(temp).getPath();
        startPlay(path);
//        temp = positon;
//        Log.v("gpp", "上一曲:" + temp + " 结束");
//        try {
//            if (temp < list.size()) {
//                positon--;
//                if (positon == -1) {
//                    positon = list.size() - 1;
//                    temp = list.size() - 1;
//                } else {
//                    temp = positon;
//                }
//                Log.v("gpp", "下一曲:" + temp + " 开始播放");
//                player.reset();
//                player.setDataSource(list.get(temp).getPath());
//                player.prepare();
//                player.start();
//            }
//        } catch (IOException e) {
//            Log.v("gpp", "song.getPath获取为空");
//        }
    }


    public void setListrecycle(boolean listrecycle) {
        isListrecycle = listrecycle;
    }

    public boolean isListrecycle() {
        return isListrecycle;
    }

    public void setLoop(boolean isloop) {
        player.setLooping(isloop);
    }

    public boolean isLoop() {
        return player.isLooping();
    }

    public void setRandom(boolean random) {
        this.isRandom = random;
    }

    public boolean isRandom() {
        return isRandom;
    }

    public boolean isPlay() {
        if (list != null)
            return player.isPlaying();
        return false;
    }
}
