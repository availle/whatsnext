package com.example.fii.whatsnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.example.fii.whatsnext.R;
import com.example.fii.whatsnext.database.DBAccess;
import com.example.fii.whatsnext.model.Task;

import java.util.List;
import java.util.Random;

public class DoTask extends AppCompatActivity {
    public static final String TASKNAME = "TASK_NAME";
    public static final String TASKESTIMATE = "TASK_ESTIMATE" ;
    private int taskLength;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_do_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //get Message String and parse length
        Intent intent = getIntent();
        taskLength = intent.getIntExtra(MainActivity.EXTRA_MESSAGE, MainActivity.SHORT);
        displayTask();



        //when activity finishes, stop clock
        //so we need a clock, a textfield for the activity, and a start/stop button

    }


    public void runTask(View view){
        Intent intent = new Intent(this, TaskRunning.class);
        intent.putExtra(TASKNAME, currentTask.name);
        long duration = currentTask.duration*60000;//millis
        intent.putExtra(TASKESTIMATE, duration);
        startActivity(intent);
        finish();
    }

    private void displayTask(){
        String task = getTaskFromDatabase();
        TextView textView = (TextView)findViewById(R.id.taskDisplay);
        textView.setText(task);

    }


    private String getTaskFromDatabase(){
        List<Task> tasks;
        tasks = new DBAccess(getApplicationContext()).getTasks(taskLength);
        currentTask = getRandomTask(tasks);
        return currentTask.name;
    }


    private Task getRandomTask(List<Task> nonRandomTasks){
        if(nonRandomTasks.isEmpty()){
            sendAlertNoTasks();
        }

        else {
            Random random = new Random(System.currentTimeMillis());
            return nonRandomTasks.get(random.nextInt(nonRandomTasks.size()));
        }
        return new Task();
    }

    private void sendAlertNoTasks(){
        View parentLayout = findViewById(R.id.do_task_root);
        Snackbar.make(parentLayout, "No task with that length available.",
                Snackbar.LENGTH_LONG).setAction("Action", null).show();
        finish();
    }


}
