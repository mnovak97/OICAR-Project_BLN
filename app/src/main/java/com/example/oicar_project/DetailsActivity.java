package com.example.oicar_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.LISTING_ID;

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
    boolean isEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeComponents();
        setOnClickListeners();
        getWorkTypeTitle();
        getWorkCategoryTitle();
        setData();
        checkIfUserIsEmployer();
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

    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        isEmployer = PreferenceUtils.getIsEmployer(this);
        listing = (ListingModel) getIntent().getSerializableExtra(LISTING);
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
        btnOffer.setOnClickListener(v -> makeAnOffer(v));
    }

    private void makeAnOffer(View view) {
        Intent intent = new Intent(view.getContext(), OfferActivity.class);
        intent.putExtra(LISTING_ID, listing.getIdListing());
        startActivity(intent);
    }

    private void setData() {

        //template
        txtTitle.setText(listing.getTitle());
        txtDescription.setText(listing.getDescription());
        txtLocation.setText("location");
        if (!listing.isToolsRequired()){
            txtTools.setText("Tools are not required");
        }
        else{
            txtTools.setText("Tools are required");
        }
    }
    private void checkIfUserIsEmployer() {
        if (isEmployer){
            btnOffer.setVisibility(View.INVISIBLE);
        }else btnOffer.setVisibility(View.VISIBLE);
    }
}
