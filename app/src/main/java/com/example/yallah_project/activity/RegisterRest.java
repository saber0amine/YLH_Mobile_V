package com.example.yallah_project.activity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.apis.ApiService;
import com.example.yallah_project.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.yallah_project.R;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.io.IOException;

// Updated RegisterActivity using Retrofit
    public class RegisterRest extends AppCompatActivity implements View.OnClickListener {

    private EditText registerName ;
    private EditText registerEmail ;
    private EditText registerPassword ;
private TextView registerMessage ;
    private Button registerButton ;
    private Button loginbutton ;

    private UserViewModel userViewModel ;
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
            userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

             }

        @Override
        public void onClick(View v) {


            if (v.getId() == R.id.registerButton2) {
                User user = new User();
                user.setName(registerName.getText().toString());
                user.setEmail(registerEmail.getText().toString());
                user.setPassword(registerPassword.getText().toString());
                LiveData<Boolean> serverRep = userViewModel.insertUser(user);
                serverRep.observe(this , response -> {
                    if(serverRep.getValue() != null && serverRep.getValue() == true){
                        registerMessage.setText("User Registered Successfully");
                    } else {
                        registerMessage.setText("User Registration Failed");
                    }
                }
                );
            }
        }
    }
