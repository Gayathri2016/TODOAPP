package com.example.gayathrim.simpletodo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String>todoItems;
    ArrayAdapter<String> aToDOAdapter;
    ListView lvItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        populateArrayItems();
        lvItems = (ListView)findViewById(R.id.lvItems);
        lvItems.setAdapter(aToDOAdapter);
    }
    public  void populateArrayItems(){
        todoItems = new ArrayList<String>();
        todoItems.add("Item1");
        todoItems.add("Item2");
        todoItems.add("Item3");
        aToDOAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, todoItems);
    }
}
