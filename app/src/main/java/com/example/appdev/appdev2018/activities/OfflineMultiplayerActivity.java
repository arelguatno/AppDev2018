package com.example.appdev.appdev2018.activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.fragments.Single_player_4_buttons;
import com.example.appdev.appdev2018.fragments.TwoPlayerOffline;
import com.google.firebase.firestore.FirebaseFirestore;

public class OfflineMultiplayerActivity extends BaseActivity implements TwoPlayerOffline.OnFragmentInteractionListener {
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

            TwoPlayerOffline firstFragment = new TwoPlayerOffline();
            // firstFragment.setArguments(bundle);

            // Add the fragment to the 'fragment_container' FrameLayout
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, firstFragment).commit();
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        bgResumeMusic();
    }

}
