package com.example.anh.anhnguyen_pset4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anh.anhnguyen_pset4.R;
import com.example.anh.anhnguyen_pset4.db.Activity_DbHelper;
import com.example.anh.anhnguyen_pset4.db.Activity_Task;

import java.util.ArrayList;

import static android.R.attr.duration;
import static com.example.anh.anhnguyen_pset4.R.id.ItemList;
import static com.example.anh.anhnguyen_pset4.R.id.addItem;
import static com.example.anh.anhnguyen_pset4.R.id.task_title;

public class MainActivity extends AppCompatActivity {
    private Activity_DbHelper mHelper;
    private ListView TaskListView;
    private ArrayAdapter<String> mAdapter;
    //private ListView ItemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHelper = new Activity_DbHelper(this);
        TaskListView = (ListView) findViewById(ItemList);

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(Activity_Task.TaskEntry.TABLE,
                new String[]{Activity_Task.TaskEntry._ID, Activity_Task.TaskEntry.TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(Activity_Task.TaskEntry.TASK_TITLE);
            Log.d("HALLO: ", cursor.getString(idx));

        }
        cursor.close();
        db.close();
        updateUI();
    }

    public void onAddItem(View view) {
        EditText addItem = (EditText) findViewById(R.id.addItem);
        String task = String.valueOf(addItem.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Activity_Task.TaskEntry.TASK_TITLE, task);
        db.insertWithOnConflict(Activity_Task.TaskEntry.TABLE,
                null,
                values,
                SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
        updateUI();
        addItem.setText("");
    }

    public void delete_Task(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(Activity_Task.TaskEntry.TABLE,
                Activity_Task.TaskEntry.TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

    public void deleteTask(View view){
        view.setOnLongClickListener(new View.OnLongClickListener(){
                                        @Override
                                        public boolean onLongClick(    View view){
                                            View parent = (View) view.getParent();
                                            TextView taskTextView = (TextView) parent.findViewById(R.id.task_title);
                                            String task = String.valueOf(taskTextView.getText());
                                            SQLiteDatabase db = mHelper.getWritableDatabase();
                                            db.delete(Activity_Task.TaskEntry.TABLE,
                                                    Activity_Task.TaskEntry.TASK_TITLE + " = ?",
                                                    new String[]{task});
                                            db.close();
                                            updateUI();
                                            Toast.makeText(getBaseContext(),"  ' "+task +" '  " + " is deleted",
                                                    Toast.LENGTH_LONG).show();



                                            return true;
                                        }
                                    }
        );
    }
    public void selectItem(View view){
        TextView background = (TextView) findViewById(R.id.task_title);
        CheckBox checkDone = (CheckBox) findViewById(R.id.checkBox);
        //boolean checked = ((CheckBox) view).isChecked();

        if(checkDone.isChecked())//checked then
        {
            background.setBackgroundColor(0xff00ff00);
        }
        else
        {
            background.setBackgroundColor(0xFFFFFF);
        }





    }




    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(Activity_Task.TaskEntry.TABLE,
                new String[]{Activity_Task.TaskEntry._ID, Activity_Task.TaskEntry.TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(Activity_Task.TaskEntry.TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.todo,
                    R.id.task_title,
                    taskList);
            TaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();
    }

    //makes menu bigger
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    }

