package com.mrswimmer.shiftwatch.domain.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.mrswimmer.shift.domain.service.SendResultService;
import com.mrswimmer.shiftwatch.R;
import com.mrswimmer.shiftwatch.presentation.activity.MainActivity;

import java.util.Map;


public class FCMService extends FirebaseMessagingService {

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static int num = 0;
    public static int count = 0;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.i("code", "data " + remoteMessage.getData().toString());
        Map<String, String> data = remoteMessage.getData();
        String fio = data.get("first") + " " + data.get("second") + " " + data.get("third");
        String id = data.get("id");
        num++;
        count++;
        sendNotification(id, fio);
    }

    private void sendNotification(String id, String fio) {

        Intent intentYes = new Intent(this, SendResultService.class);
        intentYes.putExtra("result", 1);
        intentYes.putExtra("id", id);
        intentYes.putExtra("num", num);
        intentYes.putExtra("count", count);
        PendingIntent pendingIntentYes = PendingIntent.getService(this, 0, intentYes, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intentNo = new Intent(this, SendResultService.class);
        intentNo.putExtra("result", 0);
        intentNo.putExtra("id", id);
        intentNo.putExtra("num", num);
        intentNo.putExtra("count", count);
        PendingIntent pendingIntentNo = PendingIntent.getService(this, 0, intentNo, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.drawable.icon))
                .setContentTitle(this.getString(R.string.app_name))
                .setContentText(fio)
                .setAutoCancel(true)
                .addAction(R.drawable.ic_yes, "Верно", pendingIntentYes)
                .addAction(R.drawable.ic_no, "Неверно", pendingIntentNo)
                .setContentIntent(pendingIntent);

        Log.i("code", "num " + num);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(num, notificationBuilder.build());
    }
}
