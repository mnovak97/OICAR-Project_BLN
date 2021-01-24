package com.example.oicar_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.oicar_project.Model.ListingModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BoardAdapter adapter;
    private RecyclerView recyclerView;
    DrawerLayout drawer;
    ImageButton btnMenu;
    ImageButton btnAdd;
    NavigationView navigationView;
    View headerView;
    ImageButton btnProfile;
    TextView userName;
    TextView userLastName;
    JsonPlaceHolderApi service;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeComponents();
        setOnClickListeners();

    }

    private void initializeComponents() {
        currentUser = PreferenceUtils.getUser(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        drawer = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.btnMenu);
        btnAdd = findViewById(R.id.btnAdd);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        btnProfile = headerView.findViewById(R.id.btnProfile);
        userName = headerView.findViewById(R.id.name);
        userLastName = headerView.findViewById(R.id.lastName);
        userName.setText(currentUser.getFirstName());
        userLastName.setText(currentUser.getLastName());

        Call<List<ListingModel>> call = service.getAllListings();
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
        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer.openDrawer(Gravity.LEFT);
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),JobAddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void generateDataList(List<ListingModel> listings, Context context) {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BoardAdapter(listings,context);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menuJobs:{
                Intent intent = new Intent(MainActivity.this, UserJobsActivity.class);
                startActivity(intent);
                break;
            }

            case R.id.menuLogOut: {
                PreferenceUtils.clearPreference(this);
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            }
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
