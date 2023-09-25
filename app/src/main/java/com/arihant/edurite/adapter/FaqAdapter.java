package com.arihant.edurite.adapter;

import static com.arihant.edurite.util.Util.decodeImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.arihant.edurite.R;
import com.arihant.edurite.databinding.ItemCourseRecyclerBinding;
import com.arihant.edurite.databinding.ItemFaqRecyclerBinding;
import com.arihant.edurite.models.CourseListModel;
import com.arihant.edurite.models.FaqModel;

import java.util.List;

public class FaqAdapter extends RecyclerView.Adapter<FaqAdapter.ViewHolder> {
    private final Context context;
    private final List<FaqModel.Faq> data;

    public FaqAdapter(Context context, List<FaqModel.Faq> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemFaqRecyclerBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (data.get(position) != null) {
            FaqModel.Faq current = data.get(position);

            holder.binding.textHeading.setText(position + 1 + ". " + current.getQuestion());
            holder.binding.textDesc.setText(current.getAnswer());

            holder.binding.getRoot().setOnClickListener(view -> {
                if (holder.binding.textDesc.getMaxLines() != 2) {
                    holder.binding.textDesc.setMaxLines(2);
                    holder.binding.textViewMore.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.textDesc.setMaxLines(1000);
                    holder.binding.textViewMore.setVisibility(View.GONE);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ItemFaqRecyclerBinding binding;

        public ViewHolder(@NonNull ItemFaqRecyclerBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
