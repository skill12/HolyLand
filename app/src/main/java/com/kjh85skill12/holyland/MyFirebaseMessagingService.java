package com.kjh85skill12.holyland;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Map<String, String> datas = remoteMessage.getData();
        if(datas!=null && datas.size()>0){
            String noti = datas.get("noti");

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationCompat.Builder builder = null;
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                String channelId = "ch01";
                String ChannelName = "channel01";
                NotificationChannel notificationChannel = new NotificationChannel(channelId,ChannelName,NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(notificationChannel);

                builder = new NotificationCompat.Builder(this,channelId);
            }else {
                builder= new NotificationCompat.Builder(this,null);
            }

            builder.setSmallIcon(R.drawable.ic_whatshot_black_24dp);
            builder.setContentTitle("HolyLand - 이스라엘 성지순례");
            builder.setContentText(noti);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.ic_launcher_foreground));

            Intent intent = new Intent(this,MainActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(this,200,intent,PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);

            builder.setAutoCancel(true);

            notificationManager.notify(1,builder.build());


        }
    }
}
