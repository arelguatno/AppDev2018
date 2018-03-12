package com.example.appdev.appdev2018.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.example.appdev.appdev2018.R;

public class SinglePlayerGameActivity extends BaseActivity {
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_single_player_game);

        mp = MediaPlayer.create(this, R.raw.hayaan_mo_sila);
        appPauseMusic();
    }

    public void start_button(View v){
        if(mp.isPlaying()){
            mp.stop();
        }else{
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        appResumeMusic();
        mp.stop();
        mp.release();
    }
}
