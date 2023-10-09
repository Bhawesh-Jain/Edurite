package com.arihant.edurite.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.os.Bundle;
import android.view.View;

import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.model.KeyPath;
import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ActivityDashboardBinding;
import com.arihant.edurite.ui.fragments.HomeFragment;
import com.arihant.edurite.ui.fragments.InterviewQuestionFragment;
import com.arihant.edurite.ui.fragments.MaterialFragment;
import com.arihant.edurite.ui.fragments.NotificationFragment;
import com.arihant.edurite.ui.fragments.SettingsFragment;

public class DashboardActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;

        setupNavBar();
        changeFragment(0);
    }

    private void setupNavBar() {
        binding.homeAnim.setMinAndMaxProgress(0.0f, 0.6f);
        binding.settingsAnim.setMinAndMaxProgress(0.0f, 0.5f);

        binding.homeAnim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        );

        binding.materialAnim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        );

        binding.questionAnim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        );

        binding.coursesAnim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        );

        binding.settingsAnim.addValueCallback(
                new KeyPath("**"),
                LottieProperty.COLOR_FILTER,
                frameInfo -> new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP)
        );

        binding.rlHome.setOnClickListener(view -> {
            binding.homeAnim.playAnimation();
            changeFragment(0);
        });
        binding.rlMaterial.setOnClickListener(view -> {
            binding.materialAnim.playAnimation();
            changeFragment(1);
        });
        binding.rlCourses.setOnClickListener(view -> {
            binding.coursesAnim.playAnimation();
            changeFragment(2);
        });
        binding.rlSettings.setOnClickListener(view -> {
            binding.settingsAnim.playAnimation();
            changeFragment(3);
        });
        binding.rlQuestion.setOnClickListener(view -> {
            binding.questionAnim.playAnimation();
            changeFragment(4);
        });
    }

    private void changeFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new HomeFragment();

                break;
            case 1:
                fragment = new MaterialFragment();

                break;
            case 2:
                fragment = new NotificationFragment();

                break;
            case 3:
                fragment = new SettingsFragment();

                break;
            case 4:
                fragment = new InterviewQuestionFragment();

                break;
            default:
                new HomeFragment();
        }

        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction().replace(binding.container.getId(), fragment);
            ft.commit();
        }
    }
}