package com.example.oicar_project;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.ReviewModel;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.HttpURLConnection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.LISTING_ID;

public class UserJobsActivity extends AppCompatActivity implements UserJobsAdapter.OnItemClickedListener {
    private UserJobsAdapter adapter;
    private RecyclerView recyclerView;
    String currentUserEmail;
    ImageButton btnExitUserJobs;
    JsonPlaceHolderApi service;
    List<ListingModel> userListings;

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
                userListings = response.body();
                generateDataList(getApplicationContext());
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

    private void generateDataList(Context context) {
        recyclerView = findViewById(R.id.recyclerViewUserJobs);
        adapter = new UserJobsAdapter(userListings, context, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClicked(int position) {
        ListingModel userListing = userListings.get(position);
        Intent intent = new Intent(getApplicationContext(), JobOffersActivity.class);
        intent.putExtra(LISTING, userListing);
        startActivity(intent);
    }
}
