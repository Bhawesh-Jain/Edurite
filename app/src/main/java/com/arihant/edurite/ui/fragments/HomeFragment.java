package com.arihant.edurite.ui.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.adapter.CourseListAdapter;
import com.arihant.edurite.databinding.FragmentHomeBinding;
import com.arihant.edurite.ui.activities.CourseDetailActivity;

public class HomeFragment extends Fragment {
    private Activity activity;
    private FragmentHomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        activity = requireActivity();

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(new CourseListAdapter());

        binding.textHeading.setOnClickListener(view -> startActivity(new Intent(activity, CourseDetailActivity.class)));

        return binding.getRoot();
    }
}