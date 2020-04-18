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
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.utils.AppConstant
import com.ssindher11.todonotes.utils.PrefConstant

class LoginActivity : AppCompatActivity() {

    private lateinit var fullnameET: EditText
    private lateinit var usernameET: EditText
    private lateinit var loginBtn: MaterialButton

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

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
                intent.putExtra(AppConstant.FULL_NAME, fullName)
                startActivity(intent)
                saveLoginStatus()
                saveFullname(fullName)
                finishAfterTransition()
            } else {
                Toast.makeText(this, "Full Name and User Name can't be empty", Toast.LENGTH_SHORT).show()
            }
        }

        loginBtn.setOnClickListener(clickAction)
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun saveLoginStatus() {
        editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.IS_LOGGED_IN, true)
        editor.apply()
    }

    private fun saveFullname(fullName: String) {
        editor = sharedPreferences.edit()
        editor.putString(AppConstant.FULL_NAME, fullName)
        editor.apply()
    }

}