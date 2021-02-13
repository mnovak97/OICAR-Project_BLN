package com.example.oicar_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.Model.ReviewModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;

import java.net.HttpURLConnection;
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
    ListingModel listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_offers);
        initializeComponents();
    }

    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        listing = (ListingModel) getIntent().getSerializableExtra(LISTING);
        Call<List<OfferModel>> call = service.getOffersForListingId(listing.getIdListing());
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
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        OfferModel offer = offers.get(position);
        if (offers.stream().anyMatch(x -> x.isAccepted())) {
            if (offer.isAccepted() && !listing.isEmployerReviewed()) {
                Dialog rankDialog = new Dialog(this);
                rankDialog.setContentView(R.layout.rank_layout);
                rankDialog.setCancelable(true);
                RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.ratingBar);
                TextView txtNotes = (TextView) rankDialog.findViewById(R.id.txtNotes);
                TextView lblTitle = (TextView) rankDialog.findViewById(R.id.lblTitle);
                lblTitle.setText("Rate employee");

                Button btnRank = (Button) rankDialog.findViewById(R.id.btnRank);
                btnRank.setOnClickListener(v -> {
                    ReviewModel reviewModel = new ReviewModel();
                    reviewModel.setGrade((int) ratingBar.getRating() * 20);
                    reviewModel.setComment(txtNotes.getText().toString());
                    reviewModel.setUserReviewedId(offer.getEmployeeId());
                    reviewModel.setUserReviewerId(listing.getEmployerId());


                    Call<ReviewModel> call = service.addNewReview(reviewModel);
                    call.enqueue(new Callback<ReviewModel>() {
                        @Override
                        public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                            if (response.code() == HttpURLConnection.HTTP_OK) {
                                Toast.makeText(JobOffersActivity.this, "Employee successfully rated", Toast.LENGTH_SHORT).show();
                                rankDialog.dismiss();
                                JobOffersActivity.this.finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<ReviewModel> call, Throwable t) {
                            call.cancel();
                            Toast.makeText(JobOffersActivity.this, "Error rating employee", Toast.LENGTH_SHORT).show();
                        }
                    });
                    rankDialog.dismiss();
                });
                rankDialog.show();
            }
            return;
        }

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
