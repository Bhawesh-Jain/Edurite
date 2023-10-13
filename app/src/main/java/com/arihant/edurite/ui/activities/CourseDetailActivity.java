package com.arihant.edurite.ui.activities;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import static com.arihant.edurite.Retrofit.BaseUrl.Image_Url;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.media3.common.MediaItem;
import androidx.media3.common.util.UnstableApi;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.arihant.edurite.Retrofit.ApiService;
import com.arihant.edurite.Retrofit.RetrofitClient;
import com.arihant.edurite.adapter.CourseReviewListAdapter;
import com.arihant.edurite.adapter.ReviewListAdapter;
import com.arihant.edurite.databinding.ActivityCourseDetailBinding;
import com.arihant.edurite.models.CourseDetailModel;
import com.arihant.edurite.models.LoginModel;
import com.arihant.edurite.util.ProgressDialog;
import com.arihant.edurite.util.Session;
import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CourseDetailActivity extends AppCompatActivity {
    private Activity activity;
    private ActivityCourseDetailBinding binding;
    private Session session;
    private ProgressDialog progressDialog;
    private ExoPlayer player;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (player != null)
            player.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (player != null)
            player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null)
            player.play();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCourseDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = this;
        session = new Session(activity);
        progressDialog = new ProgressDialog(activity);

        String courseId = getIntent().getStringExtra("courseId");
        getCourseDetails(courseId);
        player = new ExoPlayer.Builder(activity).build();

        binding.imageBack.setOnClickListener(v -> onBackPressed());

    }

    private void setPlayer() {
        try {
            binding.playerView.setShowNextButton(false);
            binding.playerView.setShowPreviousButton(false);
        }catch (Exception e) {
            Log.e(TAG, "onResponse: ", e);
        }
    }

    private void getCourseDetails(String courseId) {
        progressDialog.show();
        ApiService apiService = RetrofitClient.getClient(activity);
        apiService.getCourseDetails(courseId).enqueue(new Callback<CourseDetailModel>() {
            @Override
            public void onResponse(@NonNull Call<CourseDetailModel> call, @NonNull Response<CourseDetailModel> response) {
                progressDialog.dismiss();
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getResult().equalsIgnoreCase("true")) {
                            CourseDetailModel.Data data = response.body().getData();

                            binding.textHeading.setText(data.getCourse().getTitle());
                            binding.textDesc.setText(Html.fromHtml(data.getCourse().getDescription(), Html.FROM_HTML_MODE_COMPACT));
                            binding.textLevel.setText(data.getCourse().getLevel());
                            binding.textRating.setText(data.getCourse().getAvgRating());
                            binding.rating.setRating(Float.parseFloat(data.getCourse().getAvgRating()));
                            binding.textTotalRating.setText(String.valueOf(data.getReviews().size()));

                            binding.recyclerViewRating.setAdapter(new CourseReviewListAdapter(activity, response.body().getData().getReviews()));
                            binding.recyclerViewRating.setLayoutManager(new LinearLayoutManager(activity));

                            boolean flag = true;
                            for (CourseDetailModel.Data.Review review : response.body().getData().getReviews()) {
                                if (review.getUserId().equalsIgnoreCase(session.getUserId())) flag = false;
                            }

                            if (flag) binding.textAddRating.setVisibility(View.VISIBLE);

                            if (!data.getCourse().getImage().isEmpty())
                                Glide.with(getApplicationContext()).load(Image_Url + data.getCourse().getImage()).into(binding.imageThumbnail);

                            if (data.getCourse().getVideo().isEmpty()) {
                                binding.playerView.setVisibility(View.GONE);
                                binding.imageThumbnail.setVisibility(View.VISIBLE);
                            } else {
                                binding.playerView.setVisibility(View.VISIBLE);
                                binding.imageThumbnail.setVisibility(View.GONE);

                                binding.playerView.setPlayer(player);

                                setPlayer();

                                MediaItem mediaItem = MediaItem.fromUri(Image_Url + data.getCourse().getVideo());
                                player.setMediaItem(mediaItem);
                                player.prepare();
                                player.play();
                            }

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
            public void onFailure(@NonNull Call<CourseDetailModel> call, @NonNull Throwable t) {
                Log.e(TAG, "onFailure() called with: call = [" + call + "], t = [" + t.getLocalizedMessage() + "]");
                Snackbar.make(binding.getRoot(), "Server Error!", Snackbar.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        });
    }

}