package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oicar_project.UserModel.TemporaryUserList;
import com.example.oicar_project.UserModel.User;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView lblSignUp = findViewById(R.id.lblSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);

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
                List<User> users = TemporaryUserList.getUsers();
                boolean exists = false;
                EditText txtUsername = findViewById(R.id.txtUsername);
                EditText txtPassword = findViewById(R.id.txtPassword);
                for (User user : users){
                    if(user.geteMail().equals(txtUsername.getText().toString()) && user.getPassword().equals(txtPassword.getText().toString())){
                        exists = true;
                    }
                }
                if (exists){
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
