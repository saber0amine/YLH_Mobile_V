package com.example.yallah_project.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.yallah_project.R;
import com.example.yallah_project.dtos.ActivityDto;

import java.util.ArrayList;
import java.util.List;

public class BookedActivitiesAdapter  extends RecyclerView.Adapter<BookedActivitiesAdapter.ActivityViewHolder>{

    private List<ActivityDto> activityList;
    private Context context;



    public BookedActivitiesAdapter(Context context, List<ActivityDto> activityList) {
        this.context = context;
        this.activityList = activityList != null ? activityList : new ArrayList<>();
    }

    @NonNull
    @Override
    public BookedActivitiesAdapter.ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booked_activity, parent, false);



        return new BookedActivitiesAdapter.ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookedActivitiesAdapter.ActivityViewHolder holder, int position) {
        ActivityDto activity = activityList.get(position );

        holder.name.setText(activity.getName());
        holder.description.setText(activity.getDescription());
        holder.category.setText(activity.getActivityCategorie().toString());
        holder.price.setText(String.valueOf(activity.getPrice()));
        holder.capacity.setText(String.valueOf(activity.getCapacity()));
     /*   if(activity.getDateOfEnd() != null && !activity.getDateOfEnd().isEmpty() && activity.getDateOfStart() != null && !activity.getDateOfStart().isEmpty()) {
  holder.startDate.setText(String.join(", ", activity.getDateOfStart()));
holder.endDate.setText(String.join(", ", activity.getDateOfEnd()));
        }*/

        if (activity.getActivityImages() != null && !activity.getActivityImages().isEmpty()) {
            String imagePath = activity.getActivityImages().get(0).replace("\\", "/");
            String imageUrl = "http://10.0.2.2:8080/" + imagePath;
            Log.i("gettingActivities", "corected url " + imageUrl);

            Glide.with(context)
                    .load(imageUrl)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            Log.e("gettingActivities", "URL: " + imageUrl, e);
                            return false; // Show the error placeholder
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(holder.images);
        } else {
            Log.i("gettingActivities", "No images available for this activity");
        }



    }

    @Override
    public int getItemCount() {
        return activityList.size();
    }


    public void setActivityList(List<ActivityDto> activityList) {
        this.activityList = activityList != null ? activityList : new ArrayList<>();
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder {

        TextView name, description, category, price, capacity, startDate, endDate;
        ImageView images;

        public ActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            images = itemView.findViewById(R.id.activity_image);
            name = itemView.findViewById(R.id.activity_name);
            description = itemView.findViewById(R.id.activity_description);
            category = itemView.findViewById(R.id.activity_category);
            price = itemView.findViewById(R.id.activity_price);
            capacity = itemView.findViewById(R.id.activity_capacity);
            startDate = itemView.findViewById(R.id.activity_start_date);
            endDate = itemView.findViewById(R.id.activity_end_date);

        }
    }
}
