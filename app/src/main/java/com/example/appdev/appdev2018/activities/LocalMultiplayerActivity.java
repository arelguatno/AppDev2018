package com.example.appdev.appdev2018.activities;

import android.os.Bundle;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.fragments.TwoPlayerLocal;
import com.example.appdev.appdev2018.interfaces.TwoPlayerLocal_ViewEvents;
import com.google.firebase.firestore.FirebaseFirestore;

public class LocalMultiplayerActivity extends BaseActivity implements TwoPlayerLocal_ViewEvents {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setToFullScreen();

        db = FirebaseFirestore.getInstance();
        setContentView(R.layout.activity_offline_multiplayer);
        bgPauseMusic();

        if (findViewById(R.id.fragment_container) != null) {
            // However, if we're being restored from a previous state,
            // then we don't need to do anything and should return or else
            // we could end up with overlapping fragments.
            if (savedInstanceState != null) {
                return;
            }

            TwoPlayerLocal firstFragment = new TwoPlayerLocal();
            // firstFragment.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bgResumeMusic();
    }

    @Override
    public void onback_press() {

    }
}
