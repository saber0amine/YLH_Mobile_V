package com.example.yallah_project.Fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.FormCreateActivityContainer;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.model.ActivityStatus;
import com.example.yallah_project.model.LocalDateTimeDeserializer;
import com.example.yallah_project.model.LocalDateTimeSerializer;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StepOne_Fragment extends Fragment implements View.OnClickListener {

    Spinner spinnerCategory;
    com.google.android.material.textfield.TextInputEditText editTextName, editTextDescription, editTextStartDate, editTextEndDate, editTextPrice, editTextCapacity, editTextMaxAge, editTextMinAge, editTextPublishDate;
    Button buttonBack, buttonNext;
    UserViewModel userViewModel;
    List<String> selectedImages = new ArrayList<>();
    TextView serverResult;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_one_, container, false);
        editTextName = view.findViewById(R.id.editTextName);
        editTextDescription = view.findViewById(R.id.editTextDescription);
        editTextStartDate = view.findViewById(R.id.editTextStartDate);
        editTextEndDate = view.findViewById(R.id.editTextEndDate);
        editTextPrice = view.findViewById(R.id.editTextPrice);
        editTextCapacity = view.findViewById(R.id.editTextCapacity);
        editTextMaxAge = view.findViewById(R.id.editTextMaxAge);
        editTextMinAge = view.findViewById(R.id.editTextMinAge);
        serverResult = view.findViewById(R.id.serverResult);
        buttonBack = view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(this);
        buttonNext = view.findViewById(R.id.buttonNext);
        buttonNext.setOnClickListener(this);
        spinnerCategory = view.findViewById(R.id.spinnerCategory);
        ArrayAdapter<ActivityCategorie> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ActivityCategorie.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapter);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        Bundle bundle = getArguments();
        Log.i("CreateActivity", "bundle " + bundle);

        if (bundle != null) {
            selectedImages = bundle.getStringArrayList("filePaths");
        }

        // Set up date and time pickers
        editTextStartDate.setOnClickListener(this);
        editTextEndDate.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.editTextStartDate) {
            showDateTimePicker(editTextStartDate);
        } else if(v.getId() == R.id.editTextEndDate) {
            showDateTimePicker(editTextEndDate);
        } else if (v.getId() == R.id.buttonNext) {
            sendActivityToServer();
        } else if (v.getId() == R.id.buttonBack) {
            requireActivity().onBackPressed();
        }
    }

    private void showDateTimePicker(EditText editText) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getActivity(),
                (view, year1, month1, dayOfMonth) -> {
                    TimePickerDialog timePickerDialog = new TimePickerDialog(
                            getActivity(),
                            (view1, hourOfDay, minute1) -> {
                                LocalDateTime selectedDate = LocalDateTime.of(year1, month1 + 1, dayOfMonth, hourOfDay, minute1);
                                String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
                                editText.setText(formattedDate);
                            },
                            hour, minute, true);
                    timePickerDialog.show();
                },
                year, month, day);

        datePickerDialog.show();
    }

    private void sendActivityToServer() {
        Log.i("CreateActivity", "inside sendActivityToServer");

        Activity activity = new Activity();
        activity.setActivityCategorie((ActivityCategorie) spinnerCategory.getSelectedItem());
        activity.setName(editTextName.getText().toString());
        activity.setDescription(editTextDescription.getText().toString());
        activity.setPrice(Long.parseLong(editTextPrice.getText().toString()));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        LocalDateTime startDate = LocalDateTime.parse(editTextStartDate.getText().toString(), formatter);
        LocalDateTime endDate = LocalDateTime.parse(editTextEndDate.getText().toString(), formatter);
        LocalDateTime publishDate = LocalDateTime.now();
        activity.setDateOfStart(startDate);
        activity.setDateOfEnd(endDate);
        activity.setDateOfPublish(publishDate);

        Duration duration = Duration.between(startDate, endDate);
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        activity.setDuration(Long.valueOf(String.format("%d days, %d hours, %d minutes", days, hours, minutes)));
        activity.setCapacity(Integer.parseInt(editTextCapacity.getText().toString()));
        activity.setMinAge(Integer.parseInt(editTextMinAge.getText().toString()));
        activity.setMaxAge(Integer.parseInt(editTextMaxAge.getText().toString()));
        activity.setStatus(ActivityStatus.PENDING);

        if (!selectedImages.isEmpty()) {
            activity.setActivityImages(selectedImages);
        }
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .create();
        String activityJson = gson.toJson(activity);  // Use the custom Gson instance
        Log.i("CreateActivity", "from step one the Json Activity " + activityJson);

        Bundle bundle = new Bundle();
        bundle.putString("activity", activityJson);
        bundle.putStringArrayList("filePaths", (ArrayList<String>) selectedImages);
        Log.i("CreateActivity", "from step one to step three " + selectedImages);
        StepThree_Fragment stepThree_fragment = new StepThree_Fragment();
        stepThree_fragment.setArguments(bundle);

        ((FormCreateActivityContainer) requireActivity()).loadFragment(stepThree_fragment);
    }
}
