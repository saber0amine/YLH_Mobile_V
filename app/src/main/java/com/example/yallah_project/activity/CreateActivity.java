package com.example.yallah_project.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.yallah_project.R;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.model.ActivityStatus;
import com.example.yallah_project.model.Location;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

public class CreateActivity  extends AppCompatActivity implements  View.OnClickListener {

    Spinner spinnerCategory;
    EditText  editTextName , editTextDescription , editTextLocation , editTextStartDate , editTextEndDate , editTextPrice , editTextCapacity , editTextMaxAge , editTextMinAge ;
    Button buttonCreateActivity , buttonBack ;
    Location LocationValue ;
    UserViewModel userViewModel  ;

    TextView serverResult ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_layout);
        editTextName = findViewById(R.id.editTextName);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextLocation = findViewById(R.id.editTextLocation);
        editTextStartDate = findViewById(R.id.editTextStartDate);
        editTextEndDate = findViewById(R.id.editTextEndDate);
        editTextPrice = findViewById(R.id.editTextPrice);
        editTextCapacity = findViewById(R.id.editTextCapacity);
        editTextMaxAge = findViewById(R.id.editTextMaxAge);
        editTextMinAge = findViewById(R.id.editTextMinAge);
        serverResult = findViewById(R.id.serverResult);
        buttonBack =findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
      LocationValue = new Location();
     buttonCreateActivity = findViewById(R.id.buttonCreateActivity);
        buttonCreateActivity.setOnClickListener(this);
                spinnerCategory = findViewById(R.id.spinnerCategory);
        ArrayAdapter<ActivityCategorie> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, ActivityCategorie.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);

                userViewModel = new ViewModelProvider(this).get(UserViewModel.class);


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
            Intent intent = new Intent(this, nav_layout_all.class);
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
        LiveData<String> response = userViewModel.createActivity(activity);
        response.observe(this, s -> {
            Log.i("CreateActivity", "in observer");

            serverResult.setText(s);
        });

    }


}
