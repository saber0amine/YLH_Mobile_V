package com.example.yallah_project.apis;


import com.example.yallah_project.dtos.OrganisateurSwitchRequest;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.User;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    @POST("/register")
    Call<ResponseBody> registerUser(@Body User request);
    @POST("/login")
    Call<ResponseBody> loginUser(@Body User user);

@Multipart
@POST("/switch-to-organisateur")
Call<ResponseBody> switchToOrganisateur(@Part("governmentIdType") RequestBody governmentIdTypeStr, @Part MultipartBody.Part file);


    @POST("/add-Activity")
    Call<ResponseBody> createActivity(@Body  Activity activity);
}