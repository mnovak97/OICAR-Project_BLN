package com.example.oicar_project.network;


import com.example.oicar_project.Model.Listing;
import com.example.oicar_project.Model.LoginModel;
import com.example.oicar_project.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JsonPlaceHolderApi {
    @POST("users/login")
    Call<User> userLogin(@Body LoginModel loginModel);

    @POST("users/register")
    Call<User> userRegister(@Body User user);

    @GET("listings/all")
    Call<List<Listing>> getAllListings();



}
