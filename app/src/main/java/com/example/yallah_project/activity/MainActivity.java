package com.example.yallah_project.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;

import com.example.yallah_project.R;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private ImageButton getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStartedButton  =findViewById(R.id.GetStartedButton);
        getStartedButton.setOnClickListener(this);
        checkLoginUsers() ;

    }

    private void checkLoginUsers() {
         SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("user_data", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", null);
        String email = sharedPreferences.getString("email", null);

        if (username != null && email != null) {

        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.GetStartedButton) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}