package com.example.gayathrim.TODOAPP;

import android.provider.BaseColumns;



/**
 * Created by gayathrimahamkali on 1/6/17.
 */

public class TaskListDB {
    public static final String DB_NAME = "com.example.gayathrim.TODOAPP.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String COL_TASK_NAME = "Name";
    }
}

