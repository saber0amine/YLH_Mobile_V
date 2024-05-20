package com.example.yallah_project.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.yallah_project.R;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.viewmodel.UserViewModel;

public class ActivityDetails extends AppCompatActivity {

    private TextView name, description, category, price, capacity, startDate, endDate , messageServer;
  private Button booke  , back;
private ImageView  image ;

  UserViewModel userViewModel ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        image =findViewById(R.id.activity_image) ;
        name = findViewById(R.id.activity_name);
        description = findViewById(R.id.activity_description);
        category = findViewById(R.id.activity_category);
        price = findViewById(R.id.activity_price);
        capacity = findViewById(R.id.activity_capacity);
        startDate = findViewById(R.id.activity_start_date);
        endDate = findViewById(R.id.activity_end_date);
        messageServer = findViewById(R.id.messageServer) ;
        booke = findViewById(R.id.button_booking);
        back  = findViewById(R.id.button_back) ;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

        // Retrieve the ActivityDto object
        ActivityDto activity = (ActivityDto) getIntent().getParcelableExtra("activity");
        Log.i("ActivityDetails", activity.toString());
        booke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(activity != null) {
                    userViewModel.bookActivity(activity.getId()).observe(ActivityDetails.this, serverResponse -> {
                        if(serverResponse != null) {
                            Log.i("ActivityDetails", serverResponse.toString()) ;

                            messageServer.setText(serverResponse);
                        }
                        else   messageServer.setText("error");



                    }) ;
                    }
                }
            });
        if (activity != null) {
            name.setText(activity.getName());
            description.setText(activity.getDescription());
            category.setText(activity.getActivityCategorie().toString());
            price.setText(String.valueOf(activity.getPrice()));
            capacity.setText(String.valueOf(activity.getCapacity()));
            /*startDate.setText(String.join(", ", activity.getDateOfStart()));
            endDate.setText(String.join(", ", activity.getDateOfEnd()));*/


            if (activity.getActivityImages() != null && !activity.getActivityImages().isEmpty()) {
                String imagePath = activity.getActivityImages().get(0).replace("\\", "/");
                String imageUrl = "https://de22-105-71-4-58.ngrok-free.app/" + imagePath;
                Log.i("gettingActivities", "corected url " + imageUrl);

                Glide.with(this)
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
                        .into(image);
            } else {
                Log.i("gettingActivities", "No images available for this activity");
            }
        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityDetails.this, nav_layout_all.class ) ;
                startActivity(intent);
            }
        });

    }
}
