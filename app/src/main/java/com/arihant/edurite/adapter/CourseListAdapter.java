package com.arihant.edurite.adapter;

import static com.arihant.edurite.Retrofit.BaseUrl.Image_Url;
import static com.arihant.edurite.util.Util.decodeImage;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.collection.ArraySet;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.FaqModel;
import com.arihant.edurite.ui.activities.CourseDetailActivity;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private final Context context;
    private final List<CourseListModel.Datum> data;
    private final List<CourseListModel.Datum> filteredList;

    public CourseListAdapter(Context context, List<CourseListModel.Datum> data) {
        this.context = context;
        this.data = data;
        this.filteredList = new ArrayList<>(data);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCourseRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (filteredList.get(position) != null) {
            CourseListModel.Datum current = filteredList.get(position);

            holder.binding.textHeading.setText(current.getTitle());
            holder.binding.textDesc.setText(Html.fromHtml(current.getDescription(), Html.FROM_HTML_MODE_COMPACT));
            holder.binding.textLevel.setText(current.getType());


            Glide.with(context).load(Image_Url + current.getImage()).into(holder.binding.imageThumbnail);

            holder.binding.rating.setRating(Float.parseFloat(current.getAvgRating()));
            holder.binding.textRating.setText(current.getAvgRating());
            holder.binding.textTotalRating.setText("(" + current.getReviewCount() + ")");

            holder.binding.llBody.setOnClickListener(v -> context.startActivity(new Intent(context, CourseDetailActivity.class).putExtra("courseId", current.getCourseId())));
        }
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCourseRecyclerBinding binding;

        public ViewHolder(@NonNull ItemCourseRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }


    public void filter(String text) {
        filteredList.clear();
        if (TextUtils.isEmpty(text)) {
            filteredList.addAll(data);
        } else {
            text = text.toLowerCase().trim();
            for (CourseListModel.Datum item : data) {
                if (item.getTitle().toLowerCase().contains(text)) {
                    filteredList.add(item);
                }
            }
        }
        notifyDataSetChanged();
    }
}
