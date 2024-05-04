package com.example.yallah_project.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallah_project.apis.ApiService;
import com.example.yallah_project.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.yallah_project.R;

import java.io.IOException;

// Updated RegisterActivity using Retrofit
    public class RegisterRest extends AppCompatActivity implements View.OnClickListener {

        private ApiService apiService;
    private EditText registerName ;
    private EditText registerEmail ;
    private EditText registerPassword ;
private TextView registerMessage ;
    private Button registerButton ;
    private Button loginbutton ;
    private static final String BASE_URL = "http://192.168.1.142:8080";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.res_test);
            registerMessage = findViewById(R.id.MessageFromSpring)  ;
            registerButton =findViewById(R.id.registerButton2) ;
            registerButton.setOnClickListener(this) ;
            loginbutton =findViewById(R.id.loginButton2) ;
            loginbutton.setOnClickListener(this) ;
             registerName =findViewById(R.id.registerName) ;
            registerEmail = findViewById(R.id.registerEmail);
            registerPassword = findViewById(R.id.registerPassword);

            // Create Retrofit instance
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory
                            .create())
                    .build();

            // Create ApiService instance
            apiService = retrofit.create(ApiService.class);

         }

        @Override
        public void onClick(View v) {
            // Handle button clicks and registration process

            // Example:
            if (v.getId() == R.id.registerButton2) {
                // Create UserRegistrationRequest object with user input
                User request = new User();
                request.setName(registerName.getText().toString());
                request.setEmail(registerEmail.getText().toString());
                request.setPassword(registerPassword.getText().toString());

                // Make network request using Retrofit
                Call<ResponseBody> call = apiService.registerUser(request);
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Handle response from server
                        if (response.isSuccessful()) {
                            String message = null;
                            try {
                                message = response.body().string();
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            registerMessage.setText(message);
                        } else {
                            // Handle error response
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        registerMessage.setText("Failed to register user");

                    }
                });
            }
        }
    }
