package com.arihant.edurite.ui.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.arihant.edurite.util.Util.decodeImage;
import static com.arihant.edurite.util.Util.encodeImageBitmap;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.ReviewListAdapter;
import com.arihant.edurite.databinding.ActivityProfileBinding;
import com.arihant.edurite.models.ReviewListModel;
import com.arihant.edurite.models.SignupModel;
import com.arihant.edurite.models.UserProfileModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityProfileBinding binding;
    private ProgressDialog progressDialog;
    private Session session;
    private String image = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);
        session = new Session(activity);

        binding.edtEmail.setText(session.getEmail());
        binding.edtName.setText(session.getUserName());
        binding.imageProfile.setImageBitmap(decodeImage(activity, session.getUserImage()));
        image = session.getUserImage();

        binding.icBack.setOnClickListener(view -> onBackPressed());
        binding.imageProfile.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest.Builder().build()));
        binding.text.setOnClickListener(view -> pickMedia.launch(new PickVisualMediaRequest.Builder().build()));
        binding.imageSave.setOnClickListener(view -> updateProfile(binding.edtName.getText().toString(), image));
        getProfile();
        getReview();
    }

    ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
            registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                if (uri != null) {
                    Log.e("TAG", "null() called " + uri);
                    binding.imageProfile.setImageURI(uri);

                    Bitmap bitmap = ((BitmapDrawable) binding.imageProfile.getDrawable()).getBitmap();
                    image = encodeImageBitmap(bitmap);
                } else {
                    Toast.makeText(activity, "No media selected", Toast.LENGTH_SHORT).show();
                }
            });

    private void getProfile() {
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getProfile(session.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileModel> call, @NonNull Response<UserProfileModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            UserProfileModel.Data data = response.body().getData();

                            session.setEmail(data.getUsername());
                            session.setUserId(data.getId());
                            session.setUserName(data.getName());
                            session.setUserImage(data.getProfileImg());
                            image = data.getProfileImg();

                            binding.edtEmail.setText(session.getEmail());
                            binding.edtName.setText(session.getUserName());
                            binding.imageProfile.setImageBitmap(decodeImage(activity, session.getUserImage()));
                        } else {
                            Snackbar.make(binding.getRoot(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserProfileModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

    private void updateProfile(String name, String image) {
        if (name.isEmpty()) {
            Snackbar.make(binding.getRoot(), "Name Can't be Empty!", Snackbar.LENGTH_LONG).show();
        } else {
            progressDialog.show();
            ApiService apiService = RetrofitClient.getClient(activity);
            apiService.updateProfile(session.getUserId(), name, image).enqueue(new Callback<SignupModel>() {
                @Override
                public void onResponse(@NonNull Call<SignupModel> call, @NonNull Response<SignupModel> response) {
                    progressDialog.dismiss();
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            if (response.body().getResult().equalsIgnoreCase("true")) {
                                Snackbar.make(binding.getRoot(), "Update Successful!", Snackbar.LENGTH_LONG).show();
                                session.setUserImage(image);
                            } else {
                                Snackbar.make(binding.getRoot(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                                Log.d(TAG, "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
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

    private void getReview() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getReview(session.getUserId()).enqueue(new Callback<ReviewListModel>() {
            @Override
            public void onResponse(@NonNull Call<ReviewListModel> call, @NonNull Response<ReviewListModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.textNoRating.setVisibility(View.GONE);
                            binding.reviewRecycler.setAdapter(new ReviewListAdapter(activity, response.body().getData()));
                            binding.reviewRecycler.setLayoutManager(new LinearLayoutManager(activity));
                        } else {
                            binding.textNoRating.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ReviewListModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }


}