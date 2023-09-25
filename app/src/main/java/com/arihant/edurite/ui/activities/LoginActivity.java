package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.util.Util.isValidEmail;
import static com.arihant.edurite.util.Util.togglePassword;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivityLoginBinding;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    public static final String TAG = "CustomTags";
    private Activity activity;
    private ActivityLoginBinding binding;
    private String fcm = "";
    private ProgressDialog progressDialog;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);
        session = new Session(activity);

        binding.textSignup.setOnClickListener(view -> startActivity(new Intent(activity, SignupActivity.class)));
        binding.imagePasswordEye.setOnClickListener(view -> togglePassword(binding.edtPassword, binding.imagePasswordEye));

        binding.textLogin.setOnClickListener(view -> validateLogin());
        fcm = "Hello";
    }

    private void validateLogin() {
        if (binding.edtEmail.getText() != null && !binding.edtEmail.getText().toString().trim().isEmpty() && isValidEmail(binding.edtEmail.getText().toString().trim())) {
            if (binding.edtPassword.getText() != null && !binding.edtPassword.getText().toString().trim().isEmpty()) {
                login(binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim(), fcm);
            } else {
                binding.edtPassword.requestFocus();
                binding.edtPassword.setError("Invalid Password");
            }
        } else {
            binding.edtEmail.requestFocus();
            binding.edtEmail.setError("Invalid Email");
        }
    }

    private void login(String email, String password, String fcm) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.login(email, password, fcm).enqueue(new Callback<LoginModel>() {
            @Override
            public void onResponse(@NonNull Call<LoginModel> call, @NonNull Response<LoginModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            LoginModel.Data data = response.body().getData();

                            session.setEmail(data.getUsername());
                            session.setUserId(data.getId());
                            session.setUserName(data.getName());
                            session.setFCM(fcm);
                            session.setUserImage(data.getProfileImg());

                            startActivity(new Intent(activity, DashboardActivity.class));
                            finish();
                        } else {
                            Snackbar.make(binding.getRoot(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
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