package com.example.yallah_project.Fragment;

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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.OrganizerRegistrationActivity;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.viewmodel.UserViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    private TextView nameProfil, emailProfil, testSwitch;
    private Button switchButton;
    private User user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile , container, false);
        switchButton = view.findViewById(R.id.switch_to_organisateur_button);
        nameProfil = view.findViewById(R.id.nameProfil);
        emailProfil = view.findViewById(R.id.emailProfil);
        testSwitch = view.findViewById(R.id.testSwitch);
        CircleImageView profileImageView = view.findViewById(R.id.profileImageView);


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

        switchButton.setOnClickListener(this);

        return view;
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
