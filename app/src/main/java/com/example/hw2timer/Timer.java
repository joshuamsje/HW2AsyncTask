package com.example.hw2timer;

import android.util.Log;

import java.util.ArrayList;

public class Timer {
    private ArrayList<String> timeList = new ArrayList<String>();

    private int sec = 0;
    private int min = 0;
    private int hr = 0;

    public Timer()
    {

    }

    public ArrayList<String> getTimeList() {
        return timeList;
    }

    public int getSeconds() {
        return sec;
    }

    public int getMinutes() {
        return min;
    }

    public int getHours() {
        return hr;
    }

    public void calc()
    {
        sec++;
        if (sec == 60)
        {
            sec = 0;
            min++;
        }
        if (min == 60)
        {
            min = 0;
            hr++;
        }
    }

    public void addTime(String time)
    {
        timeList.add(time);
        //return timeList;
    }


    public void reset()
    {
        sec = 0;
        min = 0;
        hr = 0;
        timeList = new ArrayList<String>();

    }
}
