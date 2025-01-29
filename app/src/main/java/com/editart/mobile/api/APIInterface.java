package com.editart.mobile.api;

import com.editart.mobile.models.LoginRequest;
import com.editart.mobile.models.LoginResponse;
import com.editart.mobile.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface APIInterface {
    @GET("/api/users")
    Call<List<User>> getUsers();

    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);
}