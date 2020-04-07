package com.ssindher11.todonotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.ssindher11.todonotes.AppConstants;
import com.ssindher11.todonotes.R;

public class SplashActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setupSharedPreferences();
        checkLoginStatus();
    }

    private void setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
    }

    private void checkLoginStatus() {
        boolean isLoggedIn = sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false);
        if (isLoggedIn) {
            startActivity(new Intent(SplashActivity.this, MyNotesActivity.class));
        } else {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        }
    }
}
