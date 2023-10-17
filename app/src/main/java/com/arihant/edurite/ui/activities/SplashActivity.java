package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.arihant.edurite.R;
import com.arihant.edurite.util.Session;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.List;

public class SplashActivity extends AppCompatActivity {
    private final int SPLASH_TIMER = 3000;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setStatusBarColor(getColor(R.color.logo_bg));
        session = new Session(this);

        FirebaseMessaging.getInstance().subscribeToTopic("all")
                .addOnCompleteListener(task -> {
                    String msg = "Subscribed";
                    if (!task.isSuccessful()) {
                        msg = "Subscribe failed";
                    }
                    Log.e(TAG, msg);
                });

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            Dexter.withContext(this)
                    .withPermissions(
                            Manifest.permission.POST_NOTIFICATIONS
                    ).withListener(new MultiplePermissionsListener() {
                        @Override
                        public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                            setTimer();
                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                            setTimer();
                        }
                    }).check();
        } else
            setTimer();
    }

    private void setTimer() {
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