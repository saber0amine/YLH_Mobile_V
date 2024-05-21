package com.example.yallah_project.Fragment;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallah_project.R;
import com.example.yallah_project.adapter.LocationAdapter;
import com.example.yallah_project.activity.MapsActivity;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.example.yallah_project.model.Location;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class StepThree_Fragment extends Fragment implements View.OnClickListener {

    private static final int REQUEST_CODE_MAP = 1;

    private Button buttonAddLocation;
    private RecyclerView recyclerViewLocations;
    private Button buttonPreviousStep2;
    private Button buttonSubmitActivity;
    private TextView serverResult;

    private List<Location> locations = new ArrayList<>();
    private UserViewModel userViewModel;
    private LocationAdapter locationAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_step_three_, container, false);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonAddLocation = view.findViewById(R.id.button_add_location);
        recyclerViewLocations = view.findViewById(R.id.recyclerView_locations);
        buttonPreviousStep2 = view.findViewById(R.id.button_previous);
        buttonSubmitActivity = view.findViewById(R.id.button_submit_activity);
        serverResult = view.findViewById(R.id.serverResult);

        buttonAddLocation.setOnClickListener(this);
        buttonPreviousStep2.setOnClickListener(this);
        buttonSubmitActivity.setOnClickListener(this);

        // Initialize the RecyclerView and LocationAdapter
        locationAdapter = new LocationAdapter(getContext(), locations);
        recyclerViewLocations.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewLocations.setAdapter(locationAdapter);

        return view;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_add_location) {
            // Open MapsActivity to select location
            Intent intent = new Intent(getActivity(), MapsActivity.class);
            startActivityForResult(intent, REQUEST_CODE_MAP);
        } else if (v.getId() == R.id.button_previous) {
            requireActivity().onBackPressed();
        } else if (v.getId() == R.id.button_submit_activity) {
            submitActivity();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_MAP && resultCode == RESULT_OK && data != null) {
            double latitude = data.getDoubleExtra("latitude", 0);
            double longitude = data.getDoubleExtra("longitude", 0);
            String address = data.getStringExtra("address");
            Location location = new Location(address, latitude, longitude);
            locations.add(location);
            locationAdapter.notifyDataSetChanged();
        }
    }

    private void submitActivity() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<String> imagePaths = bundle.getStringArrayList("filePaths");
            String activityJson = bundle.getString("activity");
            Log.i("CreateActivity", "from the step three " + imagePaths + " " + activityJson);
            Log.i("CreateActivity", "from step three the Json Activity  " + activityJson);

            List<MultipartBody.Part> imageParts = new ArrayList<>();
            for (String path : imagePaths) {
                File file = new File(path);
                RequestBody fileBody = RequestBody.create(MediaType.parse("image/*"), file);
                MultipartBody.Part part = MultipartBody.Part.createFormData("images", file.getName(), fileBody);
                imageParts.add(part);
            }

            RequestBody activityBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), activityJson);
            String locationsJson = new Gson().toJson(locations);
            RequestBody locationsBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), locationsJson);

            LiveData<String> response = userViewModel.createActivity(activityBody, imageParts, locationsBody);
            response.observe(this, s -> serverResult.setText(s)   );
        }
    }
}
