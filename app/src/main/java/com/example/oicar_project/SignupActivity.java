package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.oicar_project.Database.User;

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        final EditText etFirstName = findViewById(R.id.txtFirstName);
        final EditText etLastName = findViewById(R.id.txtLastName);
        final EditText etMobilePhone = findViewById(R.id.txtMobilePhone);
        final EditText etEmailAddress = findViewById(R.id.txtEmail);
        final EditText etPassword = findViewById(R.id.txtPasswordSignUp);

        Button btnSignUp = findViewById(R.id.btnSignUp);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                User newUser = new User(etFirstName.getText().toString(),etLastName.getText().toString(),etMobilePhone.getText().toString(),etEmailAddress.getText().toString(),etPassword.getText().toString());
                newUser.save();
                Intent intent = new Intent(view.getContext(),LoginActivity.class);
                startActivity(intent);
            }
        });

    }
}
