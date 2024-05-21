package com.example.yallah_project.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OrganizerRegistrationActivity extends AppCompatActivity {
    private Bitmap profileBitmap;
    private ImageView identityImage, uploadButton;
    private GovernmentIdType governmentIdType;
    private TextView Errors;
    private Button SubmitButton, BackButton;
    private UserViewModel userViewModel;
    private RadioGroup identityType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_organizer_registration);

        Errors = findViewById(R.id.Errors);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        uploadButton = findViewById(R.id.uploadButton);
        BackButton = findViewById(R.id.BackButton);
        BackButton.setOnClickListener(view -> {
            Intent intent = new Intent(OrganizerRegistrationActivity.this, nav_layout_all.class);
            startActivity(intent);
        });

        identityType = findViewById(R.id.identityType);
        identityImage = findViewById(R.id.identityImage);
        SubmitButton = findViewById(R.id.SubmitButton);
        SubmitButton.setOnClickListener(view -> {
            Log.i("OrganizerRegistration", "Submit button clicked");
            SwitchUser();
        });

        identityType.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.identityCardOption) {
                governmentIdType = GovernmentIdType.IDENTITY_CARD;
                Log.i("OrganizerRegistration", "Selected identity card option");
            } else if (checkedId == R.id.passportOption) {
                governmentIdType = GovernmentIdType.PASSPORT;
                Log.i("OrganizerRegistration", "Selected passport option");
            }
        });

        uploadButton.setOnClickListener(view -> {
            Log.i("OrganizerRegistration", "Upload button clicked");
            addIdentityImage();
        });
    }

    private Boolean SwitchUser() {
        if (profileBitmap != null) {
            if (governmentIdType == null) {
                Errors.setText("Please select an identity type.");
                return false;
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            profileBitmap.compress(Bitmap.CompressFormat.JPEG, 80, baos); // Compress to 80% quality
            byte[] imageBytes = baos.toByteArray();
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
            MultipartBody.Part body = MultipartBody.Part.createFormData("IdentityPicture", "file.jpg", requestFile);
            RequestBody governmentIdTypeStr = RequestBody.create(MediaType.parse("text/plain"), governmentIdType.toString());

            LiveData<String> serverRes = userViewModel.switch_to_organisateur(governmentIdTypeStr, body);
            serverRes.observe(this, resp -> {
                if (resp.startsWith("ok")) {
                    Log.i("OrganizerRegistration", "SwitchUser successful: " + resp);
                    Errors.setText(resp.substring(2));
                } else {
                    Log.i("OrganizerRegistration", "SwitchUser failed: " + resp);
                    Errors.setText(resp);
                }
            });

            return true;
        }
        Log.i("OrganizerRegistration", "Profile bitmap is null");
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
                        Log.i("OrganizerRegistration", "Image selected: " + uri.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("OrganizerRegistration", "Error loading image", e);
                    }
                }
            });

    private void addIdentityImage() {
        Log.i("OrganizerRegistration", "Launching image picker");
        mGetContent.launch("image/*");
    }
}
