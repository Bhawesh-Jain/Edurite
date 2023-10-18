package com.arihant.edurite.notification;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.ui.activities.SplashActivity;
import com.arihant.edurite.util.Session;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "NOTIFICATION";


    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);
        Session session = new Session(getApplicationContext());

        session.setFCM(token);
        sendRegistrationToServer(token, session);
    }

    private void sendRegistrationToServer(String fcm, Session session) {
        if (session.isLoggedIn()) {
            ApiService apiService = RetrofitClient.getClient(getApplicationContext());
            apiService.addFcm(session.getUserId(), fcm).enqueue(new Callback<LoginModel>() {
                @Override
                public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                    Log.d(TAG, "sendRegistrationToServer() called with: call = [" + call + "], response = [" + response.code() + "]");
                }

                @Override
                public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                }
            });
        }
    }


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());
        createNotificationChannel();
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            sendNotification(remoteMessage);
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotificationBody(remoteMessage);
        }
    }

    private void sendNotificationBody(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        String imageTitle = remoteMessage.getNotification().getTitle();
        String imageDescription = remoteMessage.getNotification().getBody();

        Bitmap myBitmap = null;

        Notification notification = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_logo_icon)
                .setContentTitle(imageTitle)
                .setContentText(imageDescription)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(imageDescription))
//                .setLargeIcon(myBitmap)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(myBitmap)
//                        .bigLargeIcon(null))
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Random random = new Random();
        int notificationId = random.nextInt(500);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(notificationId, notification);
    }

    public void sendNotification(RemoteMessage remoteMessage) {
        Intent intent = new Intent(this, SplashActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);


        String imageTitle = remoteMessage.getData().get("title");
        String imageDescription = remoteMessage.getData().get("description");

        Bitmap myBitmap = null;

        Notification notification = new NotificationCompat.Builder(this, getString(R.string.default_notification_channel_id))
                .setSmallIcon(R.drawable.ic_logo_icon)
                .setContentTitle(imageTitle)
                .setContentText(imageDescription)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
//                .setLargeIcon(myBitmap)
//                .setStyle(new NotificationCompat.BigPictureStyle()
//                        .bigPicture(myBitmap)
//                        .bigLargeIcon(null))
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        Random random = new Random();
        int notificationId = random.nextInt(500);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        notificationManager.notify(notificationId, notification);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is not in the Support Library.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = ("General Notification");
            String description = ("Notifications targeted to everyone! Important!");
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(getString(R.string.default_notification_channel_id), name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
