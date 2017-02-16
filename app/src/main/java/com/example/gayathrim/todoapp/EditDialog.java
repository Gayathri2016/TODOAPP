package com.example.gayathrim.todoapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by gayathrimahamkali on 2/15/17.
 */

public class EditDialog extends DialogFragment implements View.OnClickListener {
    private EditText mEditText;
    Button mButton;
    onSubmitListener mListener;
    String text = "";

    onSubmitListener mlistner;

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.button1) {
            //Toast.makeText(getActivity(), "button clicked", Toast.LENGTH_LONG).show();
            mListener.setOnSubmitListener(mEditText.getText().toString());
            dismiss();
        }
    }

    // 1. Defines the listener interface with a method passing back data result.
    interface onSubmitListener {
        void setOnSubmitListener(String arg);
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

        mButton = (Button)view.findViewById(R.id.button1);

        mEditText.setText(text);
        mButton.setOnClickListener(this);



    }





}
