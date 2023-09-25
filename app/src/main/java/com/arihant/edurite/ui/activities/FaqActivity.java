package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.FaqAdapter;
import com.arihant.edurite.databinding.ActivityFaqBinding;
import com.arihant.edurite.databinding.ActivityPrivacyPolicyBinding;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.models.PrivacyPolicyModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FaqActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityFaqBinding binding;
    private ProgressDialog progressDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFaqBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());
        getFaq();
    }

    private void getFaq() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getFaq().enqueue(new Callback<FaqModel>() {
            @Override
            public void onResponse(@NonNull Call<FaqModel> call, @NonNull Response<FaqModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                            binding.recyclerView.setAdapter(new FaqAdapter(activity, response.body().getFaq()));
                        } else {
                            Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<FaqModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}