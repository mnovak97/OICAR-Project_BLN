package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.activeandroid.util.SQLiteUtils;
import com.example.oicar_project.Model.LoginModel;
import com.example.oicar_project.Model.User;
import com.example.oicar_project.network.HttpsTrustManager;
import com.example.oicar_project.utils.HashUtil;
import com.example.oicar_project.utils.PreferenceUtils;
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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView lblSignUp = findViewById(R.id.lblSignUp);
        Button btnSignIn = findViewById(R.id.btnSignIn);
        if (PreferenceUtils.getEmail(this) != null){
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
        }

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

                login(txtUsername.getText().toString(), txtPassword.getText().toString());

                /*if(checkUsers(txtUsername.getText().toString(),txtPassword.getText().toString())){
                    Intent intent = new Intent(view.getContext(),MainActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }*/

            }
        });
    }
    private void login(String email, String password) {
        //DISABLE UNSAFE CONNECTION ERROR
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //DISABLE INVALID CERTIFICATE ERROR
        HttpsTrustManager.allowAllSSL();

        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
/*
        LoginModel loginModel = new LoginModel(email, password);


        Log.e(ContentValues.TAG, "user:" + loginModel.toString());
        Log.e(ContentValues.TAG, "userJson:" + gson.toJson(loginModel));
*/
        HttpURLConnection client = null;
        Log.e(ContentValues.TAG, "try");

        try {
            //URL url = new URL("https://10.0.2.2:44322/api/users/login");
            URL url = new URL("https://10.0.2.2:44322/api/test/users");
            client = (HttpURLConnection) url.openConnection();

            client.setRequestMethod("GET");
            client.setReadTimeout(10000);
            client.setConnectTimeout(10000);
            //client.setDoOutput(true);
            client.setDoInput(true);
            client.setRequestProperty("Accept", "application/json");
            //client.setRequestProperty("Content-Type", "application/json; charset=utf-8");

            Log.e(ContentValues.TAG, "request");
            //String str = "{\n\"Email\":\"nameless@mail.com\",\n\"Password\":\"PaSswOrD\"\n}";     //gson.toJson(loginModel);
            //Log.e("OUTPUT DATA",  str);

            //OutputStream os = client.getOutputStream();
            //os.write(str.getBytes("UTF-8"));
            //os.close();
            //Log.e(ContentValues.TAG, "getResponseCode");

            Log.e("client.toString()", client.toString());


            client.connect();

            int responseCode = client.getResponseCode();
            Log.e(ContentValues.TAG, "response: " + responseCode);


            String response = "";
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e(ContentValues.TAG, "HTTP_OK");

                String line;
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        client.getInputStream()));
                while ((line = br.readLine()) != null) {
                    response += line;
                }
            } else {
                Log.e(ContentValues.TAG, "NOT HTTP_OK");
                response = "";
            }

            Log.e("RESPONSE", response);

            User userReturned = gson.fromJson(response, User.class);
            Log.e(ContentValues.TAG, userReturned.toString());

            //Toast.makeText(this, userReturned.toString(), Toast.LENGTH_LONG).show();

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
        catch (Exception error) {
            //Handles input and output errors
            Log.e(ContentValues.TAG, "Exception");
            Log.e("EMessage", error.getMessage());
            error.printStackTrace();
        } finally {
            if(client != null)
                client.disconnect();
            Log.e(ContentValues.TAG, "finally");
        }
    }
}
