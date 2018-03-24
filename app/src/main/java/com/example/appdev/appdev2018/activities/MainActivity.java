package com.example.appdev.appdev2018.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.pojos.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

    private CallbackManager mCallbackManager;
    static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Full Screen
        FacebookSdk.sdkInitialize(getApplicationContext());
        setToFullScreen();
        setContentView(R.layout.activity_main);


        bgMusic = MediaPlayer.create(this, R.raw.beat_fever);

        findViewById(R.id.imageView).setVisibility(View.GONE);
        findViewById(R.id.imageView3).setVisibility(View.GONE);
        findViewById(R.id.imageView4).setVisibility(View.GONE);
        findViewById(R.id.textView2).setVisibility(View.GONE);
        findViewById(R.id.textView).setVisibility(View.GONE);
        findViewById(R.id.imageView5).setVisibility(View.GONE);
        findViewById(R.id.imageView6).setVisibility(View.GONE);


        if (!iBgMusicsPlaying) {
            bgMusic.setLooping(true);
            bgMusic.start();
            iBgMusicsPlaying = true;
        }

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // [START initialize_fblogin]
        // Initialize Facebook Login button
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.button_facebook_login);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // [START_EXCLUDE]
                updateUI(null);
                // [END_EXCLUDE]
            }
        });
        // [END initialize_fblogin]
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void play_button(View view) {
        Intent intent = new Intent(this, GenresActivity.class);
        this.startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            updateDBUserLastLogin(user);
            findViewById(R.id.signInButton).setVisibility(View.GONE);
            findViewById(R.id.button_facebook_login).setVisibility(View.GONE);
            findViewById(R.id.imageView).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView3).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView4).setVisibility(View.VISIBLE);
            findViewById(R.id.textView2).setVisibility(View.VISIBLE);
            findViewById(R.id.textView).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView5).setVisibility(View.VISIBLE);
            findViewById(R.id.imageView6).setVisibility(View.VISIBLE);

            Glide.with(this)
                    .load(mAuth.getCurrentUser().getPhotoUrl())
                    .into((ImageView) findViewById(R.id.imageView));

        } else {
            findViewById(R.id.signInButton).setVisibility(View.VISIBLE);
            findViewById(R.id.button_facebook_login).setVisibility(View.VISIBLE);

            findViewById(R.id.imageView).setVisibility(View.GONE);
            findViewById(R.id.imageView3).setVisibility(View.GONE);
            findViewById(R.id.imageView4).setVisibility(View.GONE);
            findViewById(R.id.textView2).setVisibility(View.GONE);
            findViewById(R.id.textView).setVisibility(View.GONE);
            findViewById(R.id.imageView5).setVisibility(View.GONE);
            findViewById(R.id.imageView6).setVisibility(View.GONE);

        }
    }

    public void logout_facebook(View v) {
        final

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Logged in as : " + mAuth.getCurrentUser().getDisplayName());

        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // Do nothing but close the dialog

                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                updateUI(null);
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Pass the activity result back to the Facebook SDK
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }


    // [START auth_with_facebook]
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        // [START_EXCLUDE silent]
        showProgressDialog("Logging in..");
        // [END_EXCLUDE]

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            saveUserInfotoDB(user, task);
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        hideProgressDialog();
                        // [END_EXCLUDE]
                    }
                });
    }


    private void updateDBUserLastLogin(FirebaseUser user) {
        db.collection(DB_USERS_COLLECTION_NAME)
                .document(user.getUid())
                .update(DB_LAST_LOGIN_FIELD, new Date());
    }

    private void saveUserInfotoDB(FirebaseUser user, Task<AuthResult> task) {
        // Save user to Firestore
        if (task.getResult().getAdditionalUserInfo().isNewUser()) {
            User user1 = new User(user.getDisplayName(),
                    user.getEmail(),
                    user.getPhotoUrl().toString(),
                    user.getProviderId(),
                    user.getUid(),
                    new Date(),
                    new Date());

            db.collection(DB_USERS_COLLECTION_NAME)
                    .document(user.getUid()).set(user1);

        } else {
            updateDBUserLastLogin(user);
        }
    }
}
