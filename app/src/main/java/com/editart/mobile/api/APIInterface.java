package com.editart.mobile.api;

import com.editart.mobile.models.BookResponse;
import com.editart.mobile.models.Cart;
import com.editart.mobile.models.CartRequest;
import com.editart.mobile.models.LoginRequest;
import com.editart.mobile.models.UserResponse;
import com.editart.mobile.models.RegisterRequest;
import com.editart.mobile.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface APIInterface {

    @GET("/api/users")
    Call<List<User>> getUsers();

    @POST("/api/login")
    Call<UserResponse> login(@Body LoginRequest loginRequest);

    @POST("/api/register")
    Call<UserResponse> register(@Body RegisterRequest registerRequest);

    @GET("/api/books")
    Call<BookResponse> getBooks();

    @GET("/api/wishlist/{user_id}")
    Call<BookResponse> getWishlist(@Path("user_id") int userId);

    @GET("/api/cart/{user_id}")
    Call<Cart> getCart(@Path("user_id") int userId);

    @POST("/api/cart/{user_id}/add")
    Call<Void> addToCart(@Path("user_id") int userId, @Body CartRequest cartRequest);

    @POST("/api/cart/{user_id}/remove")
    Call<Void> removeFromCart(@Path("user_id") int userId, @Body CartRequest cartRequest);
}