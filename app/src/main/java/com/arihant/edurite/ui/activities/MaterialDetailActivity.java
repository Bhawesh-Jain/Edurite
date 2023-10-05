package com.arihant.edurite.ui.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;
import static com.arihant.edurite.Retrofit.BaseUrl.Image_Url;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.CourseReviewListAdapter;
import com.arihant.edurite.databinding.ActivityMaterialDetailBinding;
import com.arihant.edurite.models.CourseDetailModel;
import com.arihant.edurite.models.MaterialDetailModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MaterialDetailActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityMaterialDetailBinding binding;
    private ProgressDialog progressDialog;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMaterialDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        progressDialog = new ProgressDialog(activity);
        session = new Session(activity);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        String matId = getIntent().getStringExtra("matId");

        binding.imageBack.setOnClickListener(v -> onBackPressed());
        getMaterialDetail(matId);
    }

    private void getMaterialDetail(String matId) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getMaterialDetails(matId).enqueue(new Callback<MaterialDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<MaterialDetailModel> call, @NonNull Response<MaterialDetailModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            MaterialDetailModel.Data data = response.body().getData();

                            binding.textHeading.setText(data.getMaterial().getMatName());
                            binding.textDesc.setText(Html.fromHtml(data.getMaterial().getMatDescription(), Html.FROM_HTML_MODE_COMPACT));
                            binding.textRating.setText(data.getMaterial().getAvgRating());
                            binding.rating.setRating(Float.parseFloat(data.getMaterial().getAvgRating()));
                            binding.textTotalRating.setText(String.valueOf(data.getReviews().size()));

                            progressDialog.show();
                            WebView webView = binding.pdfView;
                            WebSettings webSettings = webView.getSettings();
                            webSettings.setJavaScriptEnabled(true);
                            webView.loadUrl(Image_Url + data.getMaterial().getPdfFile());
                            webView.setWebViewClient(new WebViewClient() {
                                @Override
                                public void onPageCommitVisible(WebView view, String url) {
                                    super.onPageCommitVisible(view, url);
                                    progressDialog.dismiss();
                                }
                            });

                            binding.recyclerViewRating.setAdapter(new CourseReviewListAdapter(activity, response.body().getData().getReviews()));
                            binding.recyclerViewRating.setLayoutManager(new LinearLayoutManager(activity));

                            binding.progressbar.setVisibility(View.GONE);
                            if (data.getReviews().size() > 0)
                                binding.textEmpty.setVisibility(View.GONE);
                            else binding.textEmpty.setVisibility(View.VISIBLE);
                        } else {
                            Snackbar.make(binding.getRoot(), response.body().getMsg(), Snackbar.LENGTH_LONG).show();
                            Log.d(TAG, "onResponse() called with: call = [" + call + "], response msg = [" + response.body().getMsg() + "]");
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MaterialDetailModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }
}