package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oicar_project.Model.User;

import java.util.List;

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

        /*List<User> storedUsers = User.getAllUsers();
        Log.d("EA","All done");
        */


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createNewUser(etFirstName,etLastName,etMobilePhone,etEmailAddress,etPassword,view);
            }
        });

    }

    private void createNewUser(EditText etFirstName, EditText etLastName, EditText etMobilePhone, EditText etEmailAddress, EditText etPassword,View view) {
        if (checkIfUserAlreadyExists(etEmailAddress)){
            User newUser = new User(etFirstName.getText().toString(),etLastName.getText().toString(),etMobilePhone.getText().toString(),etEmailAddress.getText().toString(),etPassword.getText().toString());
            newUser.save();
            Intent intent = new Intent(view.getContext(),LoginActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Already an account with that E-Mail address", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean checkIfUserAlreadyExists(EditText etEmailAddress) {
        List<User> allUsers = User.getAllUsers();
        for (User user : allUsers) {
            if (user.geteMail().equals(etEmailAddress)){
                return true;
            }
        }
        return false;
    }
}
