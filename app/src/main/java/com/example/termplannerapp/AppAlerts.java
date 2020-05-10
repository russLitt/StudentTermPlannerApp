package com.example.termplannerapp;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class AppAlerts extends Application {
    public static final String CHANNEL_TERM_DATES = "Dates for terms";
    public static final String CHANNEL_COURSE_DATES = "Dates for courses";
    public static final String CHANNEL_ASSESSMENT_DATES = "Date for assessments";

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
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel2 = new NotificationChannel(
                    CHANNEL_COURSE_DATES,
                    "channel2",
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationChannel channel3 = new NotificationChannel(
                    CHANNEL_ASSESSMENT_DATES,
                    "channel3",
                    NotificationManager.IMPORTANCE_HIGH
            );

            channel1.setDescription("This channel is for term start and end dates");
            channel2.setDescription("This channel is for course start and end dates");
            channel3.setDescription("This channel is for assessment due dates");

            NotificationManager manager = getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);
        }
    }
}
