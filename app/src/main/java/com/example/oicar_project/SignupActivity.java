package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.HttpsTrustManager;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.Constants;
import com.example.oicar_project.utils.HashUtil;
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

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final JsonPlaceHolderApi service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        final EditText etFirstName = findViewById(R.id.txtFirstName);
        final EditText etLastName = findViewById(R.id.txtLastName);
        final EditText etMobilePhone = findViewById(R.id.txtMobilePhone);
        final EditText etEmailAddress = findViewById(R.id.txtEmail);
        final EditText etPassword = findViewById(R.id.txtPasswordSignUp);
        final CheckBox cbEmployer = findViewById(R.id.cbIsEmployer);

        Button btnSignUp = findViewById(R.id.btnSignUp);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createNewUser(etFirstName, etLastName, etMobilePhone, etEmailAddress, etPassword, cbEmployer);
                Call<User> call = service.userRegister(user);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == HttpURLConnection.HTTP_OK)
                        {
                            Toast.makeText(SignupActivity.this, "Signup successful", Toast.LENGTH_SHORT).show();
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

    private User createNewUser(EditText etFirstName, EditText etLastName, EditText etMobilePhone, EditText etEmailAddress, EditText etPassword, CheckBox cbEmployer) {

        User user = new User(etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etMobilePhone.getText().toString(),
                etEmailAddress.getText().toString(),
                cbEmployer.isChecked());
        String salt = HashUtil.generateSalt().trim();
        String etPasswordText = etPassword.getText().toString().trim();
        String password = HashUtil.compute_SHA256(etPasswordText, salt);
        user.setPasswordSalt(salt.trim());
        user.setPasswordHash(password.trim());

        return user;
    }
}
