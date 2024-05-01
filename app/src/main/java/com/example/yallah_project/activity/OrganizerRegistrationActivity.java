package com.example.yallah_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.BookedActivity;
import com.example.yallah_project.model.GovernmentIdType;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OrganizerRegistrationActivity extends AppCompatActivity {
        private Bitmap profileBitmap;
         private ImageView identityImage  , uploadButton  ;
    String IdentityType = "";
private TextView Errors;
    private Button  SumbitButton , BackButton ;
    UserViewModel userViewModel ;
RadioGroup identityType ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_registration);
        Errors = findViewById(R.id.Errors) ;
        userViewModel  = new ViewModelProvider(this).get(UserViewModel.class) ;
        uploadButton = findViewById(R.id.uploadButton) ;
        BackButton =(Button) findViewById(R.id.BackButton) ;
        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrganizerRegistrationActivity.this, HomeActivity.class);
            startActivity(intent);
        });
        identityType = findViewById(R.id.identityType) ;
        identityImage = findViewById(R.id.identityImage) ;
        SumbitButton = findViewById(R.id.SumbitButton) ;
        SumbitButton.setOnClickListener(view -> {
            Boolean okey = SwitchUser() ;
            if (okey) {
                Intent intent = new Intent(OrganizerRegistrationActivity.this, HomeActivity.class);
                startActivity(intent);
            }
            else {
                Errors.setText("Please fill all the fields");
            }

        });
        identityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.identityCardOption) {
                IdentityType = "IDENTITY_CARD";
        }
        else if(checkedId == R.id.passportOption) {
            IdentityType = "PASSPORT";

        }

    }
});


        uploadButton.setOnClickListener(view -> {
             addIdentityImage();

        });
    }

    private Boolean SwitchUser() {
        if (profileBitmap != null) {
            User user = getIntent().getParcelableExtra("user");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            user.setRole(UserRole.ORGANISATEUR) ;
            if (IdentityType.equals("PASSPORT")) {
                user.setGovernmentIdType(GovernmentIdType.PASSPORT);
            }
            else if (IdentityType.equals(GovernmentIdType.IDENTITY_CARD)) {
                user.setIdentityPicture(imageBytes);
            }

            user.setIdentityPicture(imageBytes);
            userViewModel.update(user);
            return true;
        }
        return false;
    }


    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                     try {
                        profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        identityImage.setImageBitmap(profileBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void addIdentityImage() {
        mGetContent.launch("image/*");
    }


}