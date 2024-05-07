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
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;


import com.example.yallah_project.R;

import com.example.yallah_project.model.GovernmentIdType;

import com.example.yallah_project.viewmodel.UserViewModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OrganizerRegistrationActivity extends AppCompatActivity {
        private Bitmap profileBitmap;
         private ImageView identityImage  , uploadButton  ;
    private GovernmentIdType  governmentIdType  ;
private TextView Errors;
    private Button  SumbitButton , BackButton ;
    UserViewModel userViewModel ;
 RadioGroup identityType ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_organizer_registration);
        Errors = findViewById(R.id.Errors) ;
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;
        uploadButton = findViewById(R.id.uploadButton) ;
        BackButton = findViewById(R.id.BackButton) ;
        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrganizerRegistrationActivity.this, nav_layout_all.class);
            startActivity(intent);
        });
        identityType = findViewById(R.id.identityType) ;
        identityImage = findViewById(R.id.identityImage) ;
        SumbitButton = findViewById(R.id.SumbitButton) ;
        SumbitButton.setOnClickListener(view -> {
            SwitchUser() ;
             }) ;
        identityType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if(checkedId == R.id.identityCardOption) {
              governmentIdType = governmentIdType.IDENTITY_CARD;
        }
        else if(checkedId == R.id.passportOption) {
            governmentIdType = governmentIdType.PASSPORT;

        }

    }
});


        uploadButton.setOnClickListener(view -> {
             addIdentityImage();

        });
    }

    private Boolean SwitchUser() {
        if (profileBitmap != null) {
            //User user = getIntent().getParcelableExtra("user");
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            LiveData<String > serverRes  = userViewModel.switch_to_organisateur(governmentIdType  , imageBytes  ) ;
            serverRes.observe( this , resp-> {
                if ( resp.startsWith("ok"))
                {
                    Errors.setText(resp);
                 }
                else {
                    Errors.setText(resp);
                 }
            } );

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