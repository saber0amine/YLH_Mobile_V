package com.example.yallah_project.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.yallah_project.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button   loginButton, registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getStartedButton = findViewById(R.id.GetStartedButton);
        loginButton = findViewById(R.id.LoginButton);
        registerButton = findViewById(R.id.RegisterButton);

       // getStartedButton.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        registerButton.setOnClickListener(this);

        VideoView videoView = findViewById(R.id.videoView);
        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.welcomevideo);
        videoView.setVideoURI(uri);
        videoView.start();
        videoView.setOnPreparedListener(mp -> mp.setLooping(true));  // Loop the video
    }

    @Override
    public void onClick(View v) {
     /*        if(v.getId() ==  R.id.GetStartedButton ) {
                Intent getStartedIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(getStartedIntent);

                }*/

        if(v.getId() ==  R.id.LoginButton )
        {Intent loginIntent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(loginIntent);
          }

        if(v.getId() ==  R.id.RegisterButton )               {
            Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);

        }
        }
    }

