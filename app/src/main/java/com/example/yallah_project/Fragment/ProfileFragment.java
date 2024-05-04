package com.example.yallah_project.Fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.yallah_project.R;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private TextView nameProfil, emailProfil, ageProfil, testSwitch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_profil, container, false);

        nameProfil = view.findViewById(R.id.nameProfil);
        emailProfil = view.findViewById(R.id.emailProfil);
        ageProfil = view.findViewById(R.id.ageProfil);
        testSwitch = view.findViewById(R.id.testSwitch);
        CircleImageView profileImageView = view.findViewById(R.id.profileImageView);

User user = null;
if (getArguments() != null) {
    user = getArguments().getParcelable("user");
}
if (user != null) {
            nameProfil.setText(user.getName());
            emailProfil.setText(user.getEmail());
            ageProfil.setText(user.getAge().toString());
            byte[] imageBytes = user.getProfilePicture();
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            profileImageView.setImageBitmap(bitmap);

            if (user.getRole() == UserRole.USER) {
                testSwitch.setText("User");
            } else {
                testSwitch.setText("Organisateur");
            }
        }

        return view;
    }
}