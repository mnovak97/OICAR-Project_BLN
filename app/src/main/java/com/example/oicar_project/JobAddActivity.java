package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class JobAddActivity extends AppCompatActivity {

    TextView txtTitle;
    TextView txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_add);
        initializeComponents();

    }

    private void initializeComponents() {

    }
}
