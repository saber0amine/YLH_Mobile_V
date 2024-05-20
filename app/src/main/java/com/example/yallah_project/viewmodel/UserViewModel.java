package com.example.yallah_project.viewmodel;

import android.app.Application;
import android.util.Log;
import android.view.Choreographer;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.yallah_project.activity.OrganizerDashboard;
import com.example.yallah_project.apis.ApiService;
import com.example.yallah_project.database.SharedPrefer;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.dtos.OrganisateurSwitchRequest;
import com.example.yallah_project.dtos.OrgnizerActivitiesDto;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.Location;
import com.example.yallah_project.model.User;
import com.example.yallah_project.model.UserRole;
import com.example.yallah_project.network.RetrofitClient;
import com.example.yallah_project.repository.UserRepository;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
        else   {       apiService = RetrofitClient.getClient(null).create(ApiService.class);
        Log.i("jwt"  ,"nooooo token")  ;
            }
    }




    public void insert(User user) {
        userRepository.insert(user);
    }

// UserViewModel.java
public LiveData<User> getUserByEmailAndPassword(String email, String password) {
    return userRepository.getUserByEmailAndPassword(email, password);
}


    public LiveData<String> switch_to_organisateur(RequestBody governmentIdType, MultipartBody.Part governmentIdImage ) {

        MutableLiveData<String> serverResponse = new MutableLiveData<>();
//        OrganisateurSwitchRequest  organisateurSwitchRequest= new OrganisateurSwitchRequest() ;
//        organisateurSwitchRequest.setGovernmentIdType(governmentIdType);
//        organisateurSwitchRequest.setGovernmentIdImage(governmentIdImage);
        Call<ResponseBody> call = apiService.switchToOrganisateur(governmentIdType , governmentIdImage);
        Log.i("userView" , "outside the call") ;
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        serverResponse.setValue("ok"+response.body().string());
                        userRepository.setRole(sharedPrefer.getUserData(getApplication() , "email") ,  UserRole.ORGANISATEUR) ;
                        Log.i("userView" , "server response is ok"+response.body().string()) ;

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                } else {
                    try {
        Log.i("userView", "server response is not ok, status code: " + response.code() + ", error body: " + response.errorBody().string());
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
                    String jwt = response.headers().get("Authorization");
                    if (jwt != null && jwt.startsWith("Bearer ")) {
                        jwt = jwt.substring(7);
                    }
                    serverResponse.setValue("ok" + jwt);
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

    public LiveData<String> createActivity(RequestBody activity ,  List<MultipartBody.Part> imageParts ,RequestBody locations) {
        MutableLiveData<String>  serverResponse = new MutableLiveData<>() ;
        Log.i("CreateActivity", "from step one the Json Activity  " + activity.toString());
        Log.i("CreateActivity", "from step one the Json Activity  " + locations.toString());

        Call<ResponseBody> call = apiService.createActivity(activity , imageParts , locations);
        Log.i("CreateActivity", "from view model createActivity");

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        Log.i("CreateActivity", "suss from server");

                        serverResponse.setValue("ok"+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        Log.i("CreateActivity", "code error " + response.code()   +"server error response"  + response.errorBody().string());

                        serverResponse.setValue(response.errorBody().string());
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
        return serverResponse ;


    }

    public LiveData<List<ActivityDto>> getActivities() {
        MutableLiveData<List<ActivityDto>> activities = new MutableLiveData<>();

        Call<List<ActivityDto>> call = apiService.getActivities();
        call.enqueue(new Callback<List<ActivityDto>>() {
            @Override
            public void onResponse(Call<List<ActivityDto>> call, Response<List<ActivityDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    activities.setValue(response.body());
                    Log.i("gettingActivities", "Success: " + response.body().toString());
                    List<ActivityDto> activityDtos = response.body();
                    activities.setValue(activityDtos);


                    Log.i("gettingActivities", "The json: " + new Gson().toJson(activityDtos));

                } else {
                    Log.i("gettingActivities", "Response not successful: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.i("gettingActivities", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("gettingActivities", "Error reading error body", e);
                        }
                    }
                    activities.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<ActivityDto>> call, Throwable t) {
                Log.e("gettingActivities", "Failure: " + t.getMessage());
                activities.setValue(null);
            }

        });
        return activities;

    }

    public LiveData<String> bookActivity(UUID id) {
        MutableLiveData<String> serverResponse = new MutableLiveData<>();
        Call<ResponseBody> call = apiService.bookActivity(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    serverResponse.setValue("ok");
                } else {
                    try {
                        serverResponse.setValue(response.errorBody().string());
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
        return serverResponse;
    }

    public LiveData<List<ActivityDto>> gettAllBookedActivities() {
        MutableLiveData<List<ActivityDto>> activities = new MutableLiveData<>();

        Call<List<ActivityDto>> call = apiService.gettAllBookedActivities();
        call.enqueue(new Callback<List<ActivityDto>>() {
            @Override
            public void onResponse(Call<List<ActivityDto>> call, Response<List<ActivityDto>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    activities.setValue(response.body());
                    Log.i("gettingActivities", "Success: " + response.body().toString());
                    List<ActivityDto> activityDtos = response.body();
                    activities.setValue(activityDtos);


                    Log.i("gettingActivities", "The json: " + new Gson().toJson(activityDtos));

                } else {
                    Log.i("gettingActivities", "Response not successful: " + response.message());
                    if (response.errorBody() != null) {
                        try {
                            Log.i("gettingActivities", "Error body: " + response.errorBody().string());
                        } catch (IOException e) {
                            Log.e("gettingActivities", "Error reading error body", e);
                        }
                    }
                    activities.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<List<ActivityDto>> call, Throwable t) {
                Log.e("gettingActivities", "Failure: " + t.getMessage());
                activities.setValue(null);
            }

        });
        return activities;

    }


    public LiveData<OrgnizerActivitiesDto> getOrganizerActivitiesDetails() {
        MutableLiveData<OrgnizerActivitiesDto> organizerActivities = new MutableLiveData<>();

        Call<OrgnizerActivitiesDto> call = apiService.getOrganizerActivitiesDetails();
        call.enqueue(new Callback<OrgnizerActivitiesDto>() {
            @Override
            public void onResponse(Call<OrgnizerActivitiesDto> call, Response<OrgnizerActivitiesDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    organizerActivities.setValue(response.body());
                    Log.i("gettingActivities", "Success: " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<OrgnizerActivitiesDto> call, Throwable t) {
                Log.e("gettingActivities", "Failure: " + t.getMessage());
                organizerActivities.setValue(null);
            }
        });

        return organizerActivities;
    }


    public LiveData<Object> removeActivity(UUID activityId) {
        MutableLiveData<Object> serverResponse = new MutableLiveData<>();
        Call<ResponseBody> call = apiService.removeActivity(activityId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    serverResponse.setValue("ok");
                } else {
                    try {
                        serverResponse.setValue(response.errorBody().string());
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
        return serverResponse;
    }
}