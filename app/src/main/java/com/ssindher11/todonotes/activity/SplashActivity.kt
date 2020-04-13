package com.ssindher11.todonotes.activity

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ssindher11.todonotes.AppConstants
import com.ssindher11.todonotes.R

class SplashActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreferences()
        checkOnLoginStatus()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun checkOnLoginStatus() {
        val isLoggedIn = sharedPreferences.getBoolean(AppConstants.IS_LOGGED_IN, false)
        if (isLoggedIn) {
            startActivity(Intent(this@SplashActivity, MyNotesActivity::class.java))
        } else {
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
        }
    }
}