package com.example.appdev.appdev2018.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.appdev.appdev2018.R;
import com.example.appdev.appdev2018.interfaces.Single_Player_4_buttons_ViewEvents;
import com.example.appdev.appdev2018.interfaces.Single_player_blank_fields_ViewEvents;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Single_player_blank_fields.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Single_player_blank_fields#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Single_player_blank_fields extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private Single_player_blank_fields_ViewEvents viewClicked;

    public Single_player_blank_fields() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Single_player_blank_fields.
     */
    // TODO: Rename and change types and number of parameters
    public static Single_player_blank_fields newInstance(String param1, String param2) {
        Single_player_blank_fields fragment = new Single_player_blank_fields();
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
        View view = inflater.inflate(R.layout.fragment_single_player_blank_fields, container, false);
        String strtext = getArguments().getString("correct_answer");
        convertAnswerToUnderLine(view, strtext);

        return view;
    }

    private void convertAnswerToUnderLine(View v, String correctAnswer){
        String new_word = "";
        String extraSpace = "&#160;";

        for(int x= 0; x < correctAnswer.length(); x++){
            if(String.valueOf(correctAnswer.charAt(x)).equalsIgnoreCase("*")){
                new_word= new_word + extraSpace + extraSpace;
            }else{
                new_word= new_word + "_" + extraSpace;
            }
        }

        TextView txt = v.findViewById(R.id.black_text_filed);
        txt.setText(Html.fromHtml(new_word));
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
        if (context instanceof Single_player_blank_fields_ViewEvents) {
            viewClicked = (Single_player_blank_fields_ViewEvents) context;
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
