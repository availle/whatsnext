package com.example.fii.whatsnext.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.example.fii.whatsnext.R;


public class MainActivity extends AppCompatActivity {


    public final static String EXTRA_MESSAGE = "whatsnext.MESSAGE";
    public final static int SHORT = 0;
    public final static int MED = 1;
    public final static int LONG = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createTask();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void openShortTask(View view){
        openTask(SHORT);
    }


    public void openLongTask(View view){
        openTask(LONG);
    }

    public void openMedTask(View view){
        openTask(MED);
    }

    private void createTask(){
        Intent intent = new Intent(this, AddTask.class);
        startActivity(intent);
    }

    private void openTask(int duration){
        Intent intent = new Intent(this, DoTask.class);
        intent.putExtra(EXTRA_MESSAGE, duration);
        startActivity(intent);
    }
}
