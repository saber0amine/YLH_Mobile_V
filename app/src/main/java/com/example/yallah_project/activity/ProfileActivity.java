package com.example.yallah_project.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;

public class ProfileActivity extends BaseActivity {


     private TextView nameProfil , emailProfil , ageProfil ;
private de.hdodenhof.circleimageview.CircleImageView profileImageView ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profil) ;
        nameProfil =findViewById(R.id.nameProfil) ;
        emailProfil = findViewById(R.id.emailProfil) ;
        ageProfil = findViewById(R.id.ageProfil) ;
        profileImageView = findViewById(R.id.profileImageView) ;
        User user = getIntent().getParcelableExtra("user") ;
        nameProfil.setText(user.getName()) ;
        emailProfil.setText(user.getEmail()) ;
        ageProfil.setText(user.getAge().toString()) ;
        byte[] imageBytes = user.getProfilePicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImageView.setImageBitmap(bitmap);

    }











}
