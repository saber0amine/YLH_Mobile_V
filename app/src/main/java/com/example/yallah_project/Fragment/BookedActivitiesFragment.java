package com.example.yallah_project.Fragment;

import android.os.Build;
import android.os.Bundle;
        import androidx.fragment.app.Fragment;
        import androidx.lifecycle.ViewModelProvider;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import com.example.yallah_project.R;
        import com.example.yallah_project.adapter.BookedActivitiesAdapter;
        import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.model.ActivityCategorie;
import com.example.yallah_project.viewmodel.UserViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
        import com.github.mikephil.charting.data.PieDataSet;
        import com.github.mikephil.charting.data.PieEntry;
        import com.github.mikephil.charting.utils.ColorTemplate;

        import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BookedActivitiesFragment extends Fragment {

    private UserViewModel userViewModel;
    private RecyclerView recyclerViewBooked;
    private BookedActivitiesAdapter activityAdapter;
    private PieChart pieChart;
    private BarChart barChart  ;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_activities, container, false);

        recyclerViewBooked = view.findViewById(R.id.recyclerViewBooked);
        pieChart = view.findViewById(R.id.pieChart);
         barChart = view.findViewById(R.id.barChart);

        recyclerViewBooked.setLayoutManager(new LinearLayoutManager(getContext()));
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        userViewModel.gettAllBookedActivities().observe(getViewLifecycleOwner(), bookedActivities -> {
            if (activityAdapter == null) {
                activityAdapter = new BookedActivitiesAdapter(getContext(), bookedActivities);
                recyclerViewBooked.setAdapter(activityAdapter);
            } else {
                activityAdapter.setActivityList(bookedActivities);
                activityAdapter.notifyDataSetChanged();
            }
            setupPieChart(bookedActivities);
            setupBarChart(bookedActivities);
        });

        return view;
    }

private void setupBarChart(List<ActivityDto> activities) {
    List<BarEntry> entries = new ArrayList<>();
    if(activities == null || activities.isEmpty()) {
        return;
    }
    for (int i = 0; i < activities.size(); i++) {
        String duration = activities.get(i).getDuration();
        int totalMinutes = convertDurationToMinutes(duration);
        entries.add(new BarEntry(i, totalMinutes));
    }

    BarDataSet dataSet = new BarDataSet(entries, "Activity Duration");
    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    BarData data = new BarData(dataSet);

    barChart.setData(data);
    barChart.invalidate(); // refresh
}
    private int convertDurationToMinutes(String duration) {
        String[] parts = duration.split(", ");
        int days = Integer.parseInt(parts[0].split(" ")[0]);
        int hours = Integer.parseInt(parts[1].split(" ")[0]);
        int minutes = Integer.parseInt(parts[2].split(" ")[0]);

        return days * 24 * 60 + hours * 60 + minutes;
    }

private void setupPieChart(List<ActivityDto> activities) {
        if(activities == null || activities.isEmpty()) {
            return;
        }
    Map<ActivityCategorie, Integer> categoryCount = new HashMap<>();
    for (ActivityDto activity : activities) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            categoryCount.put(activity.getActivityCategorie(), categoryCount.getOrDefault(activity.getActivityCategorie(), 0) + 1);
        }
    }

    List<PieEntry> entries = new ArrayList<>();
    for (Map.Entry<ActivityCategorie, Integer> entry : categoryCount.entrySet()) {
        entries.add(new PieEntry(entry.getValue(), entry.getKey().toString()));
    }

    PieDataSet dataSet = new PieDataSet(entries, "Activity Categories");
    dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
    PieData data = new PieData(dataSet);
    pieChart.setData(data);
    pieChart.invalidate();
}


}
