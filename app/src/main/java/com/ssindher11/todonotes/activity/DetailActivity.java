package com.ssindher11.todonotes.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.ssindher11.todonotes.AppConstants;
import com.ssindher11.todonotes.R;

public class DetailActivity extends AppCompatActivity {

    private TextView titleTV, descTV;

    private String title, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        bindViews();
        setIntentData();
    }

    private void bindViews() {
        titleTV = findViewById(R.id.tv_title_detail);
        descTV = findViewById(R.id.tv_desc_detail);
    }

    private void setIntentData() {
        Intent intent = getIntent();
        title = intent.getStringExtra(AppConstants.TITLE);
        description = intent.getStringExtra(AppConstants.DESCRIPTION);
        titleTV.setText(title);
        descTV.setText(description);
    }

}
