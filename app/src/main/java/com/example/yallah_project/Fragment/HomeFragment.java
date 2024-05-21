package com.example.yallah_project.Fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallah_project.R;
import com.example.yallah_project.adapter.ActivityAdapter;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;
    private EditText editTextSearch;
    private Spinner categorySpinner;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        editTextSearch = view.findViewById(R.id.editTextSearch);
        categorySpinner = view.findViewById(R.id.categorySpinner);
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        activityAdapter = new ActivityAdapter(getContext(), new ArrayList<>() , getViewLifecycleOwner()  )   ;
        recyclerView.setAdapter(activityAdapter);

        // Set up the category spinner
        ArrayAdapter<ActivityCategorie> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, ActivityCategorie.values());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categorySpinner.setAdapter(adapter);

        // Fetch initial data
        userViewModel.getFilteredActivities("", "").observe(getViewLifecycleOwner(), activities -> {
            activityAdapter.setActivityList(activities);
            activityAdapter.notifyDataSetChanged();
        });

        // Add text watcher for search query
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                fetchFilteredActivities();
            }
        });

        // Add listener for category selection
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fetchFilteredActivities();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        return view;
    }

    private void fetchFilteredActivities() {
        String query = editTextSearch.getText().toString();
        String category = categorySpinner.getSelectedItem().toString();
        userViewModel.getFilteredActivities(query, category).observe(getViewLifecycleOwner(), activities -> {
            activityAdapter.setActivityList(activities);
            activityAdapter.notifyDataSetChanged();
        });
    }
}
