package com.arihant.edurite.adapter;

import android.app.AppOpsManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.R;
import com.arihant.edurite.Retrofit.BaseUrl;
import com.arihant.edurite.databinding.ItemNotificationRecyclerBinding;
import com.arihant.edurite.models.NotificationModel;
import com.bumptech.glide.Glide;

import java.util.List;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    private final Context context;
    private final List<NotificationModel.Datum> data;

    public NotificationListAdapter(Context context, List<NotificationModel.Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemNotificationRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            NotificationModel.Datum current = data.get(position);

            holder.binding.textDesc.setText(current.getDescription());
            holder.binding.textHeading.setText(current.getTitle());
            holder.binding.textDate.setText(current.getTime());

            if (current.getImage() != null && !current.getImage().isEmpty()) {
                Glide.with(context).load(BaseUrl.Image_Url + current.getImage()).error(R.drawable.ic_course).into(holder.binding.imageThumbnail);
            } else holder.binding.imageThumbnail.setVisibility(View.GONE);
        }
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
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemNotificationRecyclerBinding binding;

        public ViewHolder(@NonNull ItemNotificationRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
