package com.example.fii.whatsnext.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;

import com.example.fii.whatsnext.R;
import com.example.fii.whatsnext.database.DBAccess;
import com.example.fii.whatsnext.model.Task;

public class AddTask extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void addTask(View view){
        //get Task name and length from input fields

        EditText nameText = (EditText)findViewById(R.id.taskName);
        assert nameText != null;
        String name = nameText.getText().toString();
        EditText durationText = (EditText)findViewById(R.id.taskDuration);
        if(name.equals("")){
            createAlert("Please enter valid name.");
            return;
        }
        assert durationText != null;
        String str = durationText.getText().toString();
        if(str.equals("")){
            createAlert("Please enter valid duration.");
            return;
        }
            int duration = Integer.parseInt(durationText.getText().toString());
            Task task = new Task();
            task.duration = duration;
            task.name = name;
            new DBAccess(getApplicationContext()).addTask(task);
        createSuccess("Inserted activity.");


        //TODO: USER GIVES INPUT HERE. INPUT IS DANGEROUS.DO SOMETHING WITH WEIRD INPUT.
        //TODO: OH AND FIND A WAY TO LIST AND DELETE THE TASKS
    }

    private void createSuccess(String message){
        AlertDialog alertDialog = new AlertDialog.Builder(AddTask.this).create();
        alertDialog.setTitle("Success!");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });
        alertDialog.show();
    }

    private void createAlert(String message){
        //Snackbar.make(getCurrentFocus(), message, Snackbar.LENGTH_LONG).setAction("Action", null).show();
        //Maybe try with fragments //http://developer.android.com/guide/topics/ui/dialogs.html
        AlertDialog alertDialog = new AlertDialog.Builder(AddTask.this).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

        /**
         *                 Snackbar.make(view, "Please enter valid activity name", Snackbar.LENGTH_LONG)
         .setAction("Action", null).show();
         */
    }
}
