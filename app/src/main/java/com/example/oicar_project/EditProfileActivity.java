package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.utils.PreferenceUtils;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        User currentUser = PreferenceUtils.getUser(this);
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
    }
    private void setOnClickListeners() {
        LLFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Radi click", Toast.LENGTH_SHORT).show();
            }
        });

        LLLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Radi click", Toast.LENGTH_SHORT).show();
            }
        });
        LLMobilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Radi click", Toast.LENGTH_SHORT).show();
            }
        });

        LLEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Radi click", Toast.LENGTH_SHORT).show();
            }
        });
        LLPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(EditProfileActivity.this, "Radi click", Toast.LENGTH_SHORT).show();
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
