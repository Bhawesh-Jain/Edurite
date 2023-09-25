package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivityTermsBinding;
import com.arihant.edurite.models.PrivacyPolicyModel;
import com.arihant.edurite.models.TermsModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TermsActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityTermsBinding binding;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTermsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);

        binding.icBack.setOnClickListener(view -> onBackPressed());
        getTerms();
    }

    private void getTerms() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getTerms().enqueue(new Callback<TermsModel>() {
            @Override
            public void onResponse(@NonNull Call<TermsModel> call, @NonNull Response<TermsModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.textDescription.setText(Html.fromHtml(response.body().getTermsCondition().getTermConditionText(), Html.FROM_HTML_MODE_COMPACT));
                        } else {
                            Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<TermsModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}