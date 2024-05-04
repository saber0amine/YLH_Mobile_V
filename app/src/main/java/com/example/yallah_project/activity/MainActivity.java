package com.example.yallah_project.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yallah_project.R;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener {

    private static final String BASE_URL = "http://192.168.1.142:8080";

    private Button getStartedButton;
    private  Button     SpringResponse  ;

private TextView HereSpringResponse ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getStartedButton  =findViewById(R.id.GetStartedButton);
        getStartedButton.setOnClickListener(this);
        HereSpringResponse = findViewById(R.id.HereSpringResponse) ;
        SpringResponse = findViewById(R.id.SpringResponse) ;
        SpringResponse.setOnClickListener(this);
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

        if(v.getId() == R.id.SpringResponse) {
             fetchSpringResponse() ;
        }
    }

    private void fetchSpringResponse() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // Remove this line if response is not JSON
                .build();

        SpringService service = retrofit.create(SpringService.class);

        Call<ResponseBody> call = service.getSpringResponse();

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        // Convert the response body to string and set it to TextView
                        String responseBody = response.body().string();
                        HereSpringResponse.setText(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                        HereSpringResponse.setText("Error: " + e.getMessage());
                    }
                } else {
                    HereSpringResponse.setText("Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                HereSpringResponse.setText("Failed to fetch response");
            }
        });
    }


}