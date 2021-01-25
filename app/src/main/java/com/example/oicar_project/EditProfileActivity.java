package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.utils.PreferenceUtils;

import static com.example.oicar_project.utils.Constants.EMAIL_ADDRESS;
import static com.example.oicar_project.utils.Constants.FIRST_NAME;
import static com.example.oicar_project.utils.Constants.LAST_NAME;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;
import static com.example.oicar_project.utils.Constants.PASSWORD;
import static com.example.oicar_project.utils.Constants.PHONE_NUMBER;

public class EditProfileActivity extends AppCompatActivity {

    ImageButton btnExit;
    LinearLayout LLFirstName;
    LinearLayout LLLastName;
    LinearLayout LLMobilePhone;
    LinearLayout LLEmail;
    LinearLayout LLPassword;
    TextView txtName;
    TextView txtLastName;
    TextView txtMobilePhone;
    TextView txtEmail;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initializeData();
        setOnClickListeners();
        setData(currentUser);
    }



    private void initializeData() {
        btnExit = findViewById(R.id.btnExitEditProfile);
        LLFirstName = findViewById(R.id.LLFirstName);
        LLLastName = findViewById(R.id.LLLastName);
        LLMobilePhone = findViewById(R.id.LLPhoneNumber);
        LLEmail = findViewById(R.id.LLEmailAddress);
        LLPassword = findViewById(R.id.LLPassword);
        txtName = findViewById(R.id.txtFirstNameEdit);
        txtLastName = findViewById(R.id.txtLastNameEdit);
        txtEmail = findViewById(R.id.txtEmailAddressEdit);
        txtMobilePhone = findViewById(R.id.txtPhoneNumberEdit);
        currentUser = PreferenceUtils.getUser(this);
    }
    private void setOnClickListeners() {
        LLFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditActivity.class);
                intent.putExtra(PASSED_VALUE,FIRST_NAME);
                startActivity(intent);
            }
        });

        LLLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditActivity.class);
                intent.putExtra(PASSED_VALUE,LAST_NAME);
                startActivity(intent);
            }
        });
        LLMobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditActivity.class);
                intent.putExtra(PASSED_VALUE,PHONE_NUMBER);
                startActivity(intent);
            }
        });

        LLEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditActivity.class);
                intent.putExtra(PASSED_VALUE,EMAIL_ADDRESS);
                startActivity(intent);
            }
        });
        LLPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),EditActivity.class);
                intent.putExtra(PASSED_VALUE,PASSWORD);
                startActivity(intent);
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditProfileActivity.this.finish();
            }
        });
    }

    private void setData(User user) {
        txtName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtMobilePhone.setText(user.getMobilePhone());
        txtEmail.setText(user.geteMail());
    }


}
