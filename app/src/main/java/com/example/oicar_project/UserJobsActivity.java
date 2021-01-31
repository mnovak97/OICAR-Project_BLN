package com.example.oicar_project;

import android.content.Context;
import android.os.Bundle;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobsActivity extends AppCompatActivity implements BoardAdapter.OnItemClickedListener {
    private BoardAdapter adapter;
    private RecyclerView recyclerView;
    String currentUserEmail;
    ImageButton btnExitUserJobs;
    JsonPlaceHolderApi service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jobs);
        initializeComponents();
        setOnClickListeners();

    }

    private void initializeComponents() {
        currentUserEmail = PreferenceUtils.getUserEmail(this);
        btnExitUserJobs = findViewById(R.id.btnExitUserJobs);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);

        Call<List<ListingModel>> call = service.getUserListings(currentUserEmail);
        call.enqueue(new Callback<List<ListingModel>>() {
            @Override
            public void onResponse(Call<List<ListingModel>> call, Response<List<ListingModel>> response) {
                generateDataList(response.body(),getApplicationContext());
            }
            @Override
            public void onFailure(Call<List<ListingModel>> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setOnClickListeners() {
        btnExitUserJobs.setOnClickListener(view -> UserJobsActivity.this.finish());
    }

    private void generateDataList(List<ListingModel> userListings, Context context) {
        recyclerView = findViewById(R.id.recyclerViewUserJobs);
        adapter = new BoardAdapter(userListings,context,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int position) {
        Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
    }
}
