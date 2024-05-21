package com.example.yallah_project.activity;

import android.animation.ObjectAnimator;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;

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
    private ObjectAnimator rotation;

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

        rotation = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f);
        rotation.setDuration(1000);
        rotation.setRepeatCount(ObjectAnimator.INFINITE);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.uploadButtonImage) {
            addProfilePicture();
        }

        if (v.getId() == R.id.registerAge) {
            datePickerForAge();
        }

        if (v.getId() == R.id.registerButton2) {
            if (isValidInput()) {
                showProgressOverlay();
                saveUser();
            } else {
                showErrorMessage("Fill all fields and try again!");
            }
        }

        if (v.getId() == R.id.loginButton2) {
            navigateToLogin();
        }
    }

    private boolean isValidInput() {
        return !registerEmail.getText().toString().isEmpty() &&
                !registerName.getText().toString().isEmpty() &&
                !registerPassword.getText().toString().isEmpty() &&
                !registerAge.getText().toString().isEmpty();
    }

    private void showErrorMessage(String message) {
        registerError.setText(message);
        registerError.setVisibility(View.VISIBLE);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void showProgressOverlay() {
        progressOverlay.setVisibility(View.VISIBLE);
        rotation.start();
    }

    private void hideProgressOverlay() {
        progressOverlay.setVisibility(View.GONE);
        rotation.cancel();
    }

    private void saveUser() {
        User user = new User();
        user.setName(registerName.getText().toString());
        user.setEmail(registerEmail.getText().toString());
        user.setPassword(registerPassword.getText().toString());
        user.setRole(UserRole.USER);
        user.setAge((registerAge.getText().toString()));

        if (profileBitmap != null) {
            Bitmap resizedBitmap = resizeImage(profileBitmap);
            user.setProfilePicture(compressImage(resizedBitmap));
        }

        LiveData<Boolean> serverResponse = userViewModel.register(user);
        serverResponse.observe(this, response -> {
            hideProgressOverlay();
            if (response != null && response) {
                navigateToLogin();
                finish();
            } else {
                showErrorMessage("User Registration Failed");
            }
        });
    }

    private byte[] compressImage(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos);
        return baos.toByteArray();
    }

    ActivityResultLauncher<String> mGetContent = registerForActivityResult(
            new ActivityResultContracts.GetContent(),
            new ActivityResultCallback<Uri>() {
                @Override
                public void onActivityResult(Uri uri) {
                    if (uri != null) {
                        handleImageUri(uri);
                    }
                }
            });

    private void handleImageUri(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            long imageSize = cursor.getLong(sizeIndex);
            cursor.close();

            if (imageSize > 10 * 1024 * 1024) {
                Toast.makeText(RegisterActivity.this, "Image size must be less than 10MB", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        try {
            profileBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            profilePicture.setImageBitmap(profileBitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addProfilePicture() {
        mGetContent.launch("image/*");
    }

    private void datePickerForAge() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        registerAge.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                }, year, month, day);
        datePickerDialog.show();
    }

    private Bitmap resizeImage(Bitmap originalBitmap) {
        int width = originalBitmap.getWidth();
        int height = originalBitmap.getHeight();
        int maxWidth = 800; // Define your maximum width
        int maxHeight = 800; // Define your maximum height
        if (width > maxWidth || height > maxHeight) {
            float ratioBitmap = (float) width / (float) height;
            float ratioMax = (float) maxWidth / (float) maxHeight;

            int finalWidth = maxWidth;
            int finalHeight = maxHeight;
            if (ratioMax > ratioBitmap) {
                finalWidth = (int) ((float)maxHeight * ratioBitmap);
            } else {
                finalHeight = (int) ((float)maxWidth / ratioBitmap);
            }
            return Bitmap.createScaledBitmap(originalBitmap, finalWidth, finalHeight, true);
        } else {
            return originalBitmap;
        }
    }

}
