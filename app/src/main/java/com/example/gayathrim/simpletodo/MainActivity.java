package com.example.gayathrim.simpletodo;

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

    ArrayList<String>todoItems;
    ArrayAdapter<String> aToDOAdapter;
    ListView Items;
    EditText etEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        Items = (ListView)findViewById(lvItems);
        Items.setAdapter(aToDOAdapter);
        etEditText = (EditText)findViewById(R.id.etEditText);
        Items.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id){
                todoItems.remove(position);
                aToDOAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }

        });


    }
    public  void populateArrayItems(){
       readItems();
        aToDOAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }
    private void readItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(file));
        }catch(IOException e){
            todoItems = new ArrayList<String>();
        }

    }
    private void writeItems() {
        File filesDir = getFilesDir();
        File file = new File(filesDir, "todo.txt");
        try {
           FileUtils.writeLines(file, todoItems);
        }catch(IOException e){

        }

    }


    public void onAddItem(View view) {
        aToDOAdapter.add(etEditText.getText().toString());
        etEditText.setText("");
        writeItems();
    }
}
