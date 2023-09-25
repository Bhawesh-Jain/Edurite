package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivityPrivacyPolicyBinding;
import com.arihant.edurite.models.AboutUsModel;
import com.arihant.edurite.models.PrivacyPolicyModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrivacyPolicyActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityPrivacyPolicyBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPrivacyPolicyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());
        getPrivacyPolicy();
    }

    private void getPrivacyPolicy() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getPrivacyPolicy().enqueue(new Callback<PrivacyPolicyModel>() {
            @Override
            public void onResponse(@NonNull Call<PrivacyPolicyModel> call, @NonNull Response<PrivacyPolicyModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.textDescription.setText(Html.fromHtml(response.body().getPrivacyPolicy().getContent(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<PrivacyPolicyModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}