package com.example.yallah_project.activity;


import android.os.Bundle;

 import android.view.View;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;


 import com.example.yallah_project.Fragment.StepTwo_Fragment;
import com.example.yallah_project.R;
import com.example.yallah_project.viewmodel.ActivityViewModel;


public class FormCreateActivityContainer extends AppCompatActivity implements  View.OnClickListener {


    private ActivityViewModel activityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_activity_layout);

        activityViewModel = new ViewModelProvider(this).get(ActivityViewModel.class);

        // Load the first fragment
        loadFragment(new StepTwo_Fragment());
    }

    public void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public ActivityViewModel getActivityViewModel() {
        return activityViewModel;
    }


    @Override
    public void onClick(View v) {

    }
}
