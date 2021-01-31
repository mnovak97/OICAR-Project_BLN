package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.JsonPlaceHolderApi;
import com.example.oicar_project.network.RetrofitClientInstance;
import com.example.oicar_project.utils.PreferenceUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.oicar_project.utils.Constants.EMAIL_ADDRESS;
import static com.example.oicar_project.utils.Constants.FIRST_NAME;
import static com.example.oicar_project.utils.Constants.LAST_NAME;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;
import static com.example.oicar_project.utils.Constants.PASSWORD;
import static com.example.oicar_project.utils.Constants.PHONE_NUMBER;
import static com.example.oicar_project.utils.Constants.UPDATE;

public class EditActivity extends AppCompatActivity {

    ImageButton btnExit;
    TextView tvEditedProperty;
    EditText txtEditedPropertyValue;
    Button btnEdit;
    String passedValue;
    JsonPlaceHolderApi service;
    int currentUserID;
    User currentUser = new User();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                passedValue = null;
            }else{
                passedValue = extras.getString(PASSED_VALUE);
            }
        }
        getUserData();
        initializeComponents();
        setOnClickListeners();
    }

    private void getUserData() {
        currentUserID = PreferenceUtils.getUserID(this);
        service = RetrofitClientInstance.getRetrofitInstance().create(JsonPlaceHolderApi.class);
        Call<User> userCall = service.getUserById(currentUserID);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                setData(response.body());
                currentUser = response.body();
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void initializeComponents() {
        btnExit = findViewById(R.id.btnExitEdit);
        tvEditedProperty = findViewById(R.id.tvEdit);
        txtEditedPropertyValue = findViewById(R.id.txtEdit);
        btnEdit = findViewById(R.id.btnEdit);
        //making edit text focused
        txtEditedPropertyValue.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtEditedPropertyValue, InputMethodManager.SHOW_IMPLICIT);
    }
    private void setOnClickListeners() {
        btnExit.setOnClickListener(view -> EditActivity.this.finish());
        btnEdit.setOnClickListener(view -> {
          switch (passedValue){
              case FIRST_NAME:
                  currentUser.setFirstName(txtEditedPropertyValue.getText().toString());
                  updateUser(currentUser);
                  break;
              case LAST_NAME:
                 currentUser.setLastName(txtEditedPropertyValue.getText().toString());
                  updateUser(currentUser);
                  break;
              case PHONE_NUMBER:
                 currentUser.setMobilePhone(txtEditedPropertyValue.getText().toString());
                  updateUser(currentUser);
                  break;
              case EMAIL_ADDRESS:
                currentUser.seteMail(txtEditedPropertyValue.getText().toString());
                  updateUser(currentUser);
                  break;
          }
        });
    }

    private void updateUser(User user) {
        Call<User> updateUser = service.updateUser(user);
        updateUser.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                Toast.makeText(EditActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                call.cancel();
            }
        });
    }

    private void setData(User user) {
        switch (passedValue){
            case FIRST_NAME:
                tvEditedProperty.setText(FIRST_NAME);
                txtEditedPropertyValue.setText(user.getFirstName());
                btnEdit.setText(UPDATE+FIRST_NAME.toLowerCase());
                break;
            case LAST_NAME:
                tvEditedProperty.setText(LAST_NAME);
                txtEditedPropertyValue.setText(user.getLastName());
                btnEdit.setText(UPDATE+LAST_NAME.toLowerCase());
                break;
            case PHONE_NUMBER:
                tvEditedProperty.setText(PHONE_NUMBER);
                txtEditedPropertyValue.setText(user.getMobilePhone());
                btnEdit.setText(UPDATE+PHONE_NUMBER.toLowerCase());
                break;
            case EMAIL_ADDRESS:
                tvEditedProperty.setText(EMAIL_ADDRESS);
                txtEditedPropertyValue.setText(user.geteMail());
                btnEdit.setText(UPDATE+EMAIL_ADDRESS.toLowerCase());
                break;
        }
        //setting cursor at the end of text in edit text
        txtEditedPropertyValue.setSelection(txtEditedPropertyValue.getText().length());
    }

}
