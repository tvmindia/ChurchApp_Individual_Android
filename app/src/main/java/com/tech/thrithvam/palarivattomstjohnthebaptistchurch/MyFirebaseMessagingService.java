package com.tech.thrithvam.palarivattomstjohnthebaptistchurch;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        //   Log.d(TAG, "From: " + remoteMessage.getFrom());
        //  Log.d(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
        String title;
        String description;
        if(remoteMessage.getData()!=null) {
            title = remoteMessage.getData().get("title") == null ? "" : remoteMessage.getData().get("title");
            description = remoteMessage.getData().get("body") == null ? "" : remoteMessage.getData().get("body");
            DatabaseHandler db = DatabaseHandler.getInstance(this);
         /*   db.InsertNotificationIDs(remoteMessage.getMessageId(),
                    title,
                    description,
                    Long.toString(remoteMessage.getSentTime()));
*/
            //Notification----------------------
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MyFirebaseMessagingService.this);
            mBuilder.setSmallIcon(R.mipmap.ic_launcher);
            mBuilder.setContentTitle(title);
            mBuilder.setContentText(description);
            Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            mBuilder.setSound(alarmSound);
            Intent resultIntent = new Intent(MyFirebaseMessagingService.this, Notifications.class);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(MyFirebaseMessagingService.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setAutoCancel(true);
            mNotificationManager.notify((int)remoteMessage.getSentTime(), mBuilder.build());
        }
    }
}
