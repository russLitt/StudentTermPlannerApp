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
import static androidx.core.content.ContextCompat.getSystemService;

public class AppAlerts extends BroadcastReceiver {
    public static final String CHANNEL_TERM_DATES = "Dates for terms";
    public static final String CHANNEL_COURSE_DATES = "Dates for courses";
    public static final String CHANNEL_ASSESSMENT_DATES = "Date for assessments";
    static int notificationID;
    String channel_id="test";

//    @Override
//    public void onCreate() {
//        super.onCreate();
//
//        createNotificationChannel();
//    }

    @Override
    public void onReceive(Context context, Intent intent) {
   //     createNotificationChannel();
//
        Notification notification = new NotificationCompat.Builder(context, CHANNEL_TERM_DATES)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(intent.getStringExtra("key")).build();
//                .setContentTitle("Test of Notification with an id of :"+ notificationID).build();
//
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(notificationID++, notification);
    }

    private void createNotificationChannel(Context context, String CHANNEL_ID) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getResources().getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);


//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel1 = new NotificationChannel(
//                    CHANNEL_TERM_DATES,
//                    "channel1",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            NotificationChannel channel2 = new NotificationChannel(
//                    CHANNEL_COURSE_DATES,
//                    "channel2",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//            NotificationChannel channel3 = new NotificationChannel(
//                    CHANNEL_ASSESSMENT_DATES,
//                    "channel3",
//                    NotificationManager.IMPORTANCE_HIGH
//            );
//
//            channel1.setDescription("This channel is for term start and end dates");
//            channel2.setDescription("This channel is for course start and end dates");
//            channel3.setDescription("This channel is for assessment due dates");

//            NotificationManager manager = (NotificationManager) getSystemService(NotificationManager.class);
//            assert manager != null;
//            manager.createNotificationChannel(channel1);
        }
    }

}
