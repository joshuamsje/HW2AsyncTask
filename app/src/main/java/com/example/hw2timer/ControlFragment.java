package com.example.hw2timer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class ControlFragment extends Fragment implements View.OnClickListener{

    int count = 0;

    Button startButton;
    Button lapButton;
    Button resetButton;

    TextView timer;

    Timer updateTime;
    private OnFragmentInteractionListener mListener;

    public ControlFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.control_fragment, container, false);


        lapButton = view.findViewById(R.id.lapButton);
        startButton = view.findViewById(R.id.startButton);
        resetButton = view.findViewById(R.id.resetButton);

        updateTime = new Timer();
        timer = view.findViewById(R.id.timer);


        startButton.setOnClickListener(this);
        lapButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);




        return view;

    }

    @Override
    public void onAttach(Context context) {

        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener)
        {
            this.mListener = (OnFragmentInteractionListener) context;
        }
        else
        {
            throw new RuntimeException(context.toString() + "must implement");
        }
    }

    @Override
    public void onDetach() {

        super.onDetach();

    }

    public void onClick(View view)
    {
        if(view.getId() == startButton.getId())
        {
            mListener.onButtonClicked(0);
        }
        else if(view.getId() == lapButton.getId())
        {
            mListener.onButtonClicked(1);
        }
        else if(view.getId() == resetButton.getId())
        {
            mListener.onButtonClicked(2);
        }
    }

    public interface OnFragmentInteractionListener{
        void onButtonClicked(int infoID);
    }



}


