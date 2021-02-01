package com.example.oicar_project;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

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

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.FIRST_NAME;
import static com.example.oicar_project.utils.Constants.LISTING;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, BoardAdapter.OnItemClickedListener {

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
    int currentUserID;
    List<ListingModel> listings;
    boolean isEmployer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        initializeComponents();
        setOnClickListeners();
        checkIfUserIsEmployer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        initializeComponents();
        setOnClickListeners();
        checkIfUserIsEmployer();
    }

    private void initializeComponents() {
        currentUserID = PreferenceUtils.getUserID(this);
        isEmployer = PreferenceUtils.getIsEmployer(this);
        drawer = findViewById(R.id.drawer_layout);
        btnMenu = findViewById(R.id.btnMenu);
        btnAdd = findViewById(R.id.btnAdd);
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        btnProfile = headerView.findViewById(R.id.btnProfile);
        userName = headerView.findViewById(R.id.name);
        userLastName = headerView.findViewById(R.id.lastName);


        Call<List<ListingModel>> call = service.getAllListings();
        call.enqueue(new Callback<List<ListingModel>>() {
            @Override
            public void onResponse(Call<List<ListingModel>> call, Response<List<ListingModel>> response) {
                listings = response.body();
                generateDataList(getApplicationContext());
            }

            @Override
            public void onFailure(Call<List<ListingModel>> call, Throwable t) {
                call.cancel();
            }
        });

        Call<User> userCall = service.getUserById(currentUserID);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                setData(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });

    }


    private void setOnClickListeners() {
        btnProfile.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),EditProfileActivity.class);
            startActivity(intent);
        });
        btnMenu.setOnClickListener(view -> drawer.openDrawer(Gravity.LEFT));
        btnAdd.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),JobAddActivity.class);
            startActivity(intent);
        });
    }
    private void checkIfUserIsEmployer() {
       if (isEmployer){
          btnAdd.setVisibility(View.VISIBLE);
        }else btnAdd.setVisibility(View.INVISIBLE);
    }

    private void generateDataList (Context context) {
        recyclerView = findViewById(R.id.recyclerView);
        adapter = new BoardAdapter(listings,context,this);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(adapter);
    }
    private void setData(User user) {
        userName.setText(user.getFirstName());
        userLastName.setText(user.getLastName());
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

    @Override
    public void onItemClick(int position) {
        ListingModel listing = listings.get(position);
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        intent.putExtra(LISTING,listing);
        startActivity(intent);
    }
}
