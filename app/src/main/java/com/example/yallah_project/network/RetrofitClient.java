package com.example.yallah_project.network;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String BASE_URL = "http://192.168.1.142:8080";

    public static Retrofit getClient( String token) {
        if (retrofit == null) {
            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(new AuthInterceptor(token))
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
