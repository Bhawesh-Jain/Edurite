package com.arihant.edurite.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityLoginBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;


        binding.textLogin.setOnClickListener(view -> {
            startActivity(new Intent(activity, DashboardActivity.class));
            finish();
        });

    }
}