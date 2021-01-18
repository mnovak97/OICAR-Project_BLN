package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.SQLiteUtils;
import com.example.oicar_project.Model.LoginModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.HttpsTrustManager;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.Constants;
import com.example.oicar_project.utils.HashUtil;
import com.example.oicar_project.utils.PreferenceUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final JsonPlaceHolderApi service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        TextView lblSignUp = findViewById(R.id.lblSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        if (PreferenceUtils.getUser(this) != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

        lblSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),SignupActivity.class);
                startActivity(intent);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText txtUsername = findViewById(R.id.txtUsername);
                EditText txtPassword = findViewById(R.id.txtPassword);
                LoginModel loginModel = new LoginModel(txtUsername.getText().toString(),txtPassword.getText().toString());
                Call<User> call = service.userLogin(loginModel);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user = response.body();
                        if(user != null){
                            PreferenceUtils.saveUser(user,getApplicationContext());
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

            }
        });
    }
}
