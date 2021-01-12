package com.example.oicar_project.network;


import com.example.oicar_project.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceHolderApi {
    @GET("Users")
    Call<List<User>> getUsers();
}
