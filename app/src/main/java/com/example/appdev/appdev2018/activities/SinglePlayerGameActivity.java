package com.example.appdev.appdev2018.activities;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.fragments.Single_player_4_buttons;
import com.example.appdev.appdev2018.fragments.Single_player_blank_fields;
import com.example.appdev.appdev2018.interfaces.Single_Player_4_buttons_ViewEvents;
import com.example.appdev.appdev2018.interfaces.Single_player_blank_fields_ViewEvents;

public class SinglePlayerGameActivity extends BaseActivity implements Single_Player_4_buttons_ViewEvents, Single_player_blank_fields_ViewEvents {
    static MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();
        setContentView(R.layout.activity_single_player_game);

        int resID = R.raw.pop1_hayaan_mo_sila;
        mp = MediaPlayer.create(this, resID);
        bgPauseMusic();

        // Check that the activity is using the layout version with
        // the fragment_container FrameLayout
        if (findViewById(R.id.fragment_container) != null) {

            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            Bundle bundle = new Bundle();
            bundle.putString("correct_answer","hayaan*mo*sila");

            Single_player_blank_fields firstFragment = new Single_player_blank_fields();
            // In case this activity was started with special instructions from an
            // Intent, pass the Intent's extras to the fragment as arguments
            firstFragment.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
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

    @Override
    public void onFragmentSButton1Clicked() {
        Toast.makeText(this, "Button1",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentSButton2Clicked() {
        Toast.makeText(this, "Button2",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentSButton3Clicked() {
        Toast.makeText(this, "Button3",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFragmentSButton4Clicked() {
        Toast.makeText(this, "Button4",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void sample_method() {

    }
}
