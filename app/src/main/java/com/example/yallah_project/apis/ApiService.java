package com.example.yallah_project.apis;


import com.example.yallah_project.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<ResponseBody> registerUser(@Body User request);

}