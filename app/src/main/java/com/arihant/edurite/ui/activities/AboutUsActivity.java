package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivityAboutUsBinding;
import com.arihant.edurite.databinding.ActivityDashboardBinding;
import com.arihant.edurite.models.AboutUsModel;
import com.arihant.edurite.models.SignupModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutUsActivity extends AppCompatActivity {
    private ActivityAboutUsBinding binding;
    private Activity activity;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAboutUsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());
        aboutUs();
    }

    private void aboutUs() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getAboutUs().enqueue(new Callback<AboutUsModel>() {
            @Override
            public void onResponse(@NonNull Call<AboutUsModel> call, @NonNull Response<AboutUsModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.textDescription.setText(Html.fromHtml(response.body().getAbout().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<AboutUsModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}