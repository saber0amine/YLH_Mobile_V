package com.example.yallah_project.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.ActivityAdapter;
import com.example.yallah_project.viewmodel.UserViewModel;

public class    HomeFragment extends Fragment {
    private UserViewModel userViewModel;
    private RecyclerView recyclerView;
    private ActivityAdapter activityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         View view   = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.getActivities().observe(getViewLifecycleOwner(), activities -> {
            if (activityAdapter == null) {
               // Log.i("gettingActivities", activities.toString());
                activityAdapter = new ActivityAdapter(getContext(), activities);
                recyclerView.setAdapter(activityAdapter);
            } else {
                activityAdapter.setActivityList(activities);
                activityAdapter.notifyDataSetChanged();
            }
        });


        return  view ;
    }
}