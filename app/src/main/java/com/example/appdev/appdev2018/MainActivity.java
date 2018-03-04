package com.example.appdev.appdev2018;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        playAudio();
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopAudio();
    }

    public void playAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        startService(objIntent);
    }

    public void stopAudio() {
        Intent objIntent = new Intent(this, PlayAudio.class);
        stopService(objIntent);
    }
}
