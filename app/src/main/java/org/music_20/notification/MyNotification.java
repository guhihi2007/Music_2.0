package org.music_20.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import org.music_20.R;
import org.music_20.activity.Song;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MyNotification {
    private Context context;
    private RemoteViews remoteViews;
    private NotificationManager manager;
    private NotificationCompat.Builder builder;

    public MyNotification(Context context) {
        this.context = context;
        remoteViews = new RemoteViews(context.getPackageName(), R.layout.notification_layout);
        manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        builder = new NotificationCompat.Builder(context);
    }

    public void sendNotification(Song song) {
        remoteViews.setTextViewText(R.id.notify_tv, song.getName());
        Intent playIntent = new Intent();
        playIntent.setAction("play");
        playIntent.putExtra("notify_play", "play");
        PendingIntent pendingPlay = PendingIntent.getBroadcast(context, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notify_play, pendingPlay);


        Intent nextIntent = new Intent();
        nextIntent.setAction("next");
        nextIntent.putExtra("notify_next", "next");
        PendingIntent pendingNext = PendingIntent.getBroadcast(context, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notify_next, pendingNext);


        builder.setSmallIcon(R.mipmap.icon);
        builder.setContentTitle(song.getName());
        builder.setContent(remoteViews);
        /*
        //点击remoteviews启动activity
        Intent intent = new Intent().setClass(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        */
        Notification notification = builder.build();
        manager.notify(0, notification);
    }

    public void changeNotifyStatus() {
        remoteViews.setImageViewResource(R.id.notify_play,R.mipmap.pause);
        builder.setSmallIcon(R.mipmap.icon);
        builder.setContent(remoteViews);
        Notification notification = builder.build();
        manager.notify(0, notification);
    }

}
