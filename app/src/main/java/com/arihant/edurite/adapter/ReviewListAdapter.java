package com.arihant.edurite.adapter;

import static com.arihant.edurite.util.Util.calculateDateDifference;
import static com.arihant.edurite.util.Util.decodeImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.databinding.ItemReviewRecyclerBinding;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.ReviewListModel;

import java.util.List;

public class ReviewListAdapter extends RecyclerView.Adapter<ReviewListAdapter.ViewHolder> {
    private final Context context;
    private final List<ReviewListModel.Datum> data;

    public ReviewListAdapter(Context context, List<ReviewListModel.Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemReviewRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            ReviewListModel.Datum current = data.get(position);

            holder.binding.textHeading.setText(current.getUsername());
            holder.binding.textDesc.setText(current.getReview());
            holder.binding.textRating.setText(current.getRating());
            holder.binding.rating.setRating(Float.parseFloat(current.getRating()));
            holder.binding.textDate.setText(calculateDateDifference(current.getDate()));

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemReviewRecyclerBinding binding;

        public ViewHolder(@NonNull ItemReviewRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
