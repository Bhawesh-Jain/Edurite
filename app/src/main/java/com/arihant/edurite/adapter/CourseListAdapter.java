package com.arihant.edurite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCourseRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.rating.setRating(position + 1);
        holder.binding.textRating.setText(String.valueOf(position + 1));
        holder.binding.textTotalRating.setText("(" + (position + 1) * 65 + ")");


    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCourseRecyclerBinding binding;

        public ViewHolder(@NonNull ItemCourseRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
