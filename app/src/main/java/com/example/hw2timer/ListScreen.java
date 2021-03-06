package com.example.hw2timer;

import android.app.AppComponentFactory;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListScreen extends AppCompatActivity {
    ListFragment listFragment;
    boolean resetC;
    ArrayList<String> info;
    Button backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list);


        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            finish();
            return;
        }


        listFragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFrag);
        resetC = getIntent().getExtras().getBoolean("resetCheck");
        info = getIntent().getStringArrayListExtra("list");


        if (resetC)
        {
            listFragment.clear();
        }
        else if ((!info.isEmpty()) && (getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE))
        {
            Bundle b1 = getIntent().getExtras();
            int counter = b1.getInt("counter");


            listFragment.setResult(info, counter);
        }








        backButton = findViewById(R.id.backButton);
        if(getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE)
        {
            backButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openMainScreen();
                }
            });
        }


    }

    public void openMainScreen()
    {
        finish();
    }

}
