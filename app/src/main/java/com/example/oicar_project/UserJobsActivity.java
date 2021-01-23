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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserJobsActivity extends AppCompatActivity {
    private BoardAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_jobs);
        User currentUser = PreferenceUtils.getUser(this);
        ImageButton btnExitUserJobs = findViewById(R.id.btnExitUserJobs);

        JsonPlaceHolderApi service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Call<List<ListingModel>> call = service.getUserListings(currentUser.geteMail());
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

        btnExitUserJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserJobsActivity.this.finish();
            }
        });



    }

    private void generateDataList(List<ListingModel> userListings, Context context) {
        recyclerView = findViewById(R.id.recyclerViewUserJobs);
        adapter = new BoardAdapter(userListings,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
}
