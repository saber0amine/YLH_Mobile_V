package com.example.yallah_project.network;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {
    private String authToken;

    public AuthInterceptor(String token) {
        this.authToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        // Don't add Authorization header for login and registration endpoints
        if (original.url().encodedPath().equals("/login") || original.url().encodedPath().equals("/register")) {
            return chain.proceed(original);
        }
        Log.i("userView" , "from the Interceptor " + authToken) ;

        Request.Builder builder = original.newBuilder()
                .header("Authorization", "Bearer " + authToken);

        Request request = builder.build();
        return chain.proceed(request);
    }
}