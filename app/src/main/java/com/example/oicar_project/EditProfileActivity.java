package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        ImageButton btnExit = findViewById(R.id.btnExitEditProfile);
        LinearLayout LLFirstName = findViewById(R.id.LLFirstName);


        LLFirstName.setOnClickListener(new View.OnClickListener() {
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
}
