package com.example.yallah_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity  extends AppCompatActivity  implements  View.OnClickListener {

    private TextView nameProfil, emailProfil, testSwitch;
    Button switchButton;
    User user ;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profil);
        switchButton  = findViewById(R.id.switch_to_organisateur_button);
        switchButton.setOnClickListener(this);
        nameProfil = findViewById(R.id.nameProfil);
        emailProfil = findViewById(R.id.emailProfil);
         testSwitch = findViewById(R.id.testSwitch);
        CircleImageView profileImageView = findViewById(R.id.profileImageView);
         user  = getIntent().getParcelableExtra("user") ;
        if(user !=  null ) {
        Log.d("userProfil",user.toString());
        nameProfil.setText(user.getName());
        emailProfil.setText(user.getEmail());
        byte[] imageBytes = user.getProfilePicture();
        Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
        profileImageView.setImageBitmap(bitmap);

        if (user.getRole() == UserRole.USER) {
            testSwitch.setText("User");
        } else {
            testSwitch.setText("Organisateur");
        }
      }

    }

        @Override
        public void onClick(View v) {
            if(v.getId() == R.id.switch_to_organisateur_button) {
                   Intent  intent = new Intent(ProfileActivity.this, OrganizerRegistrationActivity.class);
                    intent.putExtra("user",  user);
                    startActivity(intent);

                }
        }

}
