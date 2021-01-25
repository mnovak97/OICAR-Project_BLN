package com.example.oicar_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.oicar_project.Model.User;
import com.example.oicar_project.utils.PreferenceUtils;

import static com.example.oicar_project.utils.Constants.EMAIL_ADDRESS;
import static com.example.oicar_project.utils.Constants.FIRST_NAME;
import static com.example.oicar_project.utils.Constants.LAST_NAME;
import static com.example.oicar_project.utils.Constants.PASSED_VALUE;
import static com.example.oicar_project.utils.Constants.PASSWORD;
import static com.example.oicar_project.utils.Constants.PHONE_NUMBER;
import static com.example.oicar_project.utils.Constants.UPDATE;

public class EditActivity extends AppCompatActivity {

    ImageButton btnExit;
    TextView tvEditedProperty;
    EditText txtEditedPropertyValue;
    Button btnEdit;
    String passedValue;
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        if (savedInstanceState == null){
            Bundle extras = getIntent().getExtras();
            if (extras == null){
                passedValue = null;
            }else{
                passedValue = extras.getString(PASSED_VALUE);
            }
        }
        initializeComponents();
        setOnClickListeners();
        setData();
    }




    private void initializeComponents() {
        btnExit = findViewById(R.id.btnExitEdit);
        tvEditedProperty = findViewById(R.id.tvEdit);
        txtEditedPropertyValue = findViewById(R.id.txtEdit);
        btnEdit = findViewById(R.id.btnEdit);
        currentUser = PreferenceUtils.getUser(this);
        //making edit text focused
        txtEditedPropertyValue.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(txtEditedPropertyValue, InputMethodManager.SHOW_IMPLICIT);
    }
    private void setOnClickListeners() {
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditActivity.this.finish();
            }
        });

    }
    private void setData() {
        switch (passedValue){
            case FIRST_NAME:
                tvEditedProperty.setText(FIRST_NAME);
                txtEditedPropertyValue.setText(currentUser.getFirstName());
                btnEdit.setText(UPDATE+FIRST_NAME.toLowerCase());
                break;
            case LAST_NAME:
                tvEditedProperty.setText(LAST_NAME);
                txtEditedPropertyValue.setText(currentUser.getLastName());
                btnEdit.setText(UPDATE+LAST_NAME.toLowerCase());
                break;
            case PHONE_NUMBER:
                tvEditedProperty.setText(PHONE_NUMBER);
                txtEditedPropertyValue.setText(currentUser.getMobilePhone());
                btnEdit.setText(UPDATE+PHONE_NUMBER.toLowerCase());
                break;
            case EMAIL_ADDRESS:
                tvEditedProperty.setText(EMAIL_ADDRESS);
                txtEditedPropertyValue.setText(currentUser.geteMail());
                btnEdit.setText(UPDATE+EMAIL_ADDRESS.toLowerCase());
                break;
        }
        //setting cursor at the end of text in edit text
        txtEditedPropertyValue.setSelection(txtEditedPropertyValue.getText().length());
    }
}
