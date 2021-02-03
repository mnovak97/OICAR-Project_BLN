package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.OfferModel;
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

public class OfferActivity extends AppCompatActivity {

    TextView txtPrice;
    CheckBox hasTools;
    Button btnMakeAnOffer;
    int currentUserID;
    int listingID;
    JsonPlaceHolderApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);
        initializeComponents();
        setOnClickListeners();
    }

    private void initializeComponents() {
        txtPrice = findViewById(R.id.txtPrice);
        hasTools = findViewById(R.id.cbHasTools);
        btnMakeAnOffer = findViewById(R.id.btnMakeAnOffer);
        currentUserID = PreferenceUtils.getUserID(this);
        listingID = (int) getIntent().getIntExtra(LISTING_ID, -1);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
    }

    private void setOnClickListeners() {
        btnMakeAnOffer.setOnClickListener(v -> makeAnOffer());
    }

    private void makeAnOffer() {
        OfferModel newOffer = new OfferModel(currentUserID, listingID, Double.parseDouble(txtPrice.getText().toString()), hasTools.isChecked(), false);

        Call<OfferModel> call = service.addNewOffer(newOffer);
        call.enqueue(new Callback<OfferModel>() {
            @Override
            public void onResponse(Call<OfferModel> call, Response<OfferModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                {
                    Toast.makeText(OfferActivity.this, "New offer made", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<OfferModel> call, Throwable t) {
                call.cancel();
            }
        });
    }
}