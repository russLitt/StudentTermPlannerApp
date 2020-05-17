package com.example.termplannerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AppAlerts extends BroadcastReceiver {
    public static final String CHANNEL_DATES = "alert dates";
    static int notificationID;

    @Override
    public void onReceive(Context context, Intent intent) {

        createNotificationChannel(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_DATES)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(intent.getStringExtra("key"))
                .setChannelId(CHANNEL_DATES).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(notificationID++, notification);
    }

    private void createNotificationChannel(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_DATES, "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
        }
    }
}
