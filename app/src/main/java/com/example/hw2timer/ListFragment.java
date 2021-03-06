package com.example.hw2timer;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.service.controls.Control;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ListFragment extends Fragment {

    boolean landscapeCheck;
    TextView lapHolder;
    int listCheck = 0;
    public ListFragment()
    {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.list_fragment, container, false);
        lapHolder = view.findViewById(R.id.listScreen);
        lapHolder.setMovementMethod(new ScrollingMovementMethod());
        return view;

    }



    public void setResult(ArrayList<String> list, int counter) {
        String result = "";
        for (int i = 0; i < list.size(); i++)
        {

            result += i + ": " + list.get(i) + "\n";
        }

        lapHolder.setText(result);


        listCheck++;


    }



    public void clear()
    {
        lapHolder.setText("");
    }




}
