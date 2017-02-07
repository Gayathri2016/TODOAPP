package com.example.gayathrim.TODOAPP;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import static com.example.gayathrim.TODOAPP.R.id.lvItems;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDOAdapter;
    ListView Items;
    EditText etEditText;
    private final int REQUEST_CODE = 20;
    String name;
    private TaskListDbHelper dbHelper;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new TaskListDbHelper(this);
        Items = (ListView) findViewById(lvItems);

        etEditText = (EditText) findViewById(R.id.etEditText);



        onActivityResult(REQUEST_CODE, RESULT_OK, getIntent());

        Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item = (String)(Items.getItemAtPosition(position));
                todoItems.remove(position);
                aToDOAdapter.notifyDataSetChanged();
                deleteTask(item);

                return true;
            }

        });


        Items.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                Intent editScreen = new Intent(getApplicationContext(), editActivity.class);
                String selectedFromList = (String) (Items.getItemAtPosition(position));
                Bundle extras = new Bundle();
                deleteTask(selectedFromList);
                extras.putSerializable("position", position);
                extras.putSerializable("obj_to_pass", selectedFromList);
                editScreen.putExtras(extras);
               // todoItems.remove(position);
                aToDOAdapter.notifyDataSetChanged();
               // writeItems();
                startActivity(editScreen);
            }


        });
        readItems();

    }
    public void deleteTask(String name)
    {

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TaskListDB.TaskEntry.TABLE,
                    TaskListDB.TaskEntry.COL_TASK_NAME + " = ?",
                    new String[]{name});
            db.close();
            readItems();
        }catch(Exception e) {

        }
    }
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, editActivity.class);
        i.putExtra("mode", 2); // pass arbitrary data to launched activity
        startActivityForResult(i, REQUEST_CODE);
    }


    private void readItems() {
       // File filesDir = getFilesDir();
       // File file = new File(filesDir, "todo1.txt");
        todoItems = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM tasks"  ;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    int idx = cursor.getColumnIndex(TaskListDB.TaskEntry.COL_TASK_NAME);
                    todoItems.add(cursor.getString(idx));
                }while (cursor.moveToNext());
            }
            if(aToDOAdapter == null) {
                aToDOAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
                Items.setAdapter(aToDOAdapter);
               // aToDOAdapter.notifyDataSetChanged();
            }
            else {
                aToDOAdapter.clear();
                aToDOAdapter.addAll(todoItems);
                aToDOAdapter.notifyDataSetChanged();
            }
            //todoItems = new ArrayList<String>(FileUtils.readLines(file));
            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
            todoItems = new ArrayList<String>();
        }finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    private void writeItems() {
        //File filesDir = getFilesDir();
       // File file = new File(filesDir, "todo1.txt");


        try {
            //FileUtils.writeLines(file, todoItems);
            String task = String.valueOf(etEditText.getText().toString());
            dbHelper = new TaskListDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TaskListDB.TaskEntry.COL_TASK_NAME, task);
            db.insertWithOnConflict(TaskListDB.TaskEntry.TABLE,
                    null,
                    cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            readItems();
        } catch (Exception e) {
            todoItems = new ArrayList<String>();

        }

    }

    public void updateTask(String name, int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues values = new ContentValues();
           // values.put(TaskListDB.TaskEntry._ID, id);
            values.put(TaskListDB.TaskEntry.COL_TASK_NAME, name);
            db.insertWithOnConflict(TaskListDB.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            readItems();
        }catch (Exception e) {

        }
    }
    public void onAddItem(View view) {
        //aToDOAdapter.add(etEditText.getText().toString());
        //String task = String.valueOf(etEditText.getText().toString());
        //etEditText.setText("");
        writeItems();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data.getExtras() != null) {
            // Extract name value from result extras
            name = data.getExtras().getString("name");
           int code =  data.getExtras().getInt("position");
            if(!name.isEmpty()) {
                updateTask(name, code);
                //todoItems.add(code, name);
                aToDOAdapter.notifyDataSetChanged();
            }
        }
    }








}
