package com.arihant.edurite.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.FragmentSettingsBinding;

import org.jetbrains.annotations.NotNull;

public class SettingsFragment extends Fragment {
    private Activity activity;
    private FragmentSettingsBinding binding;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        activity = requireActivity();


        return binding.getRoot();
    }
}