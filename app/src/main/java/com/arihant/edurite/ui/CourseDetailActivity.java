package com.arihant.edurite.ui;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.arihant.edurite.databinding.ActivityCourseDetailBinding;

public class CourseDetailActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityCourseDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

    }
}