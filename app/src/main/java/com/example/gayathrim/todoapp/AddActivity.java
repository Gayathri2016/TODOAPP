package com.example.gayathrim.todoapp;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by gayathrimahamkali on 2/16/17.
 */

public class AddActivity extends AppCompatActivity implements OnClickListener {
    private EditText mEditText;
    private EditText mdate;
    private static final String TAG = "ADDActivity";
    private EditText mDate;
    private SimpleDateFormat dateFormatter;
    Object my_obj;
    private String name;
    private DatePickerDialog fromDatePickerDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Bundle extras = getIntent().getExtras();

        mEditText=(EditText) findViewById(R.id.etEditText);
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);

        setDateTimeField();




    }
    private void setDateTimeField() {
        mdate = (EditText) findViewById(R.id.etDate);
        mdate.setInputType(InputType.TYPE_NULL);
        mdate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mdate.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }
    @Override
    public void onClick(View view) {
        if(view == mdate) {
            fromDatePickerDialog.show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save_task:
                Intent mainScreen = new Intent(getApplicationContext(), MainActivity.class);
                mainScreen.putExtra("name", mEditText.getText().toString());
                mainScreen.putExtra("Date", mdate.getText().toString());
                mainScreen.putExtra("code", 200);
                setResult(RESULT_OK, mainScreen);
                startActivity(mainScreen);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}

