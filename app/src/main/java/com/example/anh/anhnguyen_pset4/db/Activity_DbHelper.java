package com.example.anh.anhnguyen_pset4.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.os.FileObserver.CREATE;

/**
 * Created by Anh on 22-11-2016.
 */

public class Activity_DbHelper extends SQLiteOpenHelper {
    public Activity_DbHelper(Context context){
        super (context, Activity_Task.DB_NAME, null, Activity_Task.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + Activity_Task.TaskEntry.TABLE + " ( " +
                Activity_Task.TaskEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                Activity_Task.TaskEntry.TASK_TITLE + " TEXT NOT NULL);";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Activity_Task.TaskEntry.TABLE);
        onCreate(db);
    }

}
