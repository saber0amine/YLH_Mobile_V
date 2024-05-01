package com.example.yallah_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallah_project.R;
import com.example.yallah_project.databinding.HomeBinding;
import com.example.yallah_project.model.User;

import java.net.UnknownServiceException;

public class HomeActivity extends BaseActivity  implements  View.OnClickListener{
    HomeBinding binding ;
    private TextView username ;
    private ImageView profilePicture ;
    private Button menuButton ;
private Button switch_to_organisateur_button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(getLayoutInflater()); // Change to HomeActivityBinding
        setContentView(binding.getRoot());
        username  = findViewById(R.id.username) ;
         switch_to_organisateur_button = findViewById(R.id.switch_to_organisateur_button) ;
         profilePicture = findViewById(R.id.profilePicture) ;
                 profilePicture.setOnClickListener(this);
         menuButton = findViewById(R.id.menu_button);
         menuButton.setOnClickListener(this);
switch_to_organisateur_button.setOnClickListener(this);







        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
    if (item.getItemId() == R.id.nav_home) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyActivitiesFragment()).commit();
    } else if (item.getItemId() == R.id.nav_booked) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyActivitiesFragment()).commit();
    } else if (item.getItemId() == R.id.nav_room) {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyRoomsFragment()).commit();
    }
    return true;
});


    }


    @Override
    public void onClick(View v) {
        Intent intent ;
        User     userAuthenticated = getUserAuthenticated();

        if (v.getId() == R.id.switch_to_organisateur_button) {
              intent = new Intent(HomeActivity.this, OrganizerRegistrationActivity.class) ;
            startActivity(intent) ;
        }
        if (v.getId() == R.id.profilePicture) {

            intent = new Intent(HomeActivity.this, ProfileActivity.class);
                intent.putExtra("user", userAuthenticated);
                startActivity(intent);

        }
        if (v.getId() == R.id.menu_button) {
            openOptionsMenu();
        }

        if(v.getId() == R.id.switch_to_organisateur_button) {

            intent = new Intent(HomeActivity.this, OrganizerRegistrationActivity.class);
            intent.putExtra("user",  userAuthenticated);
            startActivity(intent);

        }


    }
}