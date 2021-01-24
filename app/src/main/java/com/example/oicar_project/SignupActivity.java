package com.example.oicar_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.HashUtil;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    JsonPlaceHolderApi service;
    EditText etFirstName;
    EditText etLastName;
    EditText etMobilePhone;
    EditText etEmailAddress;
    EditText etPassword;
    CheckBox cbEmployer;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        initializeComponents();
        setOnClickListeners();
    }

    private void initializeComponents() {
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        etFirstName = findViewById(R.id.txtFirstName);
        etLastName = findViewById(R.id.txtLastName);
        etMobilePhone = findViewById(R.id.txtMobilePhone);
        etEmailAddress = findViewById(R.id.txtEmail);
        etPassword = findViewById(R.id.txtPasswordSignUp);
        cbEmployer = findViewById(R.id.cbIsEmployer);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void setOnClickListeners() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User user = createNewUser();
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

    private User createNewUser() {

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
