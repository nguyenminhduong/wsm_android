package com.framgia.wsm.data.source.remote.api.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import com.framgia.wsm.R;
import com.framgia.wsm.screen.main.MainActivity;
import com.framgia.wsm.utils.Constant;
import com.framgia.wsm.utils.binding.ColorManagers;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import static com.framgia.wsm.utils.Constant.EXTRA_NOTIFICATION_REQUEST_TYPE;

/**
 * Created by ASUS on 14/06/2017.
 */

public class FireBaseMessageService extends FirebaseMessagingService {
    private static final String TAG = "FireBaseMessageService";
    private static final String NOTIFICATION_NUMBER = "NOTIFICATION_NUMBER";
    private static final String MESSAGE = "message";
    private static final String SHOW = "show";
    private static final String TYPE = "type";
    private static final String PERMISSION = "permission";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            String messageResponse = remoteMessage.getData().get(MESSAGE);
            String notificationType = remoteMessage.getData().get(TYPE);
            String requestType =
                    SHOW.equals(notificationType) ? remoteMessage.getData().get(PERMISSION)
                            : Constant.BLANK;
            sendNotification(messageResponse, requestType);
        }
    }

    private void sendNotification(String messageBody, String requestType) {
        SharedPreferences prefs =
                getSharedPreferences(MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        int notificationNumber = prefs.getInt(NOTIFICATION_NUMBER, 0);
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra(EXTRA_NOTIFICATION_REQUEST_TYPE, requestType);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this).setCategory(NotificationCompat.CATEGORY_PROMO)
                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),
                                R.mipmap.ic_launcher_round))
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_view, getString(R.string.view_detail),
                                pendingIntent)
                        .setSmallIcon(R.drawable.ic_notification_w)
                        .setContentText(messageBody)
                        .setAutoCancel(true)
                        .setColor(Color.parseColor(ColorManagers.COLOR_GREEN_DARK))
                        .setSound(uri)
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setVisibility(NotificationCompat.VISIBILITY_PRIVATE);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(notificationNumber, notificationBuilder.build());

        SharedPreferences.Editor editor = prefs.edit();
        notificationNumber++;
        editor.putInt(NOTIFICATION_NUMBER, notificationNumber);
        editor.apply();
    }
}
