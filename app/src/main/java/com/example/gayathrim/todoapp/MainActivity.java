package com.example.gayathrim.todoapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.gayathrim.todoapp.EditDialog.onSubmitListener;
import com.example.gayathrim.todoapp.TaskListDB.TaskEntry;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.gayathrim.todoapp.R.id.lvItems;

public class MainActivity extends AppCompatActivity implements onSubmitListener {

    ArrayList<Task> todoItems;
    ArrayAdapter<Task> aToDOAdapter;
    ListView Items;
    EditText etEditText;
    EditText etDate;
    private final int REQUEST_CODE = 20;
    String name;
    String date;
    private TaskListDbHelper dbHelper;
    private static final String TAG = "MainActivity";
    private  Task todoTask;



    private SimpleDateFormat dateFormatter;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new TaskListDbHelper(this);

        Items = (ListView) findViewById(lvItems);

        etEditText = (EditText) findViewById(R.id.etEditText);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        onActivityResult(REQUEST_CODE, RESULT_OK, getIntent());

        Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String item = aToDOAdapter!=null? aToDOAdapter.getItem(position).get_name():"";
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

                showEditDialog(position);


            }


        });
        readItems();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        updateMenuItems(menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add_task:
                Intent add = new Intent(getApplicationContext(), AddActivity.class);
                startActivity(add);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void deleteTask(String name) {

        try {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TaskListDB.TaskEntry.TABLE,
                    TaskListDB.TaskEntry.COL_TASK_NAME + " = ?",
                    new String[]{name});
            db.close();
            readItems();
        } catch (Exception e) {

        }
    }

    private void readItems() {


        todoItems = new ArrayList<Task>();
        String selectQuery = "SELECT  * FROM tasks";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        try {
            if (cursor.moveToFirst()) {
                do {

                    int idx = cursor.getColumnIndex(TaskEntry.COL_TASK_NAME);
                    int idx1 = cursor.getColumnIndex(TaskEntry.COL_TASK_Date);
                    todoItems.add(new Task(cursor.getString(idx), cursor.getString(idx1)));



                } while (cursor.moveToNext());

            }

            if (aToDOAdapter == null) {
                aToDOAdapter = new com.example.gayathrim.todoapp.ListAdapter(this, R.layout.taskitemlistrow, todoItems);

                Items.setAdapter(aToDOAdapter);

            } else {
                aToDOAdapter.clear();
                aToDOAdapter.addAll(todoItems);
                aToDOAdapter.notifyDataSetChanged();
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            Log.d("TAG", "Error while trying to get posts from database");
            todoItems = new ArrayList<Task>();
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }

    }

    private void writeItems(String item) {



        try {

            dbHelper = new TaskListDbHelper(this);
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();
            cv.put(TaskListDB.TaskEntry.COL_TASK_NAME, item);
            db.insertWithOnConflict(TaskListDB.TaskEntry.TABLE,
                    null,
                    cv,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            readItems();
        } catch (Exception e) {
            todoItems = new ArrayList<Task>();

        }

    }

    public void updateTask(String name, String date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try {

            ContentValues values = new ContentValues();

            values.put(TaskListDB.TaskEntry.COL_TASK_NAME, name);
            values.put(TaskListDB.TaskEntry.COL_TASK_Date, date);
            db.insertWithOnConflict(TaskListDB.TaskEntry.TABLE,
                    null,
                    values,
                    SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            readItems();
        } catch (Exception e) {
            throw e;
        }
    }

    public void onAddItem(View view) {

        String task = String.valueOf(etEditText.getText().toString());

        writeItems(task);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // REQUEST_CODE is defined above
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE && data.getExtras() != null) {
            // Extract name value from result extras
            name = data.getExtras().getString("name");
            date = data.getExtras().getString("Date");

            if (!name.isEmpty() && !date.isEmpty()) {
                updateTask(name, date);

                if (aToDOAdapter != null) {
                    aToDOAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    private void showEditDialog(int item) {


        FragmentManager fm = getSupportFragmentManager();
        String name = aToDOAdapter!=null? aToDOAdapter.getItem(item).get_name():"";
        String date = aToDOAdapter!=null? aToDOAdapter.getItem(item).get_date():"";
        Bundle bundle = new Bundle();
        bundle.putString("edttext", name);
        bundle.putString("edtDate", date);
        // set Fragmentclass Arguments
        deleteTask(name);
        EditDialog editDialog = EditDialog.newInstance("Edit Item Below");

        editDialog.mListener = MainActivity.this;
        editDialog.text = name;
        editDialog.date = date;
        editDialog.show(fm, "edit_dialog");
    }

    @Override
    public void setOnSubmitListener(String arg, String date) {
        updateTask(arg, date);

    }

    private void updateMenuItems(Menu menu) {
        if (menu != null) {
            menu.findItem(R.id.action_add_task).setVisible(true);
            menu.findItem(R.id.action_save_task).setVisible(false);
        } else {
            menu.findItem(R.id.action_add_task).setVisible(false);
            menu.findItem(R.id.action_save_task).setVisible(false);
        }
        invalidateOptionsMenu();
    }


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
