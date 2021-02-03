package com.example.oicar_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.LISTING_ID;

public class JobOffersActivity extends AppCompatActivity implements OffersAdapter.OnItemClickedListener {
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
        adapter = new OffersAdapter(offers, context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //recyclerView.setOnClickListener
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        if (offers.stream().anyMatch(x -> x.isAccepted())) {
            return;
        }
        OfferModel offer = offers.get(position);

        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    Call<List<OfferModel>> call = service.acceptOffer(offer);
                    call.enqueue(new Callback<List<OfferModel>>() {
                        @Override
                        public void onResponse(Call<List<OfferModel>> call, Response<List<OfferModel>> response) {
                            offers = response.body();
                            generateDataList(getApplicationContext());
                            Toast.makeText(JobOffersActivity.this, "Offer accepted", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<OfferModel>> call, Throwable t) {
                            call.cancel();
                        }
                    });
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    break;
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(String.format("Are you sure you want to accept the offer %d for %.2fHRK?", offer.getListingId(), offer.getPrice()))
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener)
                .show();

    }
}
