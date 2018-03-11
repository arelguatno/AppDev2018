package com.example.appdev.appdev2018.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.services.MusicService;


public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        setToFullScreen();
        setContentView(R.layout.activity_main);

        doBindService();

        // Start Music Service
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);

    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        if (misPlaying) {
//            mServ.pauseMusic();
//            misPlaying = false;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (!misPlaying) {
//            mServ.resumeMusic();
//            misPlaying = true;
//        }
//    }

    public void play_button(View view){
        Intent intent = new Intent(this, GenresActivity.class);
        this.startActivity(intent);
    }
}
