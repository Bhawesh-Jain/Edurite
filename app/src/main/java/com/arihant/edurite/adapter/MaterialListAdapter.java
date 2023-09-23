package com.arihant.edurite.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.databinding.ItemMaterialRecyclerBinding;

public class MaterialListAdapter extends RecyclerView.Adapter<MaterialListAdapter.ViewHolder> {

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemMaterialRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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
        public ItemMaterialRecyclerBinding binding;

        public ViewHolder(@NonNull ItemMaterialRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
