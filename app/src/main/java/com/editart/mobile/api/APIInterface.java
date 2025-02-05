package com.editart.mobile.api;

import com.editart.mobile.models.Book;
import com.editart.mobile.models.BookResponse;
import com.editart.mobile.models.LoginRequest;
import com.editart.mobile.models.LoginResponse;
import com.editart.mobile.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface APIInterface {

    final String API_URL = "http://10.0.2.2:8000/";

    @GET("/api/users")
    Call<List<User>> getUsers();

    @POST("/api/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("/api/books")
    Call<BookResponse> getBooks();
}