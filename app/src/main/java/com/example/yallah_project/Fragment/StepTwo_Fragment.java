package com.example.yallah_project.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.ImageAdapter;

import java.util.ArrayList;
import java.util.List;


public class StepTwo_Fragment extends Fragment {
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<String> selectedImages = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_step_two_, container, false);
        recyclerView = root.findViewById(R.id.imageRecyclerView);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageAdapter = new ImageAdapter(selectedImages);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        recyclerView.setAdapter(imageAdapter);
    }

    // Method to add selected images to the list
    public void addSelectedImages(List<String> images) {
        selectedImages.addAll(images);
        imageAdapter.notifyDataSetChanged();
    }

    // Method to upload selected images
    public void uploadImages() {
        // Implement image upload logic here
    }
}