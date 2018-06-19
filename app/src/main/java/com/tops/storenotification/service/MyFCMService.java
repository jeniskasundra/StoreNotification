package com.tops.storenotification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.tops.storenotification.MyApplication;
import com.tops.storenotification.R;
import com.tops.storenotification.model.NotificationModel;

import java.util.Calendar;

public class MyFCMService extends FirebaseMessagingService {
    private static final String CHANNEL_NAME = "FCM";
    private int numMessages = 0;
    private static final String CHANNEL_DESC = "Firebase Cloud Messaging";
    private static final String TAG = "MyFirebaseMsgService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("FROM", remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());


        }
        sendNotification(remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "default")
                .setContentTitle(remoteMessage.getData().get("title"))
                .setContentText(remoteMessage.getData().get("message"))
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                // .setContentIntent(pendingIntent)
                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setColor(ContextCompat.getColor(this, R.color.color_accent))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages).setSmallIcon(R.drawable.ic_action_notifications_none);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default", CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.setDescription(CHANNEL_DESC);
            channel.setShowBadge(true);
            channel.canShowBadge();
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});

            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);
        }

        assert notificationManager != null;
        notificationManager.notify(0, notificationBuilder.build());


        NotificationModel notificationModel=new NotificationModel();
        notificationModel.setTitle(remoteMessage.getData().get("title"));
        notificationModel.setMessage(remoteMessage.getData().get("message"));
        notificationModel.setUrl(remoteMessage.getData().get("url"));
        notificationModel.setTime((Calendar.getInstance().getTimeInMillis()) / 1000);
        MyApplication.getInstance().getDB().notificationDao().insertAll(notificationModel);

    }

}
