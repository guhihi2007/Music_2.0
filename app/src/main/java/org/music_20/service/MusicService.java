package org.music_20.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.telephony.TelephonyManager;
import android.util.Log;

import org.music_20.activity.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static android.provider.Settings.System.ALARM_ALERT;

/**
 * Created by Administrator on 2017/4/29.
 */

public class MusicService extends Service implements MediaPlayer.OnCompletionListener, ReceiverCallBack {
    private MediaPlayer player;
    private ArrayList<Song> list;
    private Song song;
    private int positon, temp = 0;
    private boolean isListrecycle = true, isRandom = false;
    private MyBroadcastReceiver receiver;

    @Override
    public void onCreate() {
        super.onCreate();
        player = new MediaPlayer();
        player.setOnCompletionListener(this);
        receiver = new MyBroadcastReceiver();
        Log.v("gpp", "MusicService启动");
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PHONE_STATE");
        filter.addAction(ALARM_ALERT);
        filter.addAction(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(receiver, filter);
        Log.v("gpp", "注册电话监听" + player);
        receiver.setCallBack(this);
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
    }

    @Override
    public void offhook() {
        pause();
        //此回调，来电会立即调用，接听后也会调用，基本上无用
    }

    @Override
    public void idle() {
        //挂机后,拔耳机后都调用
//        play();
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
    }

    public void pre() {
        if (positon == 0) positon = list.size();
        positon--;
        temp = positon;
        Log.v("gpp", "temp:" + temp);
        String path = list.get(temp).getPath();
        startPlay(path);
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

//    class MyBroadcastReceiver extends BroadcastReceiver {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            TelephonyManager manager = (TelephonyManager) getSystemService(Service.TELEPHONY_SERVICE);
//            int status = manager.getCallState();
//            switch (status) {
//                case TelephonyManager.CALL_STATE_RINGING:
//                    Log.v("gpp","来电了"+player);
//                    player.pause();
//                case TelephonyManager.CALL_STATE_IDLE:
//                    Log.v("gpp","空闲了"+player);
//                    player.start();
//                    break;
//            }
//        }
//    }
}
