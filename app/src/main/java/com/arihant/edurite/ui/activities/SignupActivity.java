package com.arihant.edurite.ui.activities;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;
import static com.arihant.edurite.util.Util.isValidEmail;
import static com.arihant.edurite.util.Util.togglePassword;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.ActivitySignupBinding;
import com.arihant.edurite.models.SignupModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private Activity activity;
    private ActivitySignupBinding binding;
    private ProgressDialog progressDialog;
    private Session session;
    private String image = "", fcm = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);
        session = new Session(activity);

        binding.textSignup.setOnClickListener(view -> validateSignup());
        binding.imagePasswordEye.setOnClickListener(view -> togglePassword(binding.edtPassword, binding.imagePasswordEye));
        binding.imageConPasswordEye.setOnClickListener(view -> togglePassword(binding.edtConfirmPassword, binding.imageConPasswordEye));

        image = "";
        fcm = "Hello";

    }

    private void validateSignup() {
        if (binding.edtName.getText() != null && !binding.edtName.getText().toString().trim().isEmpty()) {
            if (binding.edtEmail.getText() != null && !binding.edtEmail.getText().toString().trim().isEmpty() && isValidEmail(binding.edtEmail.getText().toString().trim())) {
                if (binding.edtPassword.getText() != null && !binding.edtPassword.getText().toString().trim().isEmpty()) {
                    if (binding.edtConfirmPassword.getText() != null && !binding.edtConfirmPassword.getText().toString().trim().isEmpty()) {
                        if (binding.edtConfirmPassword.getText().toString().trim().equals(binding.edtPassword.getText().toString().trim())) {
                            signup(binding.edtName.getText().toString().trim(), binding.edtEmail.getText().toString().trim(), binding.edtPassword.getText().toString().trim(), image, fcm);
                        } else {
                            binding.edtConfirmPassword.requestFocus();
                            binding.edtPassword.setError("Passwords Don't Match!");
                            binding.edtConfirmPassword.setError("Passwords Don't Match!");
                        }
                    } else {
                        binding.edtConfirmPassword.requestFocus();
                        binding.edtConfirmPassword.setError("Invalid Password");
                    }
                } else {
                    binding.edtPassword.requestFocus();
                    binding.edtPassword.setError("Invalid Password");
                }
            } else {
                binding.edtEmail.requestFocus();
                binding.edtEmail.setError("Invalid Email");
            }
        } else {
            binding.edtName.requestFocus();
            binding.edtName.setError("Name can't be Empty!");
        }
    }

    private void signup(String name, String email, String password, String image, String fcm) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.signup(name, email, password, image, fcm).enqueue(new Callback<SignupModel>() {
            @Override
            public void onResponse(@NonNull Call<SignupModel> call, @NonNull Response<SignupModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            SignupModel.Data data = response.body().getData();

                            session.setEmail(data.getUsername());
                            session.setUserId(data.getId());
                            session.setUserName(data.getName());
                            session.setFCM(fcm);
                            session.setUserImage(data.getProfileImg());

                            startActivity(new Intent(activity, DashboardActivity.class)
                                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK));
                        } else {
                            String message = response.body().getMsg();
                            if (message.equalsIgnoreCase("username already registerd..!")) message = "Email Already Used! Please Login or Signup using another Email!";
                            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<SignupModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}