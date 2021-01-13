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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        User currentUser = PreferenceUtils.getUser(this);
        ImageButton btnExit = findViewById(R.id.btnExitEditProfile);
        LinearLayout LLFirstName = findViewById(R.id.LLFirstName);
        TextView txtName = findViewById(R.id.txtFirstNameEdit);
        TextView txtLastName = findViewById(R.id.txtLastNameEdit);
        TextView txtMobilePhone = findViewById(R.id.txtPhoneNumberEdit);
        TextView txtEmail = findViewById(R.id.txtEmailAddressEdit);
        setData(txtName,txtLastName,txtMobilePhone,txtEmail,currentUser);

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

    private void setData(TextView txtName, TextView txtLastName, TextView txtMobilePhone, TextView txtEmail,User user) {
        txtName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        txtMobilePhone.setText(user.getMobilePhone());
        txtEmail.setText(user.geteMail());
    }


}
