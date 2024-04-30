package com.example.yallah_project.activity;

import android.content.Intent;
import android.os.Bundle;
 import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.viewmodel.UserViewModel;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton  , loginButton ;
    private EditText loginEmail , loginPassword ;
    private TextView loginErrors;

    private UserViewModel userViewModel ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login) ;
        registerButton =findViewById(R.id.RegisterButton) ;
        loginEmail = findViewById(R.id.LoginEmail) ;
        loginPassword = findViewById(R.id.LoginPassword) ;
        loginErrors = findViewById(R.id.LoginErrors) ;
        findViewById(R.id.LoginButton).setOnClickListener(this); ;
        registerButton.setOnClickListener( this ) ;

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RegisterButton) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }


        if(v.getId() == R.id.LoginButton  ) {
CheckUserLogin() ;
        }
    }

    private void CheckUserLogin() {
        if (loginEmail.getText().toString().isEmpty() && loginPassword.getText().toString().isEmpty()) {
            loginErrors.setText("Please fill all the fields");
        } else {
            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();
            LiveData<User> userLiveData = userViewModel.getUserByEmailAndPassword(email, password);
            userLiveData.observe(this, user -> {
                if (user != null) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    intent.putExtra("user", user);
                    startActivity(intent);
                } else {
                    loginErrors.setText("User not found");
                }
            });
        }
    }















}
