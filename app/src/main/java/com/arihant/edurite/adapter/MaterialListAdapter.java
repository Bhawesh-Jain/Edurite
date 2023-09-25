package com.arihant.edurite.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.databinding.ItemMaterialRecyclerBinding;
import com.arihant.edurite.models.MaterialListModel;

import java.util.List;

public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.ViewHolder> {
    private final Context context;
    private final List<MaterialListModel.Datum> data;

    public MaterialListAdapter(Context context, List<MaterialListModel.Datum> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMaterialRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            MaterialListModel.Datum current = data.get(position);

            holder.binding.textHeading.setText(current.getMatName());
            holder.binding.textDesc.setText(current.getMatDescription());

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
        holder.binding.rating.setRating(position + 1);
        holder.binding.textRating.setText(String.valueOf(position + 1));
        holder.binding.textTotalRating.setText("(" + (position + 1) * 65 + ")");


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemMaterialRecyclerBinding binding;

        public ViewHolder(@NonNull ItemMaterialRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
