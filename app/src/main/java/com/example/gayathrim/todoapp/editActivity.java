package com.example.gayathrim.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

public class editActivity extends AppCompatActivity {
    ArrayAdapter<String> aToDOAdapter;
    EditText txt;
    Object my_obj;
    private String name;
    private int position;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Bundle extras = getIntent().getExtras();
        my_obj = extras.getSerializable("obj_to_pass");
        position =(int) extras.getSerializable("position");
        txt=(EditText) findViewById(R.id.etEditText);
       txt.setText((CharSequence) my_obj);


    }

    public void onSaveItem(View view) {

        name  = txt.getText().toString().toUpperCase().trim();
        Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
        mainScreen.putExtra("name", txt.getText().toString());
        mainScreen.putExtra("code", 200);
        mainScreen.putExtra("position", position );
        setResult(RESULT_OK, mainScreen);
        startActivity(mainScreen);
        finish();



    }

}
