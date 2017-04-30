package org.music_20.activity;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import org.music_20.service.MusicService;

/**
 * Created by Administrator on 2017/4/29.
 */

public  class CoreConnection implements ServiceConnection {
    public MusicService musicService;
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        MusicService.CoreServiceBinder binder=(MusicService.CoreServiceBinder)service;
        musicService = binder.getService();
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        musicService=null;
    }
}
