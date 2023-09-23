package com.arihant.edurite.ui.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.adapter.MaterialListAdapter;
import com.arihant.edurite.adapter.NotificationListAdapter;
import com.arihant.edurite.databinding.FragmentNotificationBinding;

public class NotificationFragment extends Fragment {
    private Activity activity;
    private FragmentNotificationBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        activity = requireActivity();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(new NotificationListAdapter());

        return binding.getRoot();
    }
}