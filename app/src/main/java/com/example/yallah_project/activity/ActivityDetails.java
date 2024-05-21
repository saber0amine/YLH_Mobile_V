package com.example.yallah_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.yallah_project.R;
import com.example.yallah_project.adapter.ImageSliderAdapter;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.google.android.material.button.MaterialButton;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class ActivityDetails extends AppCompatActivity {

    private TextView name, description, category, price, capacity, startDate, endDate, messageServer;
    private MaterialButton booke, back;
    private ViewPager2 imageSlider;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        imageSlider = findViewById(R.id.activity_image_viewpager);
        name = findViewById(R.id.activity_name);
        description = findViewById(R.id.activity_description);
        category = findViewById(R.id.activity_category);
        price = findViewById(R.id.activity_price);
        capacity = findViewById(R.id.activity_capacity);
        startDate = findViewById(R.id.activity_start_date);
        endDate = findViewById(R.id.activity_end_date);
        messageServer = findViewById(R.id.messageServer);
        booke = findViewById(R.id.button_booking);
        back = findViewById(R.id.button_back);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        ActivityDto activity = getIntent().getParcelableExtra("activity");
        if (activity != null) {
            Log.i("ActivityDetails", activity.toString());

            name.setText(activity.getName());
            description.setText(activity.getDescription());
            category.setText(activity.getActivityCategorie().toString());
            price.setText(String.valueOf(activity.getPrice()) +"DH");
            capacity.setText(String.valueOf(activity.getCapacity()));




            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm", Locale.getDefault());

            // Ensure dateOfStart is properly formatted
            List<String> dateOfStartList = activity.getDateOfStart();
            if (dateOfStartList != null && dateOfStartList.size() == 5) {
                LocalDateTime dateOfStart = LocalDateTime.of(
                        Integer.parseInt(dateOfStartList.get(0)),
                        Integer.parseInt(dateOfStartList.get(1)),
                        Integer.parseInt(dateOfStartList.get(2)),
                        Integer.parseInt(dateOfStartList.get(3)),
                        Integer.parseInt(dateOfStartList.get(4))
                );
                startDate.setText(dateOfStart.format(formatter));
            }

            // Ensure dateOfEnd is properly formatted
            List<String> dateOfEndList = activity.getDateOfEnd();
            if (dateOfEndList != null && dateOfEndList.size() == 5) {
                LocalDateTime dateOfEnd = LocalDateTime.of(
                        Integer.parseInt(dateOfEndList.get(0)),
                        Integer.parseInt(dateOfEndList.get(1)),
                        Integer.parseInt(dateOfEndList.get(2)),
                        Integer.parseInt(dateOfEndList.get(3)),
                        Integer.parseInt(dateOfEndList.get(4))
                );
                endDate.setText(dateOfEnd.format(formatter));
            }
            List<String> images = activity.getActivityImages();
            if (images != null && !images.isEmpty()) {
                imageSlider.setAdapter(new ImageSliderAdapter(this, images));
            } else {
                Log.i("ActivityDetails", "No images available for this activity");
            }
            booke.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userViewModel.bookActivity(activity.getId()).observe(ActivityDetails.this, serverResponse -> {
                        if (serverResponse != null) {
                            Log.i("ActivityDetails", serverResponse.toString());
                            messageServer.setText(serverResponse);
                        } else {
                            messageServer.setText("error");
                        }
                    });
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ActivityDetails.this, nav_layout_all.class);
                    startActivity(intent);
                }
            });
        } else {
            Log.e("ActivityDetails", "Activity is null");
        }
    }
}
