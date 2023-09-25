package com.arihant.edurite.adapter;

import static com.arihant.edurite.util.Util.decodeImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.models.CourseListModel;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.ViewHolder> {
    private final Context context;
    private final List<CourseListModel.Datum> data;

    public CourseListAdapter(Context context, List<CourseListModel.Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemCourseRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            CourseListModel.Datum current = data.get(position);

            holder.binding.textHeading.setText(current.getTitle());
            holder.binding.textDesc.setText(current.getDescription());

            holder.binding.imageThumbnail.setImageBitmap(decodeImage(current.getImage()));
        }
        holder.binding.rating.setRating(position + 1);
        holder.binding.textRating.setText(String.valueOf(position + 1));
        holder.binding.textTotalRating.setText("(" + (position + 1) * 65 + ")");
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemCourseRecyclerBinding binding;

        public ViewHolder(@NonNull ItemCourseRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
