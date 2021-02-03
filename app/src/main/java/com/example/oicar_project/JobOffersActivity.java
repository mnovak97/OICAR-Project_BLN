package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.LISTING_ID;

public class JobOffersActivity extends AppCompatActivity {
    private OffersAdapter adapter;
    private RecyclerView recyclerView;
    List<OfferModel> offers;
    JsonPlaceHolderApi service;
    int listingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offers);
        initializeComponents();
    }

    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        listingID = getIntent().getIntExtra(LISTING_ID, -1);
        Call<List<OfferModel>> call = service.getOffersForListingId(listingID);
        call.enqueue(new Callback<List<OfferModel>>() {
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
        recyclerView = findViewById(R.id.recyclerViewOffers);
        adapter = new OffersAdapter(offers, context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.setOnClickListener
        recyclerView.setAdapter(adapter);
    }
}
