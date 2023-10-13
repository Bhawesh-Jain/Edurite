package com.arihant.edurite.ui.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ActivityMyRatingsBinding;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;

public class MyRatingsActivity extends AppCompatActivity {
    private Activity activity;
    private ProgressDialog progressDialog;
    private Session session;
    private ActivityMyRatingsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMyRatingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        progressDialog = new ProgressDialog(activity);


    }
}