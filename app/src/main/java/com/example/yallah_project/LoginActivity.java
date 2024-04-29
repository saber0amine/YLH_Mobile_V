package com.example.yallah_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton  , loginButton ;
    private EditText loginEmail , loginPassword ;
    private TextView loginErrors;

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

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.RegisterButton) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        }
        if(v.getId() == R.id.LoginButton  ) {
        if (loginEmail.getText().toString().isEmpty()  && loginPassword.getText().toString().isEmpty())  {
            loginErrors.setText("Please fill all the fields") ;
        }
        else {
            Log.i("username","Im inside") ;

            String email  = loginEmail.getText().toString() ;
        String password = loginPassword.getText().toString() ;
        UserDbHelper userDbHelper = new UserDbHelper(this) ;
        String userName = userDbHelper.checkUserExists(email, password) ;
            Log.i("username","before the intent") ;

            if (  !userName.isEmpty() ) {
            Log.i("username",userName) ;
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            intent.putExtra("userName" , userName);
            startActivity(intent);
        } else {
            loginErrors.setText("User not found") ;
        }
        }
        }
    }
}
