package com.example.oicar_project;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAddActivity extends AppCompatActivity {

    JsonPlaceHolderApi service;
    int currentUserID;
    TextView txtTitle;
    TextView txtDescription;
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
    }

    private void addNewJob() {
        WorkType workType = (WorkType) workTypes.getSelectedItem();
        WorkCategory workCategory = (WorkCategory) workCategories.getSelectedItem();

        //todo - maps
        double latitude = 45.814556;
        double longitude = 15.944449;

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
}
