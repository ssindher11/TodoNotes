package com.ssindher11.todonotes.onboarding

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.utils.PrefConstant
import com.ssindher11.todonotes.view.LoginActivity

class OnBoardingActivity : AppCompatActivity(), OnBoardingOneFragment.OnNextClick,
        OnBoardingTwoFragment.OnButtonClick,
        OnBoardingThreeFragment.OnButtonClick,
        OnBoardingFourFragment.OnButtonClick {

    private lateinit var viewPager: ViewPager

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        bindViews()
        setupSharedPreferences()
    }

    private fun bindViews() {
        viewPager = findViewById(R.id.viewPager)
        val adapter = FragmentAdapter(supportFragmentManager)
        viewPager.adapter = adapter
    }

    private fun setupSharedPreferences() {
        sharedPreferences = getSharedPreferences(PrefConstant.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)
    }

    override fun onOneNext() {
        viewPager.currentItem = 1
    }

    override fun onTwoBack() {
        viewPager.currentItem = 0
    }

    override fun onTwoNext() {
        viewPager.currentItem = 2
    }

    override fun onThreeBack() {
        viewPager.currentItem = 1
    }

    override fun onThreeNext() {
        viewPager.currentItem = 3
    }

    override fun onFourBack() {
        viewPager.currentItem = 2
    }

    override fun onDone() {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putBoolean(PrefConstant.ON_BOARDED_SUCCESSFULLY, true)
        editor.apply()

        startActivity(Intent(this@OnBoardingActivity, LoginActivity::class.java))
        finishAfterTransition()
    }
}
