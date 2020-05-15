package com.example.termplannerapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

public class AppAlerts extends BroadcastReceiver {
    private static final String CHANNEL_ID = "test";
    public static final String CHANNEL_TERM_DATES = "Dates for terms";
    //    public static final String CHANNEL_COURSE_DATES = "Dates for courses";
//    public static final String CHANNEL_ASSESSMENT_DATES = "Date for assessments";
    static int notificationID;
    private static final String channel_id = "test";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Toast.makeText(context,intent.getStringExtra("key"),Toast.LENGTH_LONG).show();

        createNotificationChannel(context);

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_TERM_DATES)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentText(intent.getStringExtra("key")).build();
        //     .setContentTitle("Test of Notification with an id of : " + notificationID).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        assert notificationManager != null;
        notificationManager.notify(notificationID++, notification);
    }

    private void createNotificationChannel(Context context) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            CharSequence name = context.getResources().getString(R.string.channel_name);
//            String description = context.getString(R.string.channel_description);
//            int importance = NotificationManager.IMPORTANCE_DEFAULT;
//            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
//            channel.setDescription(description);
//            // Register the channel with the system; you can't change the importance
//            // or other notification behaviors after this
//            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
//            assert notificationManager != null;
//            notificationManager.createNotificationChannel(channel);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(CHANNEL_TERM_DATES, "channel1",
                    NotificationManager.IMPORTANCE_HIGH
            );

            NotificationManager manager = context.getSystemService(NotificationManager.class);
            assert manager != null;
            manager.createNotificationChannel(channel1);

        }
    }
}
