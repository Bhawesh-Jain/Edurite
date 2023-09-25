package com.arihant.edurite.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.FragmentSettingsBinding;
import com.arihant.edurite.ui.activities.AboutUsActivity;
import com.arihant.edurite.ui.activities.PrivacyPolicyActivity;
import com.arihant.edurite.ui.activities.TermsActivity;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment {
    private Activity activity;
    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        activity = requireActivity();

        binding.llAboutUs.setOnClickListener(view -> startActivity(new Intent(activity, AboutUsActivity.class)));
        binding.llPrivacyPolicy.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));
        binding.llTerms.setOnClickListener(view -> startActivity(new Intent(activity, TermsActivity.class)));

        return binding.getRoot();
    }
}