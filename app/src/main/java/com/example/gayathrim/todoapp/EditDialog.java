package com.example.gayathrim.todoapp;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by gayathrimahamkali on 2/15/17.
 */

public class EditDialog extends DialogFragment implements OnClickListener {
    private EditText mEditText;
    Button mButton;
    onSubmitListener mListener;
    String text = "";
    String date= "";
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog fromDatePickerDialog;
    onSubmitListener mlistner;
    EditText mdate;

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button1) {
            //Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_LONG).show();
            mListener.setOnSubmitListener(mEditText.getText().toString(), mdate.getText().toString());
            dismiss();
        }else if(view == mdate) {
            fromDatePickerDialog.show();
        }else if(view.getId() == R.id.btncancel)
        {
            mListener.setOnSubmitListener(text, date);
            dismiss();
        }
    }

    // 1. Defines the listener interface with a method passing back data result.
    interface onSubmitListener {
        void setOnSubmitListener(String arg, String date);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        mlistner = (onSubmitListener) activity;

    }
    public static EditDialog newInstance(String title) {
        EditDialog frag = new EditDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.edit_dialog, null);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.editText);
        ((TextView)mEditText).setText(getArguments().getString("edttext"));
        mdate = (EditText) view.findViewById(R.id.etDate);
        ((TextView)mdate).setText(getArguments().getString("edtDate"));

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        mButton = (Button)view.findViewById(R.id.button1);

        mEditText.setText(text);
        mdate.setText(date);

        mButton.setOnClickListener(this);

       setDateTimeField(view);

    }


    private void setDateTimeField(View view) {
        mdate = (EditText) view.findViewById(R.id.etDate);
        mdate.setInputType(InputType.TYPE_NULL);
        mdate.setOnClickListener(this);
        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this.getContext(), new OnDateSetListener() {

            public void onDateSet(DatePicker dp, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                mdate.setText(dateFormatter.format(newDate.getTime()));
            }
        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
    }


}
