package com.example.gayathrim.todoapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by gayathrimahamkali on 2/21/17.
 */

public class ListAdapter extends ArrayAdapter<Task> {
    private Context context;
    private int layoutResourceId;
    ArrayList<Task> tasks;

    public ListAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public ListAdapter(Context context, int resource, ArrayList<Task> items) {
        super(context, resource, items);
        this.context = context;
        this.layoutResourceId = resource;
        this.tasks= items;



    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.taskitemlistrow, null);
        }

        Task p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.taskname);
            TextView tt2 = (TextView) v.findViewById(R.id.taskdate);


            if (tt1 != null) {
                tt1.setText(p.get_name());
            }

            if (tt2 != null) {
                tt2.setText(p.get_date());
            }


        }

        return v;
    }

}
