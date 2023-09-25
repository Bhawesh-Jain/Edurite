package com.arihant.edurite.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ActivitySignupBinding;

public class SignupActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;


    }
}