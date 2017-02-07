package com.example.gayathrim.TODOAPP;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by gayathrimahamkali on 1/6/17.
 */

public class TaskListDbHelper extends SQLiteOpenHelper {

    public TaskListDbHelper(Context context){
        super(context, TaskListDB.DB_NAME, null, TaskListDB.DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TaskListDB.TaskEntry.TABLE + " ( " +
                TaskListDB.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                TaskListDB.TaskEntry.COL_TASK_NAME + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TaskListDB.TaskEntry.TABLE);
        onCreate(db);
    }
}