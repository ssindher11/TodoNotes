package com.ssindher11.todonotes.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.utils.AppConstant

class DetailActivity : AppCompatActivity() {

    private lateinit var titleTV: TextView
    private lateinit var descTV: TextView
    private lateinit var imageIV: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        bindViews()
        setIntentData()
    }

    private fun bindViews() {
        titleTV = findViewById(R.id.tv_title_detail)
        descTV = findViewById(R.id.tv_desc_detail)
        imageIV = findViewById(R.id.iv_image_detail)
    }

    private fun setIntentData() {
        val intent = intent
        val title = intent.getStringExtra(AppConstant.TITLE)
        val description = intent.getStringExtra(AppConstant.DESCRIPTION)
        val imagePath = intent.getStringExtra(AppConstant.IMAGE_PATH)

        titleTV.text = title
        descTV.text = description

        if (!imagePath.isNullOrBlank()) {
            Glide.with(this).load(imagePath).into(imageIV)
        }
    }

}