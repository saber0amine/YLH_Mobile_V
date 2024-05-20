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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button registerButton;
    private Button loginButton;
    private TextInputEditText registerName, registerEmail, registerPassword, registerAge;
    private TextView registerError;
    private ImageView uploadButton2;
    private Bitmap profileBitmap;
    private de.hdodenhof.circleimageview.CircleImageView profilePicture;
    private RelativeLayout progressOverlay;
    private ImageView logoImageView;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        registerButton = findViewById(R.id.registerButton2);
        registerButton.setOnClickListener(this);
        loginButton = findViewById(R.id.loginButton2);
        loginButton.setOnClickListener(this);
        uploadButton2 = findViewById(R.id.uploadButtonImage);
        uploadButton2.setOnClickListener(this);
        profilePicture = findViewById(R.id.ProfilePicture);
        registerName = findViewById(R.id.registerName);
        registerEmail = findViewById(R.id.registerEmail);
        registerPassword = findViewById(R.id.registerPassword);
        registerAge = findViewById(R.id.registerAge);
        registerAge.setOnClickListener(this);
        registerError = findViewById(R.id.registerErrors);
        progressOverlay = findViewById(R.id.progressOverlay);
        logoImageView = findViewById(R.id.logoImageView);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.uploadButton2) {
            addProfilePicture();
        }

        if (v.getId() == R.id.registerAge) {
            datePickerForAge();
        }

        if (v.getId() == R.id.registerButton2) {
            if (!registerEmail.getText().toString().isEmpty() &&
                    !registerName.getText().toString().isEmpty() &&
                    !registerPassword.getText().toString().isEmpty() &&
                    !registerAge.getText().toString().isEmpty()) {

                showProgressOverlay();
                saveUser();
            } else {
                registerError.setText("Fill all fields and try again!");
                registerError.setVisibility(View.VISIBLE);
            }
        }

        if (v.getId() == R.id.loginButton2) {
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    try {
                        profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        profilePicture.setImageBitmap(profileBitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

    private void addProfilePicture() {
        mGetContent.launch("image/*");
    }

    private void datePickerForAge() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                registerAge.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    private void showProgressOverlay() {
        progressOverlay.setVisibility(View.VISIBLE);
        logoImageView.animate().rotationBy(360).setDuration(1000).setDuration(android.view.animation.Animation.INFINITE);
    }

    private void hideProgressOverlay() {
        progressOverlay.setVisibility(View.GONE);
        logoImageView.clearAnimation();
    }

    public void saveUser() {
        User user = new User();
        user.setName(registerName.getText().toString());
        user.setEmail(registerEmail.getText().toString());
        user.setPassword(registerPassword.getText().toString());
        user.setRole(UserRole.USER);

        String dateString = registerAge.getText().toString();
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateString);
           // user.setAge(outputFormat.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (profileBitmap != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] imageBytes = baos.toByteArray();
            user.setProfilePicture(imageBytes);
        }

        LiveData<Boolean> serverResponse = userViewModel.register(user);
        serverResponse.observe(this, response -> {
            hideProgressOverlay();
            if (response != null && response) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                registerError.setText("User Registration Failed");
                registerError.setVisibility(View.VISIBLE);
            }
        });
    }
}
