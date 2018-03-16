package com.example.appdev.appdev2018.activities;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.example.appdev.appdev2018.services.MusicService;

/**
 * Created by aguatno on 3/11/18.
 */

public class BaseActivity extends AppCompatActivity {

//    private boolean mIsBound = false;
//    protected MusicService mServ;
    public ProgressDialog mProgressDialog;
    protected  static MediaPlayer bgMusic;
    private int bgMusicLength = 0;
    static boolean iBgMusicsPlaying = false;


//    private ServiceConnection Scon = new ServiceConnection() {
//
//        public void onServiceConnected(ComponentName name, IBinder
//                binder) {
//            mServ = ((MusicService.ServiceBinder) binder).getService();
//        }
//
//        public void onServiceDisconnected(ComponentName name) {
//            mServ = null;
//        }
//    };
//
//    void doBindService() {
//        bindService(new Intent(this, MusicService.class),
//                Scon, Context.BIND_AUTO_CREATE);
//        mIsBound = true;
//    }
//
//    void doUnbindService() {
//        if (mIsBound) {
//            unbindService(Scon);
//            mIsBound = false;
//        }
//    }

    protected void setToFullScreen(){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void showProgressDialog(String msg) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(msg);
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    public void bgPauseMusic() {
        if (bgMusic.isPlaying()) {
            bgMusic.pause();
            bgMusicLength = bgMusic.getCurrentPosition();

        }
    }

    public void bgResumeMusic() {
        if (bgMusic.isPlaying() == false) {
            bgMusic.seekTo(bgMusicLength);
            bgMusic.start();
        }
    }

    public void bgStopMusic() {
        bgMusic.stop();
        bgMusic.release();
        bgMusic = null;
    }

}
