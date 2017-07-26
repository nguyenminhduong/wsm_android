package com.framgia.wsm.data.source.remote.api.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.framgia.wsm.R;
import com.framgia.wsm.screen.login.LoginActivity;
import com.framgia.wsm.screen.main.MainActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by ASUS on 14/06/2017.
 */

public class FireBaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "FireBaseMessageService";
    private static final String TITLE = "WSM";
    private static final String NOTIFICATION_NUMBER = "NOTIFICATION_NUMBER";
    private static final String MESSAGE = "message";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            sendNotification(remoteMessage.getData().get(MESSAGE));
        }
        if (remoteMessage.getNotification() != null) {
            sendNotification(remoteMessage.getNotification().getBody());
        }
    }

    private void sendNotification(String messageBody) {
        SharedPreferences prefs =
                getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationNumber = prefs.getInt(NOTIFICATION_NUMBER, 0);

        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this).setSmallIcon(R.drawable.ic_notifications)
                        .setContentTitle(TITLE)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setSound(uri)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationNumber, notificationBuilder.build());

        SharedPreferences.Editor editor = prefs.edit();
        notificationNumber++;
        editor.putInt(NOTIFICATION_NUMBER, notificationNumber);
        editor.apply();
    }
}
