package com.example.appdev.appdev2018.fragments;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.interfaces.Single_Player_4_buttons_ViewEvents;
import com.example.appdev.appdev2018.interfaces.TwoPlayerLocal_ViewEvents;
import com.example.appdev.appdev2018.pojos.Album;
import com.example.appdev.appdev2018.pojos.Song;
import com.example.appdev.appdev2018.pojos.SongsTitleForFillup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TwoPlayerLocal extends Fragment implements TwoPlayerLocal_ViewEvents {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    MediaPlayer mp;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private LinearLayout player1_linear_row1, player1_linear_row2, player2_linear_row1, player2_linear_row2;
    private Button player1_hitme_Button, player2_hitme_Button;
    private ProgressBar progressBar1, progressBar2;
    private int player1_buttons[] = {R.id.button13, R.id.button14, R.id.button15, R.id.button16};
    private int player2_buttons[] = {R.id.button9, R.id.button10, R.id.button11, R.id.button12};
    private ScheduledExecutorService service;
    SongsTitleForFillup songsTitleForFillup = new SongsTitleForFillup();
    private TwoPlayerLocal_ViewEvents viewClicked;

    private String correctAnswer;
    private int whereIsCorrectAnswer = 0;
    static int songChoice = 0;
    private List<Song> albumSong;
    private boolean playerChance = false;

    public TwoPlayerLocal() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TwoPlayerLocal.
     */
    // TODO: Rename and change types and number of parameters
    public static TwoPlayerLocal newInstance(String param1, String param2) {
        TwoPlayerLocal fragment = new TwoPlayerLocal();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_two_player_offline, container, false);
        albumSong = new ArrayList<>();

        player1_linear_row1 = view.findViewById(R.id.player1_linear_row1);
        player1_linear_row2 = view.findViewById(R.id.player1_linear_row2);

        player2_linear_row1 = view.findViewById(R.id.player2_linear_row1);
        player2_linear_row2 = view.findViewById(R.id.player2_linear_row2);

        progressBar1 = view.findViewById(R.id.progress1);
        progressBar2 = view.findViewById(R.id.progress2);

        play_music();

        player1_hitme_Button = view.findViewById(R.id.player1_hitme_Button);
        player1_hitme_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                player1_hitme_Button(view, getCorrectAnswer());
            }
        });

        player2_hitme_Button = view.findViewById(R.id.player2_hitme_Button);
        player2_hitme_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view1) {
                player2_hitme_Button(view, getCorrectAnswer());
            }
        });


        return view;
    }

    private void player1_hitme_Button(View view, String answerText) {
        player1_linear_row1.setVisibility(View.VISIBLE);
        player1_linear_row2.setVisibility(View.VISIBLE);
        player2_hitme_Button.setEnabled(false);

        player1_hitme_Button.setVisibility(View.GONE);

        loadTextButtons(view, answerText, player1_buttons);

        service.shutdown();
        mp.stop();

        progressBar1.setMax(100);
        progressBar1.setProgress(100);

        progressBar2.setMax(100);
        progressBar2.setProgress(100);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar1, "progress", 0);
        animation.setDuration(5000); // 5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        ObjectAnimator animation2 = ObjectAnimator.ofInt(progressBar2, "progress", 0);
        animation2.setDuration(5000); // 5 second
        animation2.setInterpolator(new DecelerateInterpolator());
        animation2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Timer end 5 seconds
                player1_linear_row1.setVisibility(View.GONE);
                player1_linear_row2.setVisibility(View.GONE);

                player2_hitme_Button.setEnabled(true);
                player1_hitme_Button.setEnabled(false);

                player1_hitme_Button.setVisibility(View.VISIBLE);
                progressBar1.setMax(mp.getDuration());
                progressBar1.setProgress(mp.getDuration());

                progressBar2.setMax(mp.getDuration());
                progressBar2.setProgress(mp.getDuration());

                if(!playerChance){
                    songChoice--; // Goto previous music, for other play chance
                    playerChance = true;
                    play_music();
                }else{
                    // End of chance play new music
                    player1_hitme_Button.setEnabled(true);
                    player2_hitme_Button.setEnabled(true);
                    playerChance = false;
                    play_music();
                }
                return;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animation2.start();
    }

    private void player2_hitme_Button(View view, String humble) {
        player2_linear_row1.setVisibility(View.VISIBLE);
        player2_linear_row2.setVisibility(View.VISIBLE);
        player1_hitme_Button.setEnabled(false);
        player2_hitme_Button.setVisibility(View.GONE);

        loadTextButtons(view, humble, player2_buttons);

        service.shutdown();
        mp.stop();

        progressBar1.setMax(100);
        progressBar1.setProgress(100);

        progressBar2.setMax(100);
        progressBar2.setProgress(100);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar1, "progress", 0);
        animation.setDuration(5000); // 5 second
        animation.setInterpolator(new DecelerateInterpolator());
        animation.start();

        ObjectAnimator animation2 = ObjectAnimator.ofInt(progressBar2, "progress", 0);
        animation2.setDuration(5000); // 5 second
        animation2.setInterpolator(new DecelerateInterpolator());
        animation2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                // Timer end 5 seconds
                player2_linear_row1.setVisibility(View.GONE);
                player2_linear_row2.setVisibility(View.GONE);

                player1_hitme_Button.setEnabled(true);
                player2_hitme_Button.setEnabled(false);

                player2_hitme_Button.setVisibility(View.VISIBLE);
                progressBar1.setMax(mp.getDuration());
                progressBar1.setProgress(mp.getDuration());

                progressBar2.setMax(mp.getDuration());
                progressBar2.setProgress(mp.getDuration());

                if(!playerChance){
                    songChoice--; //Goto previous music, for other play chance
                    playerChance = true;
                    play_music();
                }else{
                    // End of chance play new music
                    playerChance = false;
                    player1_hitme_Button.setEnabled(true);
                    player2_hitme_Button.setEnabled(true);
                    play_music();
                }
                return;
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animation2.start();
    }

    private void loadTextButtons(View v, String answer, int arrays[]) {
        Button btn;
        // Clear buttons text
        for (int x = 0; x < arrays.length; x++) {
            btn = v.findViewById(arrays[x]);
            btn.setText("");
        }

        int getRandPos = (int) ((Math.random() * 4) + 1);
        whereIsCorrectAnswer = getRandPos;
        switch (getRandPos) {
            case 1:
                btn = v.findViewById(arrays[0]);
                btn.setText(answer);
                break;
            case 2:
                btn = v.findViewById(arrays[1]);
                btn.setText(answer);
                break;
            case 3:
                btn = v.findViewById(arrays[2]);
                btn.setText(answer);
                break;
            case 4:
                btn = v.findViewById(arrays[3]);
                btn.setText(answer);
                break;
        }

        for (int x = 0; x < arrays.length; x++) {
            btn = v.findViewById(arrays[x]);
            if (btn.getText().toString() == "") {
                int getRand = (int) ((Math.random() * songsTitleForFillup.getListofSongs.length));
                btn.setText(songsTitleForFillup.getListofSongs[getRand].toString());

            }
        }

    }

    private void play_music() {

        Song song = new Song("pop1_congratulations","Congratulations");
        albumSong.add(song);

        song = new Song("pop2_hayaan_mo_sila","Hayaan Mo Sila");
        albumSong.add(song);

        song = new Song("pop3_humble","Humble");
        albumSong.add(song);

        song = new Song("pop4_mask_off","Mask Off");
        albumSong.add(song);


        if ((songChoice+1) == albumSong.size()){
            // no more music
            return;
        }

        mp = MediaPlayer.create(getContext(), getSongIdByName(getContext(), albumSong.get(songChoice).getSongID()));
        setCorrectAnswer(albumSong.get(songChoice).getSongTitle());
        ++songChoice; // Increment for the next music

        progressBar1.setMax(mp.getDuration());
        progressBar1.setProgress(mp.getDuration());

        progressBar2.setMax(mp.getDuration());
        progressBar2.setProgress(mp.getDuration());

        if (!mp.isPlaying()) {
            mp.start();

            service = Executors.newScheduledThreadPool(1);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    service.shutdown();
                    playerChance = false;
                    player1_hitme_Button.setEnabled(true);
                    player2_hitme_Button.setEnabled(true);

                    progressBar1.setMax(0);
                    progressBar2.setMax(0);

                    //Play next song, if no one hit the button
                    play_music();
                }
            });
            service.scheduleWithFixedDelay(new Runnable() {
                @Override
                public void run() {
                    progressBar1.setProgress(mp.getDuration() - mp.getCurrentPosition());
                    progressBar2.setProgress(mp.getDuration() - mp.getCurrentPosition());
                }
            }, 1, 1, TimeUnit.MICROSECONDS);
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TwoPlayerLocal_ViewEvents) {
            viewClicked = (TwoPlayerLocal_ViewEvents) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mp.stop();
    }

    @Override
    public void onback_press() {

    }

    public static int getSongIdByName(Context c, String ImageName) {
        return c.getResources().getIdentifier(ImageName, "raw", c.getPackageName());
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
}
