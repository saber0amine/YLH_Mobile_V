package com.example.yallah_project.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.yallah_project.R;
import com.example.yallah_project.adapter.DashboardAdapter;
import com.example.yallah_project.dtos.OrgnizerActivitiesDto;
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
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.components.XAxis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OrganizerDashboard extends AppCompatActivity {

    private PieChart chartTotalActivities;
    private BarChart chartBookedUsers;
    private RecyclerView recyclerViewActivities;
    private DashboardAdapter adapter;

    private UserViewModel userViewModel;
    private OrgnizerActivitiesDto organizerActivities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.organizer_dashbaord_layout);

        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        chartTotalActivities = findViewById(R.id.chart_total_activities);
        chartBookedUsers = findViewById(R.id.chart_booked_users);
        recyclerViewActivities = findViewById(R.id.recycler_view_activities);

        userViewModel.getOrganizerActivitiesDetails().observe(this, organizerActivitiesDto -> {
            organizerActivities = organizerActivitiesDto;
            setupRecyclerView();
            setupCharts();
        });
    }

    private void setupRecyclerView() {
        List<String> activityNames = new ArrayList<>(organizerActivities.getActivitiesNameAndId().keySet());
        adapter = new DashboardAdapter(activityNames, position -> {
            String activityName = activityNames.get(position);
            UUID activityId = organizerActivities.getActivitiesNameAndId().get(activityName);
            removeActivity(activityId, position);
        });
        recyclerViewActivities.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewActivities.setAdapter(adapter);
    }

    private void setupCharts() {
        setupPieChart();
        setupBarChart();
    }

    private void setupPieChart() {
        List<PieEntry> entries = new ArrayList<>();
        for (Map.Entry<ActivityCategorie, Long> entry : organizerActivities.getActivitiesTotalByCategory().entrySet()) {
            entries.add(new PieEntry(entry.getValue(), entry.getKey().name()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "Categories");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set multiple colors
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        PieData data = new PieData(dataSet);
        chartTotalActivities.setData(data);
        chartTotalActivities.invalidate();
    }

    private void setupBarChart() {
        List<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        int index = 0;
        for (Map.Entry<String, Integer> entry : organizerActivities.getActivitiesNameandBookedUser().entrySet()) {
            entries.add(new BarEntry(index++, entry.getValue()));
            labels.add(entry.getKey());
        }
        BarDataSet dataSet = new BarDataSet(entries, "Booked Users");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS); // Set multiple colors
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(12f);
        BarData data = new BarData(dataSet);
        chartBookedUsers.setData(data);

        XAxis xAxis = chartBookedUsers.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f); // Minimum axis-step (interval) is 1
        xAxis.setGranularityEnabled(true);

        chartBookedUsers.invalidate();
    }

    private void removeActivity(UUID activityId, int position) {
        // Call API to remove activity
        userViewModel.removeActivity(activityId).observe(this, isSuccess -> {
            if (isSuccess != null) {
                List<String> activityNames = new ArrayList<>(organizerActivities.getActivitiesNameAndId().keySet());
                organizerActivities.getActivitiesNameAndId().remove(activityNames.get(position));
                organizerActivities.getActivitiesNameandBookedUser().remove(activityNames.get(position));
                adapter.notifyItemRemoved(position);
                Toast.makeText(this, "Activity removed successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to remove activity", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
