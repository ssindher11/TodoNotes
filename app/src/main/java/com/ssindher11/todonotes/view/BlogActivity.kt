package com.ssindher11.todonotes.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.ssindher11.todonotes.R
import com.ssindher11.todonotes.adapter.BlogsAdapter
import com.ssindher11.todonotes.model.BlogsResponse


class BlogActivity : AppCompatActivity() {

    private lateinit var blogsRV: RecyclerView

    val TAG: String = "BlogResponse"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)
        bindViews()
        getBlogsData()
    }

    private fun bindViews() {
        blogsRV = findViewById(R.id.rv_blogs)
    }

    private fun getBlogsData() {
        AndroidNetworking.get("http://www.mocky.io/v2/5926ce9d11000096006ccb30")
                .setPriority(Priority.HIGH)
                .build()
                .getAsObject(BlogsResponse::class.java, object : ParsedRequestListener<BlogsResponse> {
                    override fun onResponse(response: BlogsResponse?) {
                        setupRecyclerView(response)
                    }

                    override fun onError(anError: ANError?) {
                        Log.d(TAG, anError?.localizedMessage)
                    }

                })
    }

    private fun setupRecyclerView(response: BlogsResponse?) {
        val blogAdapter = BlogsAdapter(response!!.data)
        blogsRV.adapter = blogAdapter
    }
}
