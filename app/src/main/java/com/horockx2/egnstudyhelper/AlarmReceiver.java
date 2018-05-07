package com.horockx2.egnstudyhelper;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.inputmethod.InputContentInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by user on 2018-02-05.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        Notification.Builder builder = new Notification.Builder(context);

        String dialogFileName = DialogManager.GetRandomDialog(context);
//        String dialogFileName = "chapter_1.json";
//        String notificationText = dialogFileName.split(".")[0];

        Intent resultIntent = new Intent(context, DialogActivity.class);
        resultIntent.putExtra("dialogFileName", dialogFileName);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);


        builder.setSmallIcon(R.drawable.ic_stat_name).setNumber(1).setContentTitle("영어공부 할 시간이에요~").setContentText(dialogFileName)
                .setAutoCancel(true).setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE).setContentIntent(contentIntent);

        notificationManager.notify(1, builder.build());
    }
}
