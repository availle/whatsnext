package com.example.fii.whatsnext.database;

import android.provider.BaseColumns;

/**
 * Created by fii on 16.03.16.
 */
public final class TaskStorageContract {
    //To prevent someone from accidentally instantiating the contract class,give it an empty constructor.
    public TaskStorageContract(){}

    public static abstract class TaskEntry implements BaseColumns {
        public static final String TABLE_NAME = "tasks";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_DURATION = "duration";
    }



}


