package com.arihant.edurite.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.databinding.ItemNotificationRecyclerBinding;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNotificationRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.getRoot().setOnClickListener(view -> {
            if (holder.binding.textDesc.getMaxLines() != 2) {
                holder.binding.textDesc.setMaxLines(2);
                holder.binding.textViewMore.setText(R.string.view_more);
            } else {
                holder.binding.textDesc.setMaxLines(1000);
                holder.binding.textViewMore.setText(R.string.view_less);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemNotificationRecyclerBinding binding;

        public ViewHolder(@NonNull ItemNotificationRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
