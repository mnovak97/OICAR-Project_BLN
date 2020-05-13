package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.SQLiteUtils;
import com.example.oicar_project.Model.User;

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
                EditText txtUsername = findViewById(R.id.txtUsername);
                EditText txtPassword = findViewById(R.id.txtPassword);


                if(checkUsers(txtUsername.getText().toString(),txtPassword.getText().toString())){
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private boolean checkUsers(String eMail, String password) {

        List<User> users =  SQLiteUtils.rawQuery(User.class,
                "SELECT * from User where eMail = ? and password = ?",
                new String[] { eMail,password });

        for (User user: users) {

            if (user.geteMail().equals(eMail) && user.getPassword().equals(password))
                return true;

        }

        return false;
    }
}
