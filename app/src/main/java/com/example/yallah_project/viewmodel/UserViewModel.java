package com.example.yallah_project.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yallah_project.apis.ApiService;
import com.example.yallah_project.model.User;
import com.example.yallah_project.network.RetrofitClient;
import com.example.yallah_project.repository.UserRepository;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserViewModel extends AndroidViewModel {

    private ApiService apiService;
    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    private int statusCode ;

    public UserViewModel(Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.getAllUsers();
        apiService = RetrofitClient.getClient().create(ApiService.class);

    }


    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    public void insert(User user) {
        userRepository.insert(user);
    }

// UserViewModel.java
public LiveData<User> getUserByEmailAndPassword(String email, String password) {
    return userRepository.getUserByEmailAndPassword(email, password);
}


    public void update(User user) {
        userRepository.update(user);
    }


    // Retrofit Actions

    public LiveData<Boolean> insertUser(User user) {

        MutableLiveData<Boolean> serverResponse = new MutableLiveData<>();

        Call<ResponseBody> call = apiService.registerUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // Handle response from server
                if (response.isSuccessful()) {
                    serverResponse.setValue(true);
                } else {
                    serverResponse.setValue(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle network error
                t.printStackTrace();
            }
        });


        return serverResponse;
    }







}