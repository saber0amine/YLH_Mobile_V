package com.example.yallah_project.Fragment;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import android.widget.ArrayAdapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.nav_layout_all;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.model.ActivityStatus;
import com.example.yallah_project.model.Location;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


public class StepOne_Fragment extends Fragment implements  View.OnClickListener {

    Spinner spinnerCategory;
    EditText editTextName , editTextDescription , editTextLocation , editTextStartDate , editTextEndDate , editTextPrice , editTextCapacity , editTextMaxAge , editTextMinAge ;
    Button buttonCreateActivity , buttonBack ;
    Location LocationValue ;
    UserViewModel userViewModel  ;
    List<File> ActivityFileFromStepTwoFrom = new ArrayList<>( )  ;
    List<String> selectedImages = new ArrayList<>() ;
    TextView serverResult ;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_step_one_, container, false);
         editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextLocation = view.findViewById(R.id.editTextLocation);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextCapacity = view.findViewById(R.id.editTextCapacity);
        editTextMaxAge = view.findViewById(R.id.editTextMaxAge);
        editTextMinAge = view.findViewById(R.id.editTextMinAge);
        serverResult = view.findViewById(R.id.serverResult);
        buttonBack =view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        LocationValue = new Location();
        buttonCreateActivity = view.findViewById(R.id.buttonCreateActivity);
        buttonCreateActivity.setOnClickListener(this);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<ActivityCategorie> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ActivityCategorie.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        Bundle bundle = getArguments();
        Log.i("CreateActivity", "bundle " +bundle);

        if ( bundle != null) {
        selectedImages =  bundle.getStringArrayList ("filePaths");
            }
    return  view ;
    }

    @Override
    public void onClick(View v) {

        if ( v.getId() == R.id.buttonCreateActivity){
       /*     if(spinnerCategory.getSelectedItem() != null
                    && editTextName.getText().toString().length() > 0
                    && editTextDescription.getText().toString().length() > 0
                    && editTextLocation.getText().toString().length() > 0
                    && editTextPrice.getText().toString().length() > 0
                    && editTextCapacity.getText().toString().length() > 0
                    && editTextMaxAge.getText().toString().length() > 0
                    && editTextMinAge.getText().toString().length() > 0 ) {}*/
            sendActivtyToServer();
        }


        if(v.getId() == R.id.buttonBack){
            Intent intent = new Intent(getActivity(), nav_layout_all.class);
            Log.i("CreateActivity", "buttonBack");
            startActivity(intent);
        }

    }

    private void sendActivtyToServer() {
        Log.i("CreateActivity", "inside sendActivtyToServer");

        Activity activity = new Activity();
        activity.setActivityCategorie((ActivityCategorie) spinnerCategory.getSelectedItem());
        activity.setName(editTextName.getText().toString());
        activity.setDescription(editTextDescription.getText().toString());
        LocationValue.setCity(editTextLocation.getText().toString());
        activity.setLocation(LocationValue);
        activity.setPrice(Long.parseLong(editTextPrice.getText().toString()));
        activity.setCapacity(Integer.parseInt(editTextCapacity.getText().toString()));
        activity.setMinAge(Integer.parseInt(editTextMinAge.getText().toString()));
        activity.setMaxAge(Integer.parseInt(editTextMaxAge.getText().toString()));
        activity.setStatus(ActivityStatus.PENDING);
        if (selectedImages.size() > 0) {
            Log.i("CreateActivity", "selectedImages.size() > 0");

            //activity.setActivityImages(selectedImages); I wiill recivie the uri form spring and store them locally
            activity.setActivityImages(selectedImages);
        }
        String activityJson = new Gson().toJson(activity);
        RequestBody activityBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), activityJson);

        // Prepare image parts
        List<MultipartBody.Part> imageParts = new ArrayList<>();
        for (String path : selectedImages) {
            File file = new File(path);
            RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), fileBody);
            imageParts.add(part);
            Log.i("CreateActivity", "imageParts"+imageParts.toString())     ;

        }

        LiveData<String> response = userViewModel.createActivity(activityBody, imageParts);
        response.observe(this, s -> {
            Log.i("CreateActivity", "in observer");

            serverResult.setText(s);
        });

    }

    private List<File> stringToFile(List<String> selectedImages) {
        List<File> fileImages  =new ArrayList<>()  ;
        for (String path : selectedImages ) {
            File file = new File(path) ;
            fileImages.add(file) ;
        }
        Log.i("CreateActivity", "fileImages"+fileImages.toString())     ;

        return  fileImages ;

    }


}