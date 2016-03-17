package com.example.fii.whatsnext.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.fii.whatsnext.R;
import com.example.fii.whatsnext.activities.MainActivity;
import com.example.fii.whatsnext.database.TaskStorageContract.*;
import com.example.fii.whatsnext.model.Task;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by fii on 16.03.16.
 */
public class DBAccess {
    private TaskStorageOpenHelper helper;
    private Context context;

    public DBAccess(Context context){
        helper = new TaskStorageOpenHelper(context);
        this.context = context;
    }

    public List<Task> getTasks(int taskLength){
        SQLiteDatabase db = helper.getReadableDatabase();
        String[] selectionArgs = new String[2];
        List<Task> tasks = new LinkedList<>();
        switch(taskLength) {
            case (MainActivity.SHORT): {
                selectionArgs[1] = context.getString(R.string.duration_short);
                selectionArgs[0] = "0";
                break;
            }
            case (MainActivity.MED): {
                selectionArgs[0] = context.getString(R.string.duration_short);
                selectionArgs[1] = context.getString(R.string.duration_med);
                break;
            }
            case (MainActivity.LONG): {
                selectionArgs[0] = context.getString(R.string.duration_med);
                selectionArgs[1] = context.getString(R.string.duration_long);
                break;
            }
        }
        String selection = TaskEntry.COLUMN_NAME_DURATION + " BETWEEN ?" + " AND ? " ;
        String[] projection = {
                TaskEntry._ID,
                TaskEntry.COLUMN_NAME_NAME,
                TaskEntry.COLUMN_NAME_DURATION};

        String sortOrder = TaskEntry.COLUMN_NAME_DURATION + " DESC";
        Cursor c = db.query(TaskEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        c.moveToFirst();
        int durationIndex = c.getColumnIndex(TaskEntry.COLUMN_NAME_DURATION);
        int idIndex = c.getColumnIndex(TaskEntry._ID);
        int nameIndex = c.getColumnIndex(TaskEntry.COLUMN_NAME_NAME);
        while(!c.isAfterLast()){
            Task t = new Task();
            t.duration = c.getInt(durationIndex);
            t.ID = c.getInt(idIndex);
            t.name = c.getString(nameIndex);
            c.moveToNext();
            tasks.add(t);
        }


        return tasks;
    }

    public void addTask(Task task){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TaskEntry.COLUMN_NAME_NAME, task.name);
        values.put(TaskEntry.COLUMN_NAME_DURATION, task.duration);
        db.insert(TaskEntry.TABLE_NAME, TaskEntry.COLUMN_NAME_DURATION, values);
    }
}
