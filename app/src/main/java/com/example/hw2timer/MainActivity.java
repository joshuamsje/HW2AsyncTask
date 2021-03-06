package com.example.hw2timer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ControlFragment.OnFragmentInteractionListener {

    int count = 0;
    Button nextButton;
    ControlFragment controlFragment;
    ListFragment listFragment;

    Timer updateTime;
    ArrayList<String> aList;

    TimerAsyncTask task;;
    boolean startButtonCheck;
    boolean isReset;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controlFragment = (ControlFragment) getSupportFragmentManager().findFragmentById(R.id.controlFrag);
        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        nextButton = findViewById(R.id.nextButton);

        updateTime = new Timer();
        isReset = false;
        task = new TimerAsyncTask();
        aList = updateTime.getTimeList();

        if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            nextButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    openListScreen();

                }
            });
        }


    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {

        super.onSaveInstanceState(outState);
        outState.putInt("counter", count);
        outState.putBoolean("ifReset", isReset);
        outState.putStringArrayList("lapList", aList);
        outState.putBoolean("ifStart", startButtonCheck);
        outState.putString("timer", controlFragment.timer.getText().toString());
        outState.putString("startButton", controlFragment.startButton.getText().toString());


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        count = savedInstanceState.getInt("counter");
        isReset = savedInstanceState.getBoolean("ifReset");
        aList = savedInstanceState.getStringArrayList("lapList");
        startButtonCheck = savedInstanceState.getBoolean("ifStart");

        controlFragment.startButton.setText(savedInstanceState.getString("startButton"));
        controlFragment.timer.setText(savedInstanceState.getString("timer"));

        if(task.getStatus() != AsyncTask.Status.RUNNING && startButtonCheck)
        {
            task = new TimerAsyncTask();
            task.execute();
        }
    }

    @Override
    protected void onDestroy() {

        if(task != null && task.getStatus() == AsyncTask.Status.RUNNING)
        {
            task.cancel(true);
            
        }
        super.onDestroy();
    }


    @Override
    public void onButtonClicked(int infoID)
    {
        if (infoID == 0)
        {
            isReset = false;
            if(controlFragment.startButton.getText() == "Stop")
            {
                startButtonCheck = false;
                controlFragment.startButton.setText("Start");
            }
            else
            {
                startButtonCheck = true;
                controlFragment.startButton.setText("Stop");
            }
            if (task.getStatus() != AsyncTask.Status.RUNNING && startButtonCheck)
            {

                task = new TimerAsyncTask();
                task.execute();
            }
        }
        else if (infoID == 1)
        {
            Log.v("timer", controlFragment.timer.getText().toString());
            updateTime.addTime(controlFragment.timer.getText().toString());

            if ((listFragment != null) && (listFragment.isInLayout()))
            {
                aList.add(controlFragment.timer.getText().toString());
                if (isReset)
                {

                    listFragment.lapHolder.setText("");
                }
                else
                {

                    count++;
                    listFragment.setResult(aList, count);
                }

            }



        }
        else if (infoID == 2)
        {
            count = 0;
            startButtonCheck = false;
            controlFragment.timer.setText("00:00:00");
            controlFragment.startButton.setText("Start");
            updateTime.reset();
            isReset = true;
            if (listFragment != null && listFragment.isInLayout())
            {
                listFragment.clear();
            }

            aList.clear();
        }
    }

    public void openListScreen()
    {
        Intent intent = new Intent(this, ListScreen.class);
        intent.putStringArrayListExtra("list", aList);
        intent.putExtra("counter", count);
        intent.putExtra("resetCheck", isReset);
        startActivity(intent);
    }


    @SuppressLint("StaticFieldLeak")
    private class TimerAsyncTask extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            @SuppressLint("DefaultLocale") String curr_time = String.format("%02d:%02d:%02d", values[0], values[1], values[2]); // used to properly format string.
            controlFragment.timer.setText(curr_time); // update UI.
        }

        @Override
        protected Void doInBackground(Integer... times) {

            while (startButtonCheck) { // running boolean must be updated in controller.
                if (!isCancelled() && startButtonCheck)
                {

                    updateTime.calc(); // calculate time.
                    publishProgress(updateTime.getHours(), updateTime.getMinutes(), updateTime.getSeconds()); // publish progress fomr onProgressUpdate method to be triggered.
                    try {
                        Thread.sleep(1000); // sleep for 1 second (1000 milliseconds).
                    }
                    catch (Exception e) {
                        System.out.println(e);
                    }
                }
                else
                {
                    break;
                }

            }

            return null;
        }
    }




}