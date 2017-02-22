package com.example.gayathrim.todoapp;

/**
 * Created by gayathrimahamkali on 2/20/17.
 */

public class Task {

        String _name;
        String _date;

        public Task(){}

    public Task(String name, String joiningDate)
    {
        _name = name;
        _date = joiningDate;
    }
    public String string()
    {
        return this._name;
    }
    public String get_name() {
        return this._name;
    }
    public  String get_date()
    {
        return  this._date;
    }


}
