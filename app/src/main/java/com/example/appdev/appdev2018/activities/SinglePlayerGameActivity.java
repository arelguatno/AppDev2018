package com.example.appdev.appdev2018.activities;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appdev.appdev2018.R;

public class SinglePlayerGameActivity extends BaseActivity {
    static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_single_player_game);

        int resID = R.raw.pop1_hayaan_mo_sila;
        mp = MediaPlayer.create(this, resID);
        bgPauseMusic();
    }

    public void start_button(View v){
        if(!mp.isPlaying()){
            mp.start();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bgResumeMusic();
        mp.stop();
        mp.release();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                bgResumeMusic();
                mp.stop();
                mp.release();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
