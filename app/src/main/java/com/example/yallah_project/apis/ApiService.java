package com.example.yallah_project.apis;


import com.example.yallah_project.dtos.OrganisateurSwitchRequest;
import com.example.yallah_project.model.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/register")
    Call<ResponseBody> registerUser(@Body User request);
    @POST("/login")
    Call<ResponseBody> loginUser(@Body User user);

@POST("/switch-to-organisateur")
Call<ResponseBody> switchToOrganisateur(@Body OrganisateurSwitchRequest request);

}