package com.example.yallah_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;

public class ProfileActivity extends BaseActivity {


     private TextView nameProfil , emailProfil , ageProfil  , testSwitch  ;
     Button EditProfile , BackButton;
private de.hdodenhof.circleimageview.CircleImageView profileImageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profil) ;
        nameProfil =findViewById(R.id.nameProfil) ;
        emailProfil = findViewById(R.id.emailProfil) ;
        ageProfil = findViewById(R.id.ageProfil) ;
        testSwitch = findViewById(R.id.testSwitch) ;
         BackButton = findViewById(R.id.BackButton) ;

         BackButton.setOnClickListener(view -> {
             Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
             startActivity(intent);
         });
        profileImageView = findViewById(R.id.profileImageView) ;
        User user = getIntent().getParcelableExtra("user") ;
        if (user != null) {
        nameProfil.setText(user.getName()) ;
        emailProfil.setText(user.getEmail()) ;
        ageProfil.setText(user.getAge().toString()) ;
        byte[] imageBytes = user.getProfilePicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImageView.setImageBitmap(bitmap);
        }
            if(user!=null){
        if ( user.getRole() == UserRole.USER) {
            testSwitch.setText("User") ;
        } else {
            testSwitch.setText("Organisateur") ;
        }
            }
    }











}
