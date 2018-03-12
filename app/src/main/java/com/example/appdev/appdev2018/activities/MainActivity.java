package com.example.appdev.appdev2018.activities;

import android.content.Intent;
import android.media.MediaPlayer;
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

//        doBindService();
//
//        // Start Music Service
//        Intent music = new Intent();
//        music.setClass(this, MusicService.class);
//        startService(music);

        bgMusic = MediaPlayer.create(this, R.raw.beat_fever);

        if(!isPlaying){
            bgMusic.setLooping(true);
            bgMusic.start();
            isPlaying = true;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void play_button(View view){
        Intent intent = new Intent(this, GenresActivity.class);
        this.startActivity(intent);
    }
}
