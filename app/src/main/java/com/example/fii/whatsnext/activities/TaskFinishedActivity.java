package com.example.fii.whatsnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Chronometer;

import com.example.fii.whatsnext.R;

public class TaskFinishedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_finished);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        long totaltime = intent.getLongExtra(TaskRunning.EXTRA_TOTALTIME, 0);
        long breaktime = intent.getLongExtra(TaskRunning.EXTRA_BREAKTIME, 0);
        long estimatedtime = intent.getLongExtra(TaskRunning.EXTRA_ESTIMATED, 0);
        long basetime = SystemClock.elapsedRealtime();

        Chronometer totalChrono = (Chronometer)findViewById(R.id.chrono_elapsed);
        totalChrono.setBase(basetime-totaltime);
        Chronometer breakChrono = (Chronometer)findViewById(R.id.chrono_breaks);
        breakChrono.setBase(basetime-breaktime);
        Chronometer productiveChrono = (Chronometer)findViewById(R.id.chrono_productive);
        productiveChrono.setBase(basetime-(totaltime-breaktime));
        Chronometer estimatedChrono = (Chronometer)findViewById(R.id.chrono_estimate);
        estimatedChrono.setBase(basetime - estimatedtime);

    }

    public void startOver(View view) {
        finish();
    }
}
