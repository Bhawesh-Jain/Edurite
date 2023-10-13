package com.arihant.edurite.ui.fragments;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.FaqAdapter;
import com.arihant.edurite.adapter.MaterialListAdapter;
import com.arihant.edurite.adapter.NotificationListAdapter;
import com.arihant.edurite.databinding.FragmentNotificationBinding;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.models.NotificationModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    private Activity activity;
    private FragmentNotificationBinding binding;
    private Session session;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNotificationBinding.inflate(inflater, container, false);
        activity = requireActivity();
        session = new Session(activity);
        progressDialog = new ProgressDialog(activity);


        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        getNotificationList();
    }

    private void getNotificationList() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getNotificationList(session.getUserId()).enqueue(new Callback<NotificationModel>() {
            @Override
            public void onResponse(@NonNull Call<NotificationModel> call, @NonNull Response<NotificationModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            if (response.body().getData().size() > 0) {
                                binding.textEmpty.setVisibility(View.GONE);
                                binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                                binding.recyclerView.setAdapter(new NotificationListAdapter(activity, response.body().getData()));
                            } else binding.textEmpty.setVisibility(View.VISIBLE);
                        } else binding.textEmpty.setVisibility(View.VISIBLE);
                    } else {
                        binding.textEmpty.setVisibility(View.VISIBLE);
                        Snackbar.make(binding.getRoot(), "Server Error! " + response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    binding.textEmpty.setVisibility(View.VISIBLE);
                    Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<NotificationModel> call, @NonNull Throwable t) {
                binding.textEmpty.setVisibility(View.VISIBLE);
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}