package com.example.yallah_project.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yallah_project.apis.ApiService;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.dtos.OrganisateurSwitchRequest;
import com.example.yallah_project.model.GovernmentIdType;
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

    private int statusCode ;
    private SharedPrefer sharedPrefer ;

    public UserViewModel(Application application) {
        super(application);
        sharedPrefer    = new SharedPrefer() ;
        String jwt = sharedPrefer.getUserData(getApplication(), "jwt");
        userRepository = new UserRepository(application);
        if(jwt !=null) {
            Log.i("jwt"  ,jwt)  ;
            apiService = RetrofitClient.getClient(jwt).create(ApiService.class);
        }
        else          Log.i("jwt"  ,"nooooo token")  ;

    }




    public void insert(User user) {
        userRepository.insert(user);
    }

// UserViewModel.java
public LiveData<User> getUserByEmailAndPassword(String email, String password) {
    return userRepository.getUserByEmailAndPassword(email, password);
}


    public LiveData<String> switch_to_organisateur(GovernmentIdType governmentIdType, byte[] governmentIdImage ) {

        MutableLiveData<String> serverResponse = new MutableLiveData<>();
        OrganisateurSwitchRequest  organisateurSwitchRequest= new OrganisateurSwitchRequest() ;
        organisateurSwitchRequest.setGovernmentIdType(governmentIdType);
        organisateurSwitchRequest.setGovernmentIdImage(governmentIdImage);
        Call<ResponseBody> call = apiService.switchToOrganisateur(organisateurSwitchRequest);
        Log.i("userView" , "outside the call") ;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    serverResponse.setValue("ok"+response.body().toString());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        serverResponse.setValue(errorBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                serverResponse.setValue("Check your connection and try again");
                t.printStackTrace();
            }
        });
        return serverResponse  ;
    }


    // Retrofit Actions

    public LiveData<Boolean> register(User user) {
        MutableLiveData<Boolean> serverResponse = new MutableLiveData<>();
        Call<ResponseBody> call = apiService.registerUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    serverResponse.setValue(true);
                    userRepository.insert(user);
                } else {
                    serverResponse.setValue(false);
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });


        return serverResponse;
    }


    public LiveData<String > login(User user) {

        MutableLiveData<String> serverResponse = new MutableLiveData<>();

        Call<ResponseBody> call = apiService.loginUser(user);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                // Handle response from server
                if (response.isSuccessful()) {
                    serverResponse.setValue("ok"+response.body().toString());
                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        serverResponse.setValue(errorBody);

                        Log.i("Body" , errorBody) ;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // Handle network error
                serverResponse.setValue("Check your connection and try again");

                t.printStackTrace();
            }
        });


        return serverResponse;
    }

    public LiveData<User> getUserByEmail(String userEmail) {
        return  userRepository.getUserByEmail(userEmail) ;
    }
}