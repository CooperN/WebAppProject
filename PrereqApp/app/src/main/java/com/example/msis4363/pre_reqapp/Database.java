package com.example.msis4363.pre_reqapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Created by Cooper on 11/6/2017.
 */


public class Database extends SQLiteOpenHelper {
public class Database extends AppCompatActivity {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NAME = "contacts.db";
    private static final String TABLE_NAME = "contacts";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "username";
    private static final String COLUMN_FIRSTNAME = "firstname";
    private static final String COLUMN_LASTNAME = "lastname";
    private static final String COLUMN_PASSWORD = "password";

    SQLiteDatabase db;
    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip;

    private static final String TABLE_CREATE = "create table contacts (id integer primary key not null, username text not null, password text not null, firstname not null, lastname not null);";

    //this is the constructor method
    public Helper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        // End Getting values from button, texts and progress bar

        // Declaring Server ip, username, database name and password
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";
        // Declaring Server ip, username, database name and password


    //insert query method
    public void insertContact (User c) {
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
    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected String doInBackground(String... params)
        {

            try
            {
                con = connectionclass();        // Connect to database
                if (con == null)
                {
                    z = "Check Your Internet Access!";
                }
                else
                {
                    // Change below query according to your own database.
                    String query = "select * from NeilAllen";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        name1= rs.getString("LName"); //Name is the string label of a column in database, read through the select query
                        z = "query successful";
                        isSuccess=true;
                        con.close();

                    }
                    else
                    {
                        z = "Invalid Query!";
                        isSuccess = false;
                    }
                }
            }
            catch (Exception ex)
            {
                isSuccess = false;
                z = ex.getMessage();

                Log.d ("sql error", z);
            }

            return z;
        }
    public Contact getContact(String username) {
        Contact c = new Contact();
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

    public Connection connectionclass()
    {
        //almost always the same
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //your database connection string goes below
            ConnectionURL = "jdbc:jtds:sqlserver://huckleberries.database.windows.net:1433;DatabaseName=PrereqDB;user=hberryadmin@huckleberries;password=Equifax1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (SQLException se)
        {
            Log.e("error here 1 : ", se.getMessage());
        }
        catch (ClassNotFoundException e)
        {
            Log.e("error here 2 : ", e.getMessage());
        }
        catch (Exception e)
        {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;
    }
}
