package com.arihant.edurite.ui.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.arihant.edurite.util.Util.togglePassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivityChangePasswordBinding;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    private Activity activity;
    private ProgressDialog progressDialog;
    private Session session;
    private ActivityChangePasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChangePasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        progressDialog = new ProgressDialog(activity);

        binding.backArrow.setOnClickListener(v -> onBackPressed());

        binding.imagePasswordEye.setOnClickListener(view -> togglePassword(binding.edtPassword, binding.imagePasswordEye));
        binding.imageNewPasswordEye.setOnClickListener(view -> togglePassword(binding.edtNewPassword, binding.imageNewPasswordEye));

        binding.textSave.setOnClickListener(v -> validate());
    }

    private void validate() {
        if (binding.edtPassword.getText().toString().isEmpty()) {
            binding.edtPassword.setError("Can't be Empty!");
            binding.edtPassword.requestFocus();
        } else if (binding.edtNewPassword.getText().toString().isEmpty()) {
            binding.edtNewPassword.setError("Can't be Empty!");
            binding.edtNewPassword.requestFocus();
        } else {
            changePassword(session.getUserId(), binding.edtPassword.getText().toString().trim(), binding.edtNewPassword.getText().toString().trim());
        }
    }

    private void changePassword(String userId, String oldPassword, String newPassword) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.changePassword(userId, oldPassword, newPassword).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getMsg().trim().equalsIgnoreCase("Password not Match")) {
                                Snackbar.make(binding.getRoot(), "Incorrect Old Password", Snackbar.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(activity, "Password Change Successful!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            if (response.body().getMsg().trim().equalsIgnoreCase("Password not Match"))
                                Snackbar.make(binding.getRoot(), "Incorrect Old Password", Snackbar.LENGTH_LONG).show();
                            else Snackbar.make(binding.getRoot(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}