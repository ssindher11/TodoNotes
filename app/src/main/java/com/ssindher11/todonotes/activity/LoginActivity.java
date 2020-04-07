package com.ssindher11.todonotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.ssindher11.todonotes.AppConstants;
import com.ssindher11.todonotes.R;

public class LoginActivity extends AppCompatActivity {

    private EditText fullnameET, usernameET;
    private MaterialButton loginBtn;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setupSharedPreferences();
        bindViews();

        loginBtn.setOnClickListener(view -> {

            String fullName = fullnameET.getText().toString();
            String userName = usernameET.getText().toString();

            if (!fullName.trim().isEmpty() && !userName.trim().isEmpty()) {
                Intent intent = new Intent(LoginActivity.this, MyNotesActivity.class);
                intent.putExtra(AppConstants.FULL_NAME, fullName);
                startActivity(intent);
                saveLoginStatus();
                saveFullname(fullName);
            } else {
                Toast.makeText(this, "Full Name and User Name can't be empty", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bindViews() {
        fullnameET = findViewById(R.id.et_fullname);
        usernameET = findViewById(R.id.et_username);
        loginBtn = findViewById(R.id.btn_login);
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    private void saveLoginStatus() {
        editor = sharedPreferences.edit();
        editor.putBoolean(AppConstants.IS_LOGGED_IN, true);
        editor.apply();
    }

    private void saveFullname(String fullname) {
        editor = sharedPreferences.edit();
        editor.putString(AppConstants.FULL_NAME, fullname);
        editor.apply();
    }
}
