package com.example.yallah_project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel;

public class LoginActivity  extends AppCompatActivity implements View.OnClickListener {
    private Button registerButton  , loginButton ;
    private EditText loginEmail , loginPassword ;
    private TextView loginErrors;

    private SharedPrefer sharedPrefer  ;

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
            String email = loginEmail.getText().toString();
            String password = loginPassword.getText().toString();
            User user = new User() ;
            user.setEmail(email);
            user.setPassword(password);
             LiveData<String> userLiveData = userViewModel.login(user);
            userLiveData.observe(this, jwt -> {
                if (jwt.startsWith("ok")) {
                    Log.i("jwt"  ,"from login " + jwt)  ;
                    storeUserInformation(user, jwt.substring(2));
                    Intent intent = new Intent(LoginActivity.this, nav_layout_all.class);
                    startActivity(intent);
                } else {
                    loginErrors.setText(jwt);
                }
            });
        }



    private void storeUserInformation(User user , String jwt) {
      String[] paramName ={"name" , "email"  , "jwt" } ;
      if(user != null) {
      LiveData<User> userName = userViewModel.getUserByEmailAndPassword(user.getEmail() , user.getPassword()   ) ;
      userName.observe(this , user1 -> {
          String[] paramValue  = { user1.getName() , user1.getEmail() , jwt} ;
          SharedPrefer.storeUserData(this , paramName , paramValue) ;
      });
          }
    }


}
