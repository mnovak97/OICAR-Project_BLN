package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.FROM_OFFERS;
import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.OFFER;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;

public class UserOffers extends AppCompatActivity implements OffersAdapter.OnItemClickedListener {

    List<OfferModel> offers;
    private OffersAdapter adapter;
    private RecyclerView recyclerView;
    JsonPlaceHolderApi service;
    int currentUserId;
    ImageButton btnExitUserOffers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_offers);
        initializeComponents();
    }

    private void initializeComponents() {
        btnExitUserOffers = UserOffers.this.findViewById(R.id.btnExitUserOffers);
        btnExitUserOffers.setOnClickListener(view -> this.finish());

        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        currentUserId = PreferenceUtils.getUserID(this);
        Call<List<OfferModel>> offersCall = service.getOffersForUserId(currentUserId);
        offersCall.enqueue(new Callback<List<OfferModel>>() {
            @Override
            public void onResponse(Call<List<OfferModel>> call, Response<List<OfferModel>> response) {
                offers = response.body();
                generateDataList(getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<OfferModel>> call, Throwable t) {
                call.cancel();
            }
        });

    }

    private void generateDataList(Context context) {
        recyclerView = findViewById(R.id.recyclerViewUserOffers);
        adapter = new OffersAdapter(offers, context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        OfferModel offer = offers.get(position);
        Call<ListingModel> listingModelCall = service.getListingById(offer.getListingId());
        listingModelCall.enqueue(new Callback<ListingModel>() {
            @Override
            public void onResponse(Call<ListingModel> call, Response<ListingModel> response) {
                ListingModel listing = response.body();
                showListingDetails(listing, offer);
            }

            @Override
            public void onFailure(Call<ListingModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void showListingDetails(ListingModel listing, OfferModel offer) {
        Intent intent = new Intent(getApplicationContext(), DetailsActivity.class);
        intent.putExtra(PASSED_VALUE, FROM_OFFERS);
        intent.putExtra(LISTING, listing);
        intent.putExtra(OFFER, offer);
        startActivity(intent);
    }
}
