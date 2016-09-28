package com.example.gayathrim.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.example.gayathrim.simpletodo.R.id.lvItems;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> todoItems;
    ArrayAdapter<String> aToDOAdapter;
    ListView Items;
    EditText etEditText;
    private final int REQUEST_CODE = 20;
    String name;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        Items = (ListView) findViewById(lvItems);
        Items.setAdapter(aToDOAdapter);
        etEditText = (EditText) findViewById(R.id.etEditText);

        onActivityResult(REQUEST_CODE, RESULT_OK, getIntent());

        Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                todoItems.remove(position);
                aToDOAdapter.notifyDataSetChanged();
                writeItems();
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
                extras.putSerializable("position", position);
                extras.putSerializable("obj_to_pass", selectedFromList);
                editScreen.putExtras(extras);
                todoItems.remove(position);
                aToDOAdapter.notifyDataSetChanged();
                writeItems();
                startActivity(editScreen);
            }


        });

    }
    public void onClick(View view) {
        Intent i = new Intent(MainActivity.this, editActivity.class);
        i.putExtra("mode", 2); // pass arbitrary data to launched activity
        startActivityForResult(i, REQUEST_CODE);
    }

    public void populateArrayItems() {
        readItems();
        aToDOAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo1.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        } catch (IOException e) {
            todoItems = new ArrayList<String>();
        }

    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo1.txt");
        try {
            FileUtils.writeLines(file, todoItems);
        } catch (IOException e) {
            todoItems = new ArrayList<String>();

        }

    }


    public void onAddItem(View view) {
        aToDOAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
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
                todoItems.add(code, name);
                aToDOAdapter.notifyDataSetChanged();
            }
        }
    }








}
