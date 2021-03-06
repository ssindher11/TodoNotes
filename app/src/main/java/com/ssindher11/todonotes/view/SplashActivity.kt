package com.ssindher11.todonotes.view

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.onboarding.OnBoardingActivity
import com.ssindher11.todonotes.utils.PrefConstant

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setupSharedPreferences()
        checkOnLoginStatus()
        getFCMToken()
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    private fun checkOnLoginStatus() {
        val LAUNCH_DELAY = 750L
        val isLoggedIn = sharedPreferences.getBoolean(PrefConstant.IS_LOGGED_IN, false)
        val isOnBoarded = sharedPreferences.getBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, false)
        if (isLoggedIn) {
            Handler().postDelayed({
                startActivity(Intent(this@SplashActivity, MyNotesActivity::class.java))
                finishAfterTransition()
            }, LAUNCH_DELAY)
        } else {
            if (isOnBoarded) {
                Handler().postDelayed({
                    startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
                    finishAfterTransition()
                }, LAUNCH_DELAY)
            } else {
                Handler().postDelayed({
                    startActivity(Intent(this@SplashActivity, OnBoardingActivity::class.java))
                    finishAfterTransition()
                }, LAUNCH_DELAY)
            }
        }
    }

    private fun getFCMToken() {
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    val token = task.result?.token

                    // Log and toast
                    Log.d(TAG, token)
//                    Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
                })

    }

}