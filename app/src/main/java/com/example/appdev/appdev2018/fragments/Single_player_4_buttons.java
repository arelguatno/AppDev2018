package com.example.appdev.appdev2018.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.interfaces.Single_Player_4_buttons_ViewEvents;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Single_player_4_buttons.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Single_player_4_buttons#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Single_player_4_buttons extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Single_Player_4_buttons_ViewEvents viewClicked;
    private int listOfButtons[] = {R.id.s_button1, R.id.s_button2, R.id.s_button3, R.id.s_button4};

    public Single_player_4_buttons() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Single_player_4_buttons.
     */
    // TODO: Rename and change types and number of parameters
    public static Single_player_4_buttons newInstance(String param1, String param2) {
        Single_player_4_buttons fragment = new Single_player_4_buttons();
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

        View v = inflater.inflate(R.layout.fragment_single_player_4_buttons, container, false);
        String strtext = getArguments().getString("correct_answer");
        String songTitle = getArguments().getString("answerTitle");

        loadTextButtons(v,songTitle);

        Button btn1 = v.findViewById(R.id.s_button1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClicked.onFragmentSButton1Clicked();
            }
        });

        Button btn2 = v.findViewById(R.id.s_button2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClicked.onFragmentSButton2Clicked();
            }
        });

        Button btn3 = v.findViewById(R.id.s_button3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClicked.onFragmentSButton3Clicked();
            }
        });

        Button btn4 = v.findViewById(R.id.s_button4);
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewClicked.onFragmentSButton4Clicked();
            }
        });


        return v;
    }

    private void loadTextButtons(View v, String answer) {
        Button btn;
        // Clear buttons text
        for (int x = 0; x < listOfButtons.length; x++){
            btn = v.findViewById(listOfButtons[x]);
            btn.setText("");
        }

        int getRandPos = (int) ((Math.random() * 4) + 1);
        switch (getRandPos){
            case 1:
                btn = v.findViewById(listOfButtons[0]);
                btn.setText(answer);
                break;
            case 2:
                btn = v.findViewById(listOfButtons[1]);
                btn.setText(answer);
                break;
            case 3:
                btn = v.findViewById(listOfButtons[2]);
                btn.setText(answer);
                break;
            case 4:
                btn = v.findViewById(listOfButtons[3]);
                btn.setText(answer);
                break;
        }

        for (int x = 0; x < listOfButtons.length; x++){
            btn = v.findViewById(listOfButtons[x]);
            if(btn.getText().toString() == ""){
                btn.setText("Generated Text");
            }
        }

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Single_Player_4_buttons_ViewEvents) {
            viewClicked = (Single_Player_4_buttons_ViewEvents) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
