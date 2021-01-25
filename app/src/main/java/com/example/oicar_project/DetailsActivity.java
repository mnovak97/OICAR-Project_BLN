package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.LISTING;
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
    User currentUser;
    ListingModel listing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        initializeComponents();
        setOnClickListeners();
        setData();
    }



    private void initializeComponents() {
        listing = (ListingModel) getIntent().getSerializableExtra(LISTING);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        currentUser = PreferenceUtils.getUser(this);
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
    private void setData() {

        //template
        txtTitle.setText(listing.getTitle());
        txtDescription.setText(listing.getDescription());
        txtLocation.setText("location");
        txtWorkType.setText("type");
        txtCategory.setText("category");
        if (!listing.isToolsRequired()){
            txtTools.setText("Tools are not required");
        }
        else{
            txtTools.setText("Tools are required");
        }
    }
}
