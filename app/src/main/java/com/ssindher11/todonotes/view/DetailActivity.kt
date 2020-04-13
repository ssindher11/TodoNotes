package com.ssindher11.todonotes.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.utils.AppConstant

class DetailActivity : AppCompatActivity() {

    private lateinit var titleTV: TextView
    private lateinit var descTV: TextView

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
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        titleTV.text = title
        descTV.text = description
    }

}