package com.example.yallah_project.apis;


import com.example.yallah_project.activity.OrganizerDashboard;
import com.example.yallah_project.dtos.ActivityDto;
import com.example.yallah_project.dtos.OrganisateurSwitchRequest;
import com.example.yallah_project.dtos.OrgnizerActivitiesDto;
import com.example.yallah_project.model.Activity;
import com.example.yallah_project.model.Location;
import com.example.yallah_project.model.User;

import java.util.List;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/register")
    Call<ResponseBody> registerUser(@Body User request);
    @POST("/login")
    Call<ResponseBody> loginUser(@Body User user);

@Multipart
@POST("/switch-to-organisateur")
Call<ResponseBody> switchToOrganisateur(@Part("governmentIdType") RequestBody governmentIdTypeStr, @Part MultipartBody.Part file);


    @Multipart
    @POST("/add-Activity")
    Call<ResponseBody> createActivity(
            @Part("activity") RequestBody activity,
            @Part List<MultipartBody.Part> images ,
            @Part("locations") RequestBody locations
    );

    @GET("get-All-Activities")
    Call<List<ActivityDto>> getActivities();

    @POST("/user/Book-Activity/{id}")
    Call<ResponseBody> bookActivity(@Path("id") UUID activityId);

    @GET("/user/Get-BokeedActivties")
    Call<List<ActivityDto>> gettAllBookedActivities() ;


    @GET("orginzer-dashboard")
    Call<OrgnizerActivitiesDto> getOrganizerActivitiesDetails();


    @DELETE("Remove-Activity/{id}")
    Call<ResponseBody> removeActivity(@Path("id") UUID activityId);

    @GET("/activities/filter")
    Call<List<ActivityDto>> getFilteredActivities(@Query("param1") String s, @Query("param2") String s1);

}