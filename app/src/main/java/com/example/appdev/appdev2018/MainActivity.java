package com.example.appdev.appdev2018;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends AppCompatActivity {
    private boolean mIsBound = false;
    private MusicService mServ;
    boolean misPlaying = true;


    private ServiceConnection Scon = new ServiceConnection() {

        public void onServiceConnected(ComponentName name, IBinder
                binder) {
            mServ = ((MusicService.ServiceBinder) binder).getService();
        }

        public void onServiceDisconnected(ComponentName name) {
            mServ = null;
        }
    };

    void doBindService() {
        bindService(new Intent(this, MusicService.class),
                Scon, Context.BIND_AUTO_CREATE);
        mIsBound = true;
    }

    void doUnbindService() {
        if (mIsBound) {
            unbindService(Scon);
            mIsBound = false;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Full Screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        // Start Music Service
        Intent music = new Intent();
        music.setClass(this, MusicService.class);
        startService(music);
        doBindService();
    }


    @Override
    protected void onStop() {
        super.onStop();
        //mServ.stopMusic();
        doUnbindService();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (misPlaying) {
            mServ.pauseMusic();
            misPlaying = false;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!misPlaying) {
            mServ.resumeMusic();
            misPlaying = true;
        }
    }
}
