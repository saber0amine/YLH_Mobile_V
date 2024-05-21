package com.example.yallah_project.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.yallah_project.R;
import com.example.yallah_project.activity.ActivityDetails;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ActivityViewHolder> {

    private List<ActivityDto> activityList;
    private Context context;
private UserViewModel userViewModel;
private  LifecycleOwner lifecycleOwner;


    public ActivityAdapter(Context context, List<ActivityDto> activityList , LifecycleOwner lifecycleOwner ) {
    this.context = context;
    this.activityList = activityList != null ? activityList : new ArrayList<>();
    this.lifecycleOwner = lifecycleOwner;

    }

    @NonNull
    @Override
    public ActivityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false);



        return new ActivityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityViewHolder holder, int position) {
        ActivityDto activity = activityList.get(position );
userViewModel = new ViewModelProvider((ViewModelStoreOwner) lifecycleOwner).get(UserViewModel.class);
holder.name.setText(activity.getName());
        holder.description.setText(activity.getDescription());
  //holder.category.setText(activity.getActivityCategorie().toString());
        holder.price.setText(String.valueOf(activity.getPrice()));
        holder.capacity.setText(String.valueOf(activity.getCapacity()));
     if(activity.getDateOfEnd() != null && !activity.getDateOfEnd().isEmpty() && activity.getDateOfStart() != null && !activity.getDateOfStart().isEmpty()) {
  holder.startDate.setText(String.join(", ", activity.getDateOfStart()));
holder.endDate.setText(String.join(", ", activity.getDateOfEnd()));
        }

        if (activity.getActivityImages() != null && !activity.getActivityImages().isEmpty()) {
            String imagePath = activity.getActivityImages().get(0).replace("\\", "/");
            String imageUrl = "https://c067-105-71-133-151.ngrok-free.app/" + imagePath;
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

        holder.button_see_more.setOnClickListener(v -> {
            Intent intent = new Intent(context, ActivityDetails.class);
            intent.putExtra("activity", activity);
            context.startActivity(intent);
        });
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.item_fall_down);
        holder.itemView.startAnimation(animation);

        holder.button_booking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userViewModel.bookActivity(activity.getId()).observe(lifecycleOwner, serverResponse -> {
                    if (serverResponse != null) {
                        Log.i("ActivityDetails", serverResponse.toString());
                        Toast.makeText(context, "You booked this Activity Successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


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
        private Button button_booking ,  button_see_more   ;
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
            button_booking  =itemView.findViewById(R.id.button_booking);
            button_see_more = itemView.findViewById(R.id.button_see_more);
         }
    }
}
