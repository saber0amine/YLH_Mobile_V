package com.example.yallah_project.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
 import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel  ;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.text.ParseException  ;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener  {
    private static final int PICK_IMAGE_REQUEST = 1;

    private Button registerButton ;
    private Button loginbutton ;

    private EditText registerName ;
    private EditText registerEmail ;
    private EditText registerPassword ;
    private EditText registerAge ;
     private TextView registerError ;

     private Button uploadButton2 ;

     private Bitmap profileBitmap ;
     private ImageView ProfilePicture ;

     private UserViewModel userViewModel ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register) ;
        registerButton =findViewById(R.id.registerButton2) ;
        registerButton.setOnClickListener(this) ;
        loginbutton =findViewById(R.id.loginButton2) ;
        loginbutton.setOnClickListener(this) ;
        uploadButton2 = findViewById(R.id.uploadButton2) ;
        uploadButton2.setOnClickListener(this) ;
        ProfilePicture = findViewById(R.id.ProfilePicture) ;
        registerName =findViewById(R.id.registerName) ;
         registerEmail = findViewById(R.id.registerEmail);
         registerPassword = findViewById(R.id.registerPassword);
         registerAge = findViewById(R.id.registerAge);
         registerAge.setOnClickListener(this);
          registerError = findViewById(R.id.registerErrors) ;


          userViewModel = new ViewModelProvider(this).get(UserViewModel.class) ;

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.uploadButton2) {
            AddProfilePicture() ;
        }


        if(v.getId() == R.id.registerAge) {
          DatePickerForAge() ;
        }

        if ( v.getId() == R.id.registerButton2){
           if ( !registerEmail.getText().toString().isEmpty() &&
                !registerName.getText().toString().isEmpty() &&
                !registerPassword.getText().toString().isEmpty() &&
                !registerAge.getText().toString().isEmpty() )
           {
                 saveUser() ;
               Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
               startActivity(intent);
           }
           else {
               registerError.setText("Error in your resgistartion !");
           }
        }

        if ( v.getId() == R.id.loginButton2){
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }


    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    // Handle the returned Uri
                    try {
                        profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        ProfilePicture.setImageBitmap(profileBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void AddProfilePicture() {
        mGetContent.launch("image/*");
    }


    private void DatePickerForAge() {
        Calendar calendar = Calendar.getInstance() ;
        int annee = calendar.get(Calendar.YEAR) ;
        int mois = calendar.get(Calendar.MONTH) ;
        int jour = calendar.get(Calendar.DAY_OF_MONTH) ;

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                registerAge.setText(dayOfMonth +"/" +(month  + 1 ) + "/" + year ) ;
            }
        } , annee , mois , jour ) ;

        datePickerDialog.show();
    }



    public void saveUser() {
        User user = new User();
        user.setName(registerName.getText().toString());
        user.setEmail(registerEmail.getText().toString());
        user.setPassword(registerPassword.getText().toString());
        user.setRole(UserRole.USER);
        String dateString = registerAge.getText().toString();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date date = format.parse(dateString);
            user.setAge(date);
        } catch (ParseException e) {
            e.printStackTrace();
         }

        if (profileBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            user.setProfilePicture(imageBytes);
        }
        userViewModel.insert(user);



    }



























}