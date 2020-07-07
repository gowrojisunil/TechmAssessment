package com.example.sampleapp.view;
/**
 * Created by sunil gowroji on 07/07/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.sampleapp.R;
import com.example.sampleapp.model.RowsModel;

import java.util.List;

public class FactsAdapter extends RecyclerView.Adapter<FactsAdapter.FactsViewHolder> {

    private List<RowsModel> rowsModelList;

    private Context context;

    public FactsAdapter(Context context, List<RowsModel> rowsModelList) {
        this.rowsModelList = rowsModelList;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(@NonNull FactsViewHolder holder, int position) {
        final RowsModel rowsModel = rowsModelList.get(position);
        if (rowsModel != null) {
            Glide.with(context)
                    .load(rowsModel.getImageHref())
                    .placeholder(R.mipmap.ic_launcher)
                    .centerCrop()
                    .into(holder.imageView);

            holder.name.setText(rowsModel.getTitle());
            holder.description.setText(rowsModel.getDescription());
        }
    }

    @NonNull
    @Override
    public FactsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.facts_adapter, parent, false);
        return new FactsViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return rowsModelList.size();
    }

    class FactsViewHolder extends RecyclerView.ViewHolder {

        TextView name, description;
        ImageView imageView;

        FactsViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            description = view.findViewById(R.id.description);
            imageView = view.findViewById(R.id.image);
        }
    }

}
