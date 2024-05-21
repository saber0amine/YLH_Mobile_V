package com.example.yallah_project.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.OrganizerRegistrationActivity;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private static final int PICK_IMAGE_REQUEST = 1;

    private TextView nameProfil, emailProfil, testSwitch, userBio;
    private EditText userBioEdit;
    private Button switchButton, editProfileButton, saveChangesButton;
    private CircleImageView profileImageView;
    private User user;
    private Bitmap newProfileImageBitmap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        switchButton = view.findViewById(R.id.switch_to_organisateur_button);
        nameProfil = view.findViewById(R.id.nameProfil);
        emailProfil = view.findViewById(R.id.emailProfil);
        testSwitch = view.findViewById(R.id.testSwitch);
        userBio = view.findViewById(R.id.userBio);
        userBioEdit = view.findViewById(R.id.userBioEdit);
        editProfileButton = view.findViewById(R.id.EditProfile);
        saveChangesButton = view.findViewById(R.id.saveProfileChanges);
        profileImageView = view.findViewById(R.id.profileImageView);

        user = getArguments().getParcelable("user");
        if (user != null) {
            Log.d("userProfil", user.toString());
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

        profileImageView.setOnClickListener(v -> openImageChooser());
        editProfileButton.setOnClickListener(v -> enableEditing());
        saveChangesButton.setOnClickListener(v -> saveProfileChanges());

        switchButton.setOnClickListener(this);

        return view;
    }

    private void openImageChooser() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            try {
                newProfileImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                profileImageView.setImageBitmap(newProfileImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void enableEditing() {
        userBio.setVisibility(View.GONE);
        userBioEdit.setVisibility(View.VISIBLE);
        userBioEdit.setText(userBio.getText());
        saveChangesButton.setVisibility(View.VISIBLE);
    }

    private void saveProfileChanges() {
        String updatedBio = userBioEdit.getText().toString();
        userBio.setText(updatedBio);
        userBio.setVisibility(View.VISIBLE);
        userBioEdit.setVisibility(View.GONE);
        saveChangesButton.setVisibility(View.GONE);

        // Save profile image if changed
        if (newProfileImageBitmap != null) {
            // Convert the Bitmap to a byte array or save it to your server or local storage
            // Example of converting bitmap to byte array:
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            newProfileImageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] imageBytes = stream.toByteArray();
            user.setProfilePicture(imageBytes);
        }


        Toast.makeText(getActivity(), "Profile updated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch_to_organisateur_button) {
            Intent intent = new Intent(getActivity(), OrganizerRegistrationActivity.class);
            intent.putExtra("user", user);
            startActivity(intent);
        }
    }
}
