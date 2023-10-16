package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.arihant.edurite.R;
import com.arihant.edurite.util.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(getColor(R.color.logo_bg));
        Session session = new Session(this);

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed";
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                    Log.e(TAG, msg);
                });

        new Handler().postDelayed(() -> {
            Intent intent;
            if (session.isLoggedIn())
                intent = new Intent(SplashActivity.this, DashboardActivity.class);
            else intent = new Intent(SplashActivity.this, LoginActivity.class);

            startActivity(intent);
            finish();
        }, SPLASH_TIMER);
    }
}