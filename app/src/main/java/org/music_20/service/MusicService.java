package org.music_20.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import org.music_20.activity.Song;
import org.music_20.database.modify.DB_ModifyPlayList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.provider.Settings.System.ALARM_ALERT;

/**
 * Created by Administrator on 2017/4/29.
 */

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, ReceiverCallBack {
    private MediaPlayer player;
    private ArrayList<Song> list;
    private int positon, temp = 0;
    private boolean isListrecycle = true, isRandom = false;
    private MyBroadcastReceiver receiver;
    private pauseCallBack pauseCallBack;
    private int songDuration, currentPosition;
    private Timer timer = new Timer();
    private PlayMesssageCallBack playMesssageCallBack;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        receiver = new MyBroadcastReceiver();
//        Log.v("gpp", "MusicService启动");
        IntentFilter phone = new IntentFilter();
        phone.addAction("android.intent.action.PHONE_STATE");
        phone.addAction(ALARM_ALERT);
        phone.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(receiver, phone);
//        Log.v("gpp", "注册电话监听" + player);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("gpp", "Service激活");
        list = (ArrayList<Song>) intent.getSerializableExtra("play_list");
        positon = intent.getIntExtra("position", 0);
        if (player != null && list != null) {
            startPlay(list.get(positon));
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
        startPlay(list.get(number));
        Log.v("gpp", "list中第 " + number + " 个Random播放");
    }

    public void recyclePlay() {
        next();
    }

    @Override
    public void onDestroy() {
        player.release();
        player = null;
        unregisterReceiver(receiver);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CoreServiceBinder();
    }

    @Override
    public void ringing() {
        pause();
        pauseCallBack.paused();
    }

    @Override
    public void offhook() {
        pause();
        pauseCallBack.paused();
    }

    @Override
    public void idle() {
        //挂机后,拔耳机后都调用
    }

    public void setPauseCallBack(MusicService.pauseCallBack pauseCallBack) {
        this.pauseCallBack = pauseCallBack;
    }

    public interface pauseCallBack {
        void paused();
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
        if (player.isPlaying())
            player.pause();
    }

    public void startPlay(Song song) {
        player.reset();
        try {
            player.setDataSource(song.getPath());
            player.prepare();
            player.start();
            songDuration = player.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (playMesssageCallBack != null && player.isPlaying()) {
                    currentPosition = player.getCurrentPosition();
                    playMesssageCallBack.currentPosition(currentPosition);
                }
            }
        }, 0, 1000);
//        playMesssageCallBack.songDuration(songDuration);
        Intent send = new Intent();
        send.putExtra("songDuration", songDuration);
        send.putExtra("song_name", song.getName());
        send.setAction("songDuration");
        sendBroadcast(send);
    }

    public void next() {
        if (list != null) {
            if (positon > list.size() - 1) positon = 0;
            positon++;
            temp = positon;
            if (temp > list.size() - 1) temp = 0;
            Log.v("gpp", "temp:" + temp);
            String path = list.get(temp).getPath();
            startPlay(list.get(temp));
        }
    }

    public void pre() {
        if (list != null) {
            if (positon == 0) positon = list.size();
            positon--;
            temp = positon;
            Log.v("gpp", "temp:" + temp);
            String path = list.get(temp).getPath();
            startPlay(list.get(temp));
        }
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
//        if (list != null)
            return player.isPlaying();
//        return false;
    }

    public void seekTo(int s) {
        player.seekTo(s);
    }

    public interface PlayMesssageCallBack {
        void currentPosition(int current);
    }

    public void setPlayMesssageCallBack(PlayMesssageCallBack playMesssageCallBack) {
        this.playMesssageCallBack = playMesssageCallBack;
    }

}
