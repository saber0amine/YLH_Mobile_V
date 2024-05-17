package com.example.yallah_project.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.yallah_project.R;
import com.example.yallah_project.activity.FormCreateActivityContainer;
import com.example.yallah_project.activity.ImageAdapter;
import com.example.yallah_project.viewmodel.UserViewModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class StepTwo_Fragment extends Fragment {
    private static final int PICK_IMAGES_REQUEST = 1;
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;
    private List<Uri> selectedImages = new ArrayList<>();
    private List<File> uriTofiles   = new ArrayList<>() ;

    private  List<String> filePaths = new ArrayList<>() ;
    private Button uploadButton;
    private Button next  ;
    private UserViewModel userViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_two_, container, false);
        userViewModel = ((FormCreateActivityContainer) requireActivity()).getActivityViewModel();

        recyclerView = view.findViewById(R.id.imageRecyclerView);
        uploadButton = view.findViewById(R.id.uploadButton2);
        next = view.findViewById(R.id.next) ;
        imageAdapter = new ImageAdapter(selectedImages);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 1 ));
        recyclerView.setAdapter(imageAdapter);

        uploadButton.setOnClickListener(v -> openImagePicker());
        next.setOnClickListener(v -> {
            if ( selectedImages.size() > 0 ) {
                StepOne_Fragment stepOne_fragment = new StepOne_Fragment();
                Bundle bundle= new Bundle();
                for (File file : uriTofiles) {
                    filePaths.add(file.getAbsolutePath());
                }
                bundle.putStringArrayList("filePaths", (ArrayList<String>) filePaths);
                Log.i("CreateActivity", "from step two to step one " + filePaths.toString() + "    " + filePaths.size());

                stepOne_fragment.setArguments(bundle);
                ((FormCreateActivityContainer) requireActivity()).loadFragment( stepOne_fragment);

            }
        });
        return view;
    }

    private void openImagePicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select Pictures"), PICK_IMAGES_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGES_REQUEST && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    selectedImages.add(imageUri);
                    uriTofiles.add(uriTofile(imageUri , requireContext())) ;
                    Log.i("selectedImages", selectedImages.toString());

                }
            } else if (data.getData() != null) {
                Uri imageUri = data.getData();
                selectedImages.add(imageUri);
            }
            imageAdapter.notifyDataSetChanged();
        }

    }


    public File uriTofile(Uri uri , Context context )
    {

        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            File file = new File(context.getCacheDir(), "tempImage");
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int read  ;
            while ((read = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, read);
            }

            fileOutputStream.flush();
            fileOutputStream.close();
            inputStream.close();
            Log.i("CreateActivity", "from uri to file " + file.getAbsolutePath() + "    " + file.getName());

            return file;



        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
