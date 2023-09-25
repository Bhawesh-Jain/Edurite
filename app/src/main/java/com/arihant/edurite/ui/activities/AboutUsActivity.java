package com.arihant.edurite.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ActivityAboutUsBinding;
import com.arihant.edurite.databinding.ActivityDashboardBinding;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutUsBinding binding;
    private Activity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;


    }
}