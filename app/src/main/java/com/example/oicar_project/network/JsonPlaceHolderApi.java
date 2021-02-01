package com.example.oicar_project.network;


import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.LoginModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface JsonPlaceHolderApi {

    @POST("users/login")
    Call<User> userLogin(@Body LoginModel loginModel);

    @POST("users/register")
    Call<User> userRegister(@Body User user);

    @GET("listings/all")
    Call<List<ListingModel>> getAllListings();

    @GET("worktype/all")
    Call<List<WorkType>> getAllWorkTypes();

    @POST("listings/add")
    Call<ListingModel> addNewListing(@Body ListingModel listing);

    @GET("workcategory/all")
    Call<List<WorkCategory>> getAllWorkCategories();

    @GET("listings/{email}/")
    Call<List<ListingModel>> getUserListings(@Path("email") String email);

    @POST("users/update")
    Call<User> updateUser(@Body User user);

    @GET("workcategory/{id}")
    Call<WorkCategory> getWorkCategoryById(@Path("id") int id);

    @GET("worktype/{id}")
    Call<WorkType> getWorkTypeById(@Path("id") int id);

    @GET("users/{id}")
    Call<User> getUserById(@Path("id")int id);

    @POST("offers/add")
    Call<OfferModel> addNewOffer(@Body OfferModel offer);

    @GET("offers/{listingId}")
    Call<List<OfferModel>> getOffersForListingId(@Path("listingId") int lisingId);
}
