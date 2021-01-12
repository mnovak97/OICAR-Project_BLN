package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.HttpsTrustManager;
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
                User user = createNewUser(etFirstName, etLastName, etMobilePhone, etEmailAddress, etPassword, view);

                Log.e("user returned", user.toString());
            }
        });
    }

    private User createNewUser(EditText etFirstName, EditText etLastName, EditText etMobilePhone, EditText etEmailAddress, EditText etPassword, View view) {
        User userReturned = null;
        HttpURLConnection client = null;

        //DISABLE UNSAFE CONNECTION ERROR
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //DISABLE INVALID CERTIFICATE ERROR
        HttpsTrustManager.allowAllSSL();

        //create user data json
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        User user = new User(etFirstName.getText().toString(),
                etLastName.getText().toString(),
                etMobilePhone.getText().toString(),
                etEmailAddress.getText().toString());
        String salt = HashUtil.generateSalt().trim();
        String etPasswordText = etPassword.getText().toString().trim();
        String password = HashUtil.compute_SHA256(etPasswordText, salt);
        user.setPasswordSalt(salt.trim());
        user.setPasswordHash(password.trim());
        String userData = gson.toJson(user);

        try {
            //setup connection
            URL url = new URL(Constants.ServerURL + "/api/users/register");
            client = (HttpURLConnection) url.openConnection();
            client.setRequestMethod("POST");
            client.setReadTimeout(15000);
            client.setConnectTimeout(15000);
            client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Accept", "application/json");
            client.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            //send user data
            byte[] outputBytes = userData.getBytes("UTF-8");
            OutputStream os = client.getOutputStream();
            os.write(outputBytes);

            //read server response
            int responseCode = client.getResponseCode();
            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e(ContentValues.TAG, "HTTP_OK");

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
                Log.e("RESPONSE", response);
            } else {
                Log.e(ContentValues.TAG, "NOT HTTP_OK");
            }

            userReturned = gson.fromJson(response, User.class);
        } catch(MalformedURLException error) {
            //Handles an incorrectly entered URL
            Log.e(ContentValues.TAG, "MalformedURLException");
            Log.e("EMessage", error.getMessage());
            error.printStackTrace();
        }
        catch(SocketTimeoutException error) {
            //Handles URL access timeout.
            Log.e(ContentValues.TAG, "SocketTimeoutException");
            Log.e("EMessage", error.getMessage());
            error.printStackTrace();
        }
        catch (IOException error) {
            //Handles input and output errors
            Log.e(ContentValues.TAG, "IOException");
            Log.e("EMessage", error.getMessage());
            error.printStackTrace();
        } finally {
            if(client != null)
                client.disconnect();
            Log.e(ContentValues.TAG, "finally");
        }

        return userReturned;

        /*if (checkIfUserAlreadyExists(etEmailAddress)){
            //User newUser = new User(etFirstName.getText().toString(),etLastName.getText().toString(),etMobilePhone.getText().toString(),etEmailAddress.getText().toString(),etPassword.getText().toString());
            Intent intent = new Intent(view.getContext(),LoginActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "Already an account with that E-Mail address", Toast.LENGTH_SHORT).show();
        }
*/
    }
}
