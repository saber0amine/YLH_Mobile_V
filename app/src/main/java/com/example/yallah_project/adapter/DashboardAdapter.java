package com.example.yallah_project.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallah_project.R;

import java.util.List;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private List<String> activityNames;
    private OnRemoveClickListener removeClickListener;

    public interface OnRemoveClickListener {
        void onRemoveClick(int position);
    }

    public DashboardAdapter(List<String> activityNames, OnRemoveClickListener removeClickListener) {
        this.activityNames = activityNames;
        this.removeClickListener = removeClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String activityName = activityNames.get(position);
        holder.tvActivityName.setText(activityName);
        holder.btnRemoveActivity.setOnClickListener(v -> removeClickListener.onRemoveClick(position));
    }

    @Override
    public int getItemCount() {
        return activityNames.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvActivityName;
        Button btnRemoveActivity;

        public ViewHolder(View itemView) {
            super(itemView);
            tvActivityName = itemView.findViewById(R.id.tv_activity_name);
            btnRemoveActivity = itemView.findViewById(R.id.btn_remove_activity);
        }
    }
}
