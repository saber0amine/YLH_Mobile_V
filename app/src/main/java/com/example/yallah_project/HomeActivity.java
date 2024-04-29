package com.example.yallah_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.TextView;

 import com.example.yallah_project.databinding.HomeBinding;

public class HomeActivity extends AppCompatActivity  {

    HomeBinding binding ;
    private TextView username ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = HomeBinding.inflate(getLayoutInflater()); // Change to HomeActivityBinding
        setContentView(binding.getRoot());
        /*username  = findViewById(R.id.username) ;
        Intent intent = getIntent() ;
        String userName = intent.getStringExtra("userName") ;
        username.setText("Welcome " + userName) ;
*/
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


}