package com.example.yallah_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.yallah_project.R;
import com.example.yallah_project.apis.SpringService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {


    private Button getStartedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkLoginUsers() ;
        getStartedButton  =findViewById(R.id.GetStartedButton);
        getStartedButton.setOnClickListener(this);



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