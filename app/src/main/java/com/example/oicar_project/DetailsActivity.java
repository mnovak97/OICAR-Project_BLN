package com.example.oicar_project;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
import com.example.oicar_project.Model.ReviewModel;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;


import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.FROM_LISTINGS;
import static com.example.oicar_project.utils.Constants.FROM_OFFERS;
import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.LISTING_ID;
import static com.example.oicar_project.utils.Constants.OFFER;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;

public class DetailsActivity extends AppCompatActivity {

    TextView txtTitle;
    TextView txtDescription;
    TextView txtLocation;
    TextView txtWorkType;
    TextView txtCategory;
    TextView txtTools;
    ImageButton btnExitDetails;
    Button btnOffer;
    JsonPlaceHolderApi service;
    ListingModel listing;
    String passedValue;
    boolean isEmployer;
    OfferModel offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        initializeComponents();
        getIntentExtra();
        setOnClickListeners();
        getWorkTypeTitle();
        getWorkCategoryTitle();
        setData();
        checkIfUserIsEmployer();
    }

    private void getIntentExtra() {
        Bundle extras = getIntent().getExtras();
        passedValue = extras.getString(PASSED_VALUE);
        switch (passedValue) {
            case FROM_LISTINGS:
                listing = (ListingModel) extras.getSerializable(LISTING);
                btnOffer.setOnClickListener(v -> makeAnOffer(v));
                break;
            case FROM_OFFERS:
                listing = (ListingModel) extras.getSerializable(LISTING);
                offer = (OfferModel) extras.getSerializable(OFFER);

                if (listing.isEmployeeReviewed()) {
                    btnOffer.setText("Job completed");
                    btnOffer.setEnabled(false);
                } else {
                    if (offer.isAccepted() && !listing.isListed()) {
                        btnOffer.setOnClickListener(v -> markAsCompleted(v));
                        btnOffer.setText("Mark as completed");
                    } else {
                        btnOffer.setEnabled(false);
                        btnOffer.setText("Offer made");
                    }
                }
        }
    }

    private void initializeComponents() {

        isEmployer = PreferenceUtils.getIsEmployer(this);
        txtTitle = findViewById(R.id.txtTitleDetails);
        txtDescription = findViewById(R.id.txtDescriptionDetails);
        txtLocation = findViewById(R.id.txtLocationDetails);
        txtWorkType = findViewById(R.id.txtWorkTypeDetails);
        txtCategory = findViewById(R.id.txtWorkCategoryDetails);
        txtTools = findViewById(R.id.txtToolsDetails);
        btnExitDetails = findViewById(R.id.btnExitDetails);
        btnOffer = findViewById(R.id.btnOffer);

    }

    private void setOnClickListeners() {
        btnExitDetails.setOnClickListener(view -> DetailsActivity.this.finish());
    }

    private void markAsCompleted(View view) {
        Dialog rankDialog = new Dialog(this);
        rankDialog.setContentView(R.layout.rank_layout);
        rankDialog.setCancelable(true);
        RatingBar ratingBar = (RatingBar) rankDialog.findViewById(R.id.ratingBar);
        TextView txtNotes = (TextView) rankDialog.findViewById(R.id.txtNotes);

        Button btnRank = (Button) rankDialog.findViewById(R.id.btnRank);
        btnRank.setOnClickListener(v -> {
            ReviewModel reviewModel = new ReviewModel();
            reviewModel.setGrade((int) ratingBar.getRating() * 20);
            reviewModel.setComment(txtNotes.getText().toString());
            reviewModel.setUserReviewedId(listing.getEmployerId());
            reviewModel.setUserReviewerId(offer.getEmployeeId());


            Call<ReviewModel> call = service.addNewReview(reviewModel);
            call.enqueue(new Callback<ReviewModel>() {
                @Override
                public void onResponse(Call<ReviewModel> call, Response<ReviewModel> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(DetailsActivity.this, "Employer successfully rated", Toast.LENGTH_SHORT).show();
                        rankDialog.dismiss();
                        DetailsActivity.this.finish();
                    }
                }

                @Override
                public void onFailure(Call<ReviewModel> call, Throwable t) {
                    call.cancel();
                    Toast.makeText(DetailsActivity.this, "Error rating employer", Toast.LENGTH_SHORT).show();
                }
            });

            rankDialog.dismiss();
        });
        rankDialog.show();
    }

    private void makeAnOffer(View view) {
        Intent intent = new Intent(view.getContext(), OfferActivity.class);
        intent.putExtra(LISTING_ID, listing.getIdListing());
        startActivity(intent);
    }

    private void getWorkTypeTitle() {
        Call<WorkType> workTypeCall = service.getWorkTypeById(listing.getWorkTypeId());
        workTypeCall.enqueue(new Callback<WorkType>() {
            @Override
            public void onResponse(Call<WorkType> call, Response<WorkType> response) {
                txtWorkType.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<WorkType> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void getWorkCategoryTitle() {
        Call<WorkCategory> workCategoryCall = service.getWorkCategoryById(listing.getWorkCategoryId());
        workCategoryCall.enqueue(new Callback<WorkCategory>() {
            @Override
            public void onResponse(Call<WorkCategory> call, Response<WorkCategory> response) {
                txtCategory.setText(response.body().getTitle());
            }

            @Override
            public void onFailure(Call<WorkCategory> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setData() {

        //template
        txtTitle.setText(listing.getTitle());
        txtDescription.setText(listing.getDescription());
        txtLocation.setText("location");
        if (!listing.isToolsRequired()) {
            txtTools.setText("Tools are not required");
        } else {
            txtTools.setText("Tools are required");
        }
    }

    private void checkIfUserIsEmployer() {
        if (isEmployer) {
            btnOffer.setVisibility(View.INVISIBLE);
        } else btnOffer.setVisibility(View.VISIBLE);
    }
}
