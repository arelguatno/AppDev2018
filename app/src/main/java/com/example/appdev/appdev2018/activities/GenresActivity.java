package com.example.appdev.appdev2018.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.appdev.appdev2018.R;

public class GenresActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_genres);
    }

}
