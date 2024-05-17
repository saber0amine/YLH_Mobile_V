package com.example.yallah_project.Fragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.yallah_project.R;
import com.example.yallah_project.activity.ActivityAdapter;
import com.example.yallah_project.viewmodel.UserViewModel;

public class BookedActivitiesFragment extends Fragment {

    private UserViewModel userViewModel;
    private RecyclerView recyclerViewBooked;
    private ActivityAdapter activityAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_activities, container, false);

        recyclerViewBooked = view.findViewById(R.id.recyclerViewBooked);
        recyclerViewBooked.setLayoutManager(new LinearLayoutManager(getContext()));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.gettAllBookedActivities().observe(getViewLifecycleOwner(), bookedActivities -> {
            if (activityAdapter == null) {
                activityAdapter = new ActivityAdapter(getContext(), bookedActivities);
                recyclerViewBooked.setAdapter(activityAdapter);
            } else {
                activityAdapter.setActivityList(bookedActivities);
                activityAdapter.notifyDataSetChanged();
            }
        });

        return view;
    }
}
