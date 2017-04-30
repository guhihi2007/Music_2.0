package org.music_20.service;

import android.app.AlarmManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import static android.provider.Settings.System.ALARM_ALERT;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyBroadcastReceiver extends BroadcastReceiver {
    private ReceiverCallBack callBack;

    public void setCallBack(ReceiverCallBack callBack) {
        this.callBack = callBack;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ALARM_ALERT)) {//不支持
            Log.v("gpp", "onReceive闹铃响了");
        } else if (action.equals(AudioManager.ACTION_AUDIO_BECOMING_NOISY)) {
            Log.v("gpp", "onReceive耳机拔了");
            callBack.offhook();
        }
        TelephonyManager manager = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        int status = manager.getCallState();
        switch (status) {
            case TelephonyManager.CALL_STATE_RINGING://来电时调用
                Log.v("gpp", "onReceive来电了");
                callBack.ringing();
//            case TelephonyManager.CALL_STATE_OFFHOOK://此状态，来电会立即调用，接听后也会调用，基本上无用
//                break;
//            case TelephonyManager.CALL_STATE_IDLE://挂机后,拔耳机后都调用
//                Log.v("gpp", "onReceive空闲了");
//                callBack.idle();
//                break;
        }

    }
}
