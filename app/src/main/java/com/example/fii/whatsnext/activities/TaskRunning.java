package com.example.fii.whatsnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.fii.whatsnext.R;


public class TaskRunning extends AppCompatActivity {

    public static final String EXTRA_TOTALTIME = "TOTALTIME";
    public static final  String EXTRA_BREAKTIME = "BREAKTIME";
    public static final String EXTRA_ESTIMATED = "ESTIMATE";

    long baseTime = 0;
    long breakTime = 0;
    long lastBreakStart = 0;
    long lastBreakStop = 0;
    long productiveTime =0;
    long estimatedTime=0;
    Chronometer chronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String taskName = intent.getStringExtra(DoTask.TASKNAME);
        estimatedTime = intent.getLongExtra(DoTask.TASKESTIMATE, 0);
        setContentView(R.layout.activity_task_running);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        chronometer = (Chronometer)findViewById(R.id.chronometer);
        baseTime = SystemClock.elapsedRealtime();
        lastBreakStop = baseTime;
        chronometer.setBase(baseTime);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        chronometer.start();
        TextView displayedTask = (TextView)findViewById(R.id.displayed_task);
        assert displayedTask != null;
        displayedTask.setText(taskName);
    }

    public void onPause(View view){
        //save elapsed time and pause chronometer
        ToggleButton tb = (ToggleButton)view;
        if(tb.isChecked()){
            //resume
            long currentBreakTime = SystemClock.elapsedRealtime() - lastBreakStart;
            breakTime += currentBreakTime;
            lastBreakStop = SystemClock.elapsedRealtime();
            chronometer.setBase(baseTime + breakTime);
            chronometer.start();

            }
        else {
            //break
            productiveTime += SystemClock.elapsedRealtime()-lastBreakStop;
            chronometer.stop();
            lastBreakStart = SystemClock.elapsedRealtime();
        }

    }

    public void onDone(View view){
        chronometer.stop();
        //start intent displaying elapsed times and breaks
        Intent intent = new Intent(this, TaskFinishedActivity.class);
        intent.putExtra(EXTRA_TOTALTIME, SystemClock.elapsedRealtime()-baseTime);
        intent.putExtra(EXTRA_BREAKTIME, breakTime);
        intent.putExtra(EXTRA_ESTIMATED, estimatedTime);
        startActivity(intent);
        finish();
    }


}
