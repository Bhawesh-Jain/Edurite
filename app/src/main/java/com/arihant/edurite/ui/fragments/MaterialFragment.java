package com.arihant.edurite.ui.fragments;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.CourseListAdapter;
import com.arihant.edurite.adapter.MaterialListAdapter;
import com.arihant.edurite.databinding.FragmentMaterialBinding;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.MaterialListModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialFragment extends Fragment {
    private Activity activity;
    private FragmentMaterialBinding binding;
    private ProgressDialog progressDialog;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMaterialBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        activity = requireActivity();
        progressDialog = new ProgressDialog(activity);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        getMaterialList();
    }

    private void getMaterialList() {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getMaterialList().enqueue(new Callback<MaterialListModel>() {
            @Override
            public void onResponse(@NonNull Call<MaterialListModel> call, @NonNull Response<MaterialListModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            binding.recyclerView.setAdapter(new MaterialListAdapter(activity, response.body().getData()));
                        } else {
                            String message = response.body().getMsg();
                            Snackbar.make(binding.getRoot(), message, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MaterialListModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}