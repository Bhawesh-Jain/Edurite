package com.arihant.edurite.ui.fragments;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.arihant.edurite.util.Util.decodeImage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.databinding.FragmentSettingsBinding;
import com.arihant.edurite.models.UserProfileModel;
import com.arihant.edurite.ui.activities.AboutUsActivity;
import com.arihant.edurite.ui.activities.ChangePasswordActivity;
import com.arihant.edurite.ui.activities.FaqActivity;
import com.arihant.edurite.ui.activities.PrivacyPolicyActivity;
import com.arihant.edurite.ui.activities.ProfileActivity;
import com.arihant.edurite.ui.activities.TermsActivity;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SettingsFragment extends Fragment {
    private Activity activity;
    private FragmentSettingsBinding binding;
    private Session session;

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSettingsBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);

        binding.imageProfile.setImageBitmap(decodeImage(activity, session.getUserImage()));

        binding.llAboutUs.setOnClickListener(view -> startActivity(new Intent(activity, AboutUsActivity.class)));
        binding.llPrivacyPolicy.setOnClickListener(view -> startActivity(new Intent(activity, PrivacyPolicyActivity.class)));
        binding.llTerms.setOnClickListener(view -> startActivity(new Intent(activity, TermsActivity.class)));
        binding.llFaq.setOnClickListener(view -> startActivity(new Intent(activity, FaqActivity.class)));
        binding.llProfileCard.setOnClickListener(view -> startActivity(new Intent(activity, ProfileActivity.class)));
        binding.llUserProfile.setOnClickListener(view -> startActivity(new Intent(activity, ProfileActivity.class)));
        binding.llChangePassword.setOnClickListener(view -> startActivity(new Intent(activity, ChangePasswordActivity.class)));
        binding.textLogout.setOnClickListener(view -> session.logout());

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getProfile();
    }

    private void getProfile() {
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getProfile(session.getUserId()).enqueue(new Callback<UserProfileModel>() {
            @Override
            public void onResponse(@NonNull Call<UserProfileModel> call, @NonNull Response<UserProfileModel> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            UserProfileModel.Data data = response.body().getData();

                            session.setEmail(data.getUsername());
                            session.setUserId(data.getId());
                            session.setUserName(data.getName());

                            binding.textUserName.setText(session.getUserName());
                            binding.imageProfile.setImageBitmap(decodeImage(activity, data.getProfileImg()));
                        } else {
                            Log.d(TAG, "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserProfileModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
            }
        });
    }

}