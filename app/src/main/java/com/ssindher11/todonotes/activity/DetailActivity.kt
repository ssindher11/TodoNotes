package com.ssindher11.todonotes.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ssindher11.todonotes.AppConstants
import com.ssindher11.todonotes.R

class DetailActivity : AppCompatActivity() {

    lateinit var titleTV: TextView
    lateinit var descTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentData()
    }

    private fun bindViews() {
        titleTV = findViewById(R.id.tv_title_detail)
        descTV = findViewById(R.id.tv_desc_detail)
    }

    private fun setIntentData() {
        val intent = intent
        val title = intent.getStringExtra(AppConstants.TITLE)
        val description = intent.getStringExtra(AppConstants.DESCRIPTION)
        titleTV.text = title
        descTV.text = description
    }

}