package com.oneissue;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

import com.onesignal.notifications.IActionButton;
import com.onesignal.notifications.IDisplayableMutableNotification;
import com.onesignal.notifications.INotificationReceivedEvent;
import com.onesignal.notifications.INotificationServiceExtension;

import java.util.HashMap;

public class NotificationServiceExtension implements INotificationServiceExtension {

    @Override
    public void onNotificationReceived(INotificationReceivedEvent event) {
        Context context = event.getContext();
        IDisplayableMutableNotification notification = event.getNotification();

        int notificationId = notification.getAndroidNotificationId();

        // this is an example of how to modify the notification by changing the background color to blue
        notification.setExtender(builder -> {
            //  Show Call UI Intent
            Intent fullScreenIntent = new Intent(context, CallUIActivity.class)
                    .setAction(Constants.ACTION_SHOW_INCOMING_CALL_UI)
                    .putExtra("notificationId", notificationId);

            PendingIntent fullScreenPendingIntent = PendingIntent.getActivity(context, 0,
                    fullScreenIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            // Answer Call Intent
//            Intent answerIntent = new Intent(context, MainActivity.class)
//                    .setAction(Constants.ACTION_ANSWER_CALL_INTENT)
//                    .putExtra("notificationId", notificationId);

//            PendingIntent answerPendingIntent = PendingIntent.getActivity(context, 0,
//                    answerIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

//            Intent declineIntent = new Intent(context, SKCallKeepDeclineService.class)
//                    .setAction(Constants.ACTION_END_CALL)
//                    .putExtra("notificationId", notificationId);
//
//            PendingIntent declinePendingIntent = PendingIntent.getService(context, 0,
//                    declineIntent, PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            builder
//                    .addAction(R.drawable.ic_launcher, "Accept", answerPendingIntent)
//                    .addAction(R.drawable.ic_launcher_background, "Decline", declinePendingIntent)
                    .setCategory(NotificationCompat.CATEGORY_CALL)
//                    .setColorized(true)
                    .setContentIntent(fullScreenPendingIntent)
                    .setFullScreenIntent(fullScreenPendingIntent, true);

            return builder;
        });
    }
}
