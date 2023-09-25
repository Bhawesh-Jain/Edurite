package com.arihant.edurite.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.arihant.edurite.R;
import com.arihant.edurite.util.Session;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIMER = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(getColor(R.color.logo_bg));
        Session session = new Session(this);
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