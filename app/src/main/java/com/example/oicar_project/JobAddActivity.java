package com.example.oicar_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import java.net.HttpURLConnection;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAddActivity extends AppCompatActivity {

    private static final int AUTOCOMPLETE_REQUEST_CODE = 1;
    JsonPlaceHolderApi service;
    int currentUserID;
    TextView txtTitle;
    TextView txtDescription;
    TextView txtAddLocation;
    Spinner workTypes;
    Spinner workCategories;
    Boolean toolsRequired = true;
    Button btnAddJob;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_add);
        initializeComponents();
        setOnClickListeners();
    }

    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        currentUserID = PreferenceUtils.getUserID(this);
        workTypes = findViewById(R.id.ddlAddWorkType);
        workCategories = findViewById(R.id.ddlAddCategory);
        txtTitle = findViewById(R.id.txtAddTitle);
        txtDescription = findViewById(R.id.txtAddDescription);
        btnAddJob = findViewById(R.id.btnAddJob);
        txtAddLocation = findViewById(R.id.txtAddLocation);

        Call<List<WorkType>> callTypes = service.getAllWorkTypes();
        callTypes.enqueue(new Callback<List<WorkType>>() {
            @Override
            public void onResponse(Call<List<WorkType>> call, Response<List<WorkType>> response) {
                populateSpinnerTypes(response.body(), getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<WorkType>> call, Throwable t) {
                call.cancel();
            }
        });

        Call<List<WorkCategory>> callCategories = service.getAllWorkCategories();
        callCategories.enqueue(new Callback<List<WorkCategory>>() {
            @Override
            public void onResponse(Call<List<WorkCategory>> call, Response<List<WorkCategory>> response) {
                populateSpinnerCategories(response.body(), getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<WorkCategory>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setOnClickListeners() {

        btnAddJob.setOnClickListener(view -> addNewJob());
        txtAddLocation.setOnClickListener(view -> {
            if (!Places.isInitialized()) {
                Places.initialize(getApplicationContext(),getResources().getString(R.string.map_key));
            }
            List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME);
            Intent intent = new Autocomplete.IntentBuilder(
                    AutocompleteActivityMode.FULLSCREEN, fields)
                    .build(this);
            startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
        });

    }

    private void addNewJob() {
        WorkType workType = (WorkType) workTypes.getSelectedItem();
        WorkCategory workCategory = (WorkCategory) workCategories.getSelectedItem();

        //todo - maps
        double latitude = 45.844515;
        double longitude = 16.009059;

        ListingModel newListing = new ListingModel(txtTitle.getText().toString(), txtDescription.getText().toString(), latitude, longitude, currentUserID, toolsRequired, workType.getIdWorkType(), workCategory.getIdWorkCategory(), true);

        Call<ListingModel> call = service.addNewListing(newListing);
        call.enqueue(new Callback<ListingModel>() {
            @Override
            public void onResponse(Call<ListingModel> call, Response<ListingModel> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Toast.makeText(JobAddActivity.this, "New listing added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ListingModel> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void populateSpinnerCategories(List<WorkCategory> workCategoriesList, Context context) {
        ArrayAdapter<WorkCategory> dataAdapter = new ArrayAdapter<WorkCategory>(context, android.R.layout.simple_spinner_item, workCategoriesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workCategories.setAdapter(dataAdapter);
    }

    private void populateSpinnerTypes(List<WorkType> workTypesList, Context context) {
        ArrayAdapter<WorkType> dataAdapter = new ArrayAdapter<WorkType>(context, android.R.layout.simple_spinner_item, workTypesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypes.setAdapter(dataAdapter);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch (view.getId()) {
            case R.id.cbTools:
                toolsRequired = !checked;
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                Toast.makeText(JobAddActivity.this, place.getName(), Toast.LENGTH_SHORT).show();
            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Log.i("tagcina", status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
