package com.ssindher11.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.ssindher11.todonotes.AppConstants
import com.ssindher11.todonotes.R

class LoginActivity : AppCompatActivity() {

    lateinit var fullnameET: EditText
    lateinit var usernameET: EditText
    lateinit var loginBtn: MaterialButton

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        bindViews()
        setupSharedPreferences()
    }

    private fun bindViews() {
        fullnameET = findViewById(R.id.et_fullname)
        usernameET = findViewById(R.id.et_username)
        loginBtn = findViewById(R.id.btn_login)

        val clickAction = View.OnClickListener {
            val fullName = fullnameET.text.toString()
            val userName = usernameET.text.toString()

            if (fullName.isNotBlank() and userName.isNotBlank()) {
                val intent = Intent(this@LoginActivity, MyNotesActivity::class.java)
                intent.putExtra(AppConstants.FULL_NAME, fullName)
                startActivity(intent)
                saveLoginStatus()
                saveFullname(fullName)
            } else {
                Toast.makeText(this, "Full Name and User Name can't be empty", Toast.LENGTH_SHORT).show()
            }
        }

        loginBtn.setOnClickListener(clickAction)
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(AppConstants.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(AppConstants.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun saveFullname(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(AppConstants.FULL_NAME, fullName)
        editor.apply()
    }

}