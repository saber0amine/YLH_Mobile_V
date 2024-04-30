package com.example.yallah_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.viewmodel.UserViewModel;
// Your imports here

public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView profilePicture;
    private Button menuButton;
    private UserViewModel userViewModel;
    private User userAuthenticated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            LayoutInflater inflater = LayoutInflater.from(this);
            View customView = inflater.inflate(R.layout.custom_action_bar, null);
            actionBar.setCustomView(customView);
        }
        profilePicture = findViewById(R.id.profilePicture);
        menuButton = findViewById(R.id.menu_button);

        profilePicture.setOnClickListener(this);
        menuButton.setOnClickListener(this);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
        String email = sharedPreferences.getString("email", null);
        String password = sharedPreferences.getString("password", null);

        setUserProfilePicture(email, password);

    }

    protected User getUserAuthenticated() {
    return userAuthenticated;
}

    private void setUserProfilePicture(String email, String password) {
        if (!email.isEmpty() && !password.isEmpty()) {
            LiveData<User> userLiveData = userViewModel.getUserByEmailAndPassword(email, password);
            userLiveData.observe(this, user -> {
                if (user != null) {
                    userAuthenticated = user;
                    byte[] imageBytes = user.getProfilePicture();
                    if (imageBytes != null && imageBytes.length > 0) {
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                        profilePicture.setImageBitmap(bitmap);
                    } else {
                        Log.d("BaseActivity", "User's picture is null or empty");
                    }
                } else {
                    Log.d("BaseActivity", "User is null");
                }
            });
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profilePicture) {
            if (userAuthenticated != null) {
                Intent intent = new Intent(BaseActivity.this, ProfileActivity.class);
                intent.putExtra("user", userAuthenticated);
                startActivity(intent);
            } else {
                Log.d("BaseActivity", "UserAuthenticated is null");
            }
        }
        if (v.getId() == R.id.menu_button) {
            openOptionsMenu();
        }
    }
}