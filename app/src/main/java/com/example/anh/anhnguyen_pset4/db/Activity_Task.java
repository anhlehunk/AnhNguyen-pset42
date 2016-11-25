package com.example.anh.anhnguyen_pset4.db;
import android.provider.BaseColumns;

/**
 * Created by Anh on 22-11-2016.
 */

public class Activity_Task {
    public static final String DB_NAME = "com.example.anh.anhnguyen_pset4.db";
    public static final int DB_VERSION = 1;

    public class TaskEntry implements BaseColumns {
        public static final String TABLE = "tasks";

        public static final String TASK_TITLE = "TITLE";
    }
}
