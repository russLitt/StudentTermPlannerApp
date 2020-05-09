package com.example.termplannerapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppAlerts extends Application {
    public static final String CHANNEL_TERM_DATES = "Dates for terms";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
            CHANNEL_TERM_DATES,
                    "channel1",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel1.setDescription("This channel is for term start and end dates");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);

        }
    }
}
