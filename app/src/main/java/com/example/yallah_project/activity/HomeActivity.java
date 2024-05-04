package com.example.yallah_project.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yallah_project.R;
import com.example.yallah_project.databinding.HomeBinding;
import com.example.yallah_project.model.User;

import java.net.UnknownServiceException;

public class HomeActivity extends BaseActivity  implements  View.OnClickListener{
     private TextView username ;
    //private ImageView profilePicture ;
    private Button menuButton ;
private Button switch_to_organisateur_button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
         username  = findViewById(R.id.username) ;
         switch_to_organisateur_button = findViewById(R.id.switch_to_organisateur_button) ;
menuButton = findViewById(R.id.menu_button);
         menuButton.setOnClickListener(this);
switch_to_organisateur_button.setOnClickListener(this);
//profilePicture = findViewById(R.id.profilePicture) ;
   //     profilePicture.setOnClickListener(this);









    }


    @Override
    public void onClick(View v) {
        Intent intent ;
        //User     userAuthenticated = getUserAuthenticated();

        if (v.getId() == R.id.switch_to_organisateur_button) {
              intent = new Intent(HomeActivity.this, OrganizerRegistrationActivity.class) ;
            startActivity(intent) ;
        }

        if (v.getId() == R.id.menu_button) {
            intent = new Intent(HomeActivity.this,  nav_layout_all.class);
                startActivity(intent);
        }


       /* if (v.getId() == R.id.profilePicture) {

            intent = new Intent(HomeActivity.this, ProfileActivity.class);
            intent.putExtra("user", userAuthenticated);
            startActivity(intent);

        }*/

       /* if(v.getId() == R.id.switch_to_organisateur_button) {

            intent = new Intent(HomeActivity.this, OrganizerRegistrationActivity.class);
            intent.putExtra("user",  userAuthenticated);
            startActivity(intent);

        }

*/
    }
}