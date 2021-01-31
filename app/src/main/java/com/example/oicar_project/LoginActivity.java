package com.example.oicar_project;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oicar_project.Model.LoginModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    TextView lblSignUp;
    Button btnSignIn;
    JsonPlaceHolderApi service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeComponents();
        setOnClickListeners();

        if (PreferenceUtils.getUserID(this) != 0){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }
    }



    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        lblSignUp = findViewById(R.id.lblSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);
    }
    private void setOnClickListeners() {
        lblSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),SignupActivity.class);
            startActivity(intent);
        });

        btnSignIn.setOnClickListener(view -> {
            EditText txtUsername = findViewById(R.id.txtUsername);
            EditText txtPassword = findViewById(R.id.txtPassword);
            LoginModel loginModel = new LoginModel(txtUsername.getText().toString(),txtPassword.getText().toString());
            Call<User> call = service.userLogin(loginModel);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, Response<User> response) {
                    User user = response.body();
                    if(user != null){
                        PreferenceUtils.saveUserID(user.getUserID(),getApplicationContext());
                        PreferenceUtils.saveUserEmail(user.geteMail(),getApplicationContext());
                        PreferenceUtils.saveUserEmployer(user.isEmployer(),getApplicationContext());
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    call.cancel();
                }
            });

        });
    }
}
