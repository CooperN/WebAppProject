package com.example.msis4363.pre_reqapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by Cooper on 11/6/2017.
 */

public class Database {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "username";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_PASSWORD = "password";

    SQLiteDatabase db;

    private static final String TABLE_CREATE = "create table contacts (id integer primary key not null, username text not null, password text not null, firstname not null, lastname not null);";

    //this is the constructor method
    public Helper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //insert query method
    public void insertContact (Contact c) {
        db = this.getWritableDatabase();
        //store values from different fields
        ContentValues values = new ContentValues();

        //auto increment id
        String query = "Select * from contacts";
        Cursor cursor = db.rawQuery(query, null);
        int count = cursor.getCount();

        values.put(COLUMN_ID, count);
        values.put(COLUMN_NAME, c.getUsername());
        values.put(COLUMN_PASSWORD, c.getPassword());
        values.put(COLUMN_FIRSTNAME, c.getFirstName());
        values.put(COLUMN_LASTNAME, c.getLastName());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    //method for looking up the password - basic structure of a select query method
    public String searchPass (String username) {
        db = this.getReadableDatabase();
        String query = "select username, password from"+TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        String a,b;
        b = "not found";
        if (cursor.moveToFirst()){
            do{
                a = cursor.getString(0);
                if (a.equals(username))
                {
                    b = cursor.getString(1);
                    break;
                }
            }
            while (cursor.moveToNext());


        }
        return b;
    }

    public User getContact(String username) {
        User c = new User();
        db = this.getReadableDatabase();
        String query = "select username, password, firstname, lastname from"+TABLE_NAME + "where username = "+username;
        Cursor cursor = db.rawQuery(query, null);
        String u,p,f,l;
        cursor.moveToFirst();
        u = cursor.getString(0);
        p = cursor.getString(1);
        f = cursor.getString(2);
        l = cursor.getString(3);

        c.setUsername(u);
        c.setPassword(p);
        c.setFirstName(f);
        c.setLastName(l);

        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //drop query
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        sqLiteDatabase.execSQL(query);
        this.onCreate(sqLiteDatabase);

    }
}
