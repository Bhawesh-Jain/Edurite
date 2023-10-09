package com.arihant.edurite.ui.fragments;

import static com.arihant.edurite.ui.activities.LoginActivity.TAG;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.FaqAdapter;
import com.arihant.edurite.databinding.FragmentInterviewQuestionBinding;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.util.ProgressDialog;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class InterviewQuestionFragment extends Fragment {
    private Activity activity;
    private FragmentInterviewQuestionBinding binding;
    private ProgressDialog progressDialog;
    private FaqAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInterviewQuestionBinding.inflate(inflater, container, false);
        activity = requireActivity();
        progressDialog = new ProgressDialog(activity);

        getFaq();

        binding.edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (adapter!=null) adapter.filter(s.toString());
            }
        });

        return binding.getRoot();
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
                            adapter = new FaqAdapter(activity, response.body().getFaq());
                            binding.recyclerView.setAdapter(adapter);
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