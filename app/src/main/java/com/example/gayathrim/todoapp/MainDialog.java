package com.example.gayathrim.todoapp;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

/**
 * Created by gayathrimahamkali on 2/16/17.
 */

public class MainDialog extends DialogFragment {
    private EditText mEditText;
    private DatePicker mdate;
    onSubmitListener mlistener;

    // 1. Defines the listener interface with a method passing back data result.
    interface onSubmitListener {
        void setOnSubmitListener(String arg);
    }

    public void onAttach(Activity activity){
        super.onAttach(activity);
        mlistener = (onSubmitListener) activity;

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_dialog, null);

    }
}

