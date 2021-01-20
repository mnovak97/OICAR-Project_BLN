package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.os.WorkSource;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.Listing;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.Model.WorkCategory;
import com.example.oicar_project.Model.WorkType;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class JobAddActivity extends AppCompatActivity {

    TextView txtTitle;
    TextView txtDescription;
    Spinner workTypes;
    Spinner workCategories;
    Boolean tools = false;
    Button btnAddJob;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_add);
        initializeComponents();

    }

    private void initializeComponents() {
        final JsonPlaceHolderApi service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        final User currentUser = PreferenceUtils.getUser(this);
        workTypes = findViewById(R.id.ddlAddWorkType);
        txtTitle = findViewById(R.id.txtAddTitle);
        txtDescription = findViewById(R.id.txtAddDescription);
        btnAddJob = findViewById(R.id.btnAddJob);

        Call<List<WorkType>> callTypes = service.getAllWorkTypes();
        callTypes.enqueue(new Callback<List<WorkType>>() {
            @Override
            public void onResponse(Call<List<WorkType>> call, Response<List<WorkType>> response) {
                populateSpinnerTypes(response.body(),getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<WorkType>> call, Throwable t) {
                call.cancel();
            }
        });

       /* Call<List<WorkCategory>> callCategories = service.getAllWorkCategories();
        callCategories.enqueue(new Callback<List<WorkCategory>>() {
            @Override
            public void onResponse(Call<List<WorkCategory>> call, Response<List<WorkCategory>> response) {
                populateSpinnerCategories(response.body(),getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<WorkCategory>> call, Throwable t) {

            }
        });
        */
        btnAddJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                addNewJob(txtTitle.getText().toString(),txtDescription.getText().toString(),currentUser,workTypes.getSelectedItem(),workCategories.getSelectedItem(),view.getContext(),service);
            }
        });

    }



    private void addNewJob(String title, String description,User user, Object spinnerItem,Object categoriyitem, Context context,JsonPlaceHolderApi service) {
        WorkType workType = (WorkType) spinnerItem;
        Listing newListing = new Listing(title,description,user.getUserID(),tools,workType);
        WorkCategory workCategory = (WorkCategory) categoriyitem;
        newListing.getWorkCategories().add(workCategory);
        Call<Listing> call = service.addNewListing(newListing);
        call.enqueue(new Callback<Listing>() {
            @Override
            public void onResponse(Call<Listing> call, Response<Listing> response) {
                Toast.makeText(JobAddActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<Listing> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void populateSpinnerCategories(List<WorkCategory> workCategoriesList, Context context) {
        ArrayAdapter<WorkCategory> dataAdapter = new ArrayAdapter<WorkCategory>(context,android.R.layout.simple_spinner_item,workCategoriesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workCategories.setAdapter(dataAdapter);
    }

    private void populateSpinnerTypes(List<WorkType> workTypesList, Context context) {
        ArrayAdapter<WorkType> dataAdapter = new ArrayAdapter<WorkType>(context,android.R.layout.simple_spinner_item,workTypesList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        workTypes.setAdapter(dataAdapter);
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch (view.getId()){
            case R.id.cbTools:
                if (checked)
                    tools = true;
                else
                    tools = false;
                break;
        }
    }
}
