package com.example.yallah_project.activity;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

public interface SpringService {

    // Define a method for the API endpoint to fetch the response from Spring Boot backend
    @GET("/") // Specify the relative URL of your API endpoint
    Call<ResponseBody> getSpringResponse();
    // Change the return type and method name as per your API response
}
