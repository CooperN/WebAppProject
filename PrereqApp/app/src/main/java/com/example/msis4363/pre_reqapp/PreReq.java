package com.example.msis4363.pre_reqapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;

public class PreReq extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    public Connection con;
    public TextView message;


    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;
    private ArrayList<String> arrayListToDo2;
    private ArrayAdapter<String> arrayAdapterToDo2;
    private String selectedChoice = "";
    ArrayList<Integer> ids = null;
    Integer studentId;
    String selectedCourse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_req);
        studentId = getIntent().getIntExtra("studentid", 0);
        arrayListToDo = new ArrayList<String>();
        arrayAdapterToDo = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, arrayListToDo);
        final ListView listView = (ListView) findViewById(R.id.classesToTakeList);

        PreReq.CheckLogin checkLogin = new PreReq.CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute("");

        Spinner spinner = (Spinner) findViewById(R.id.prereqspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        //ArrayAdapter<String> arrayAdapterToDo = ArrayAdapter.createFromResource(this,
               // R.array.majors_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        arrayAdapterToDo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(arrayAdapterToDo);

        arrayListToDo2 = new ArrayList<String>();
        arrayAdapterToDo2 = new ArrayAdapter<String>(this, R.layout.row, R.id.row, arrayListToDo2);
        final ListView listView2 = (ListView) findViewById(R.id.prereqList);
        listView2.setAdapter(arrayAdapterToDo2);

        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedCourse = parent.getItemAtPosition((position)).toString();

        PreReq.CheckHavent checkhavent = new PreReq.CheckHavent();
        checkhavent.execute("");
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class CheckLogin extends AsyncTask<String,String,ArrayList<String>>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";
        String user = getIntent().getStringExtra("username");


        protected void onPreExecute()
        {

            //progressBar.setVisibility(View.VISIBLE);



        }

        @Override
        protected void onPostExecute(ArrayList<String> r)
        {
            //note the input type of the onPostExecute is changed into ArrayList<String>, which is the output from doInBackGround method
            //progressBar.setVisibility(View.GONE);
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_LONG).show();
            //iterate through the arrayList and write it into your
            Iterator<String> iterator = r.iterator();
            while (iterator.hasNext()) {
                arrayAdapterToDo.add(iterator.next().toString());
            }

            if(isSuccess)
            {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... params)
        {
            ArrayList<String> names = null;
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
                    String query = "SELECT * FROM Course WHERE courseid IN (Select courseid from ProgramRequirement WHERE programid IN (Select programid FROM StudentDegree WHERE studentid = " + studentId + ")) AND courseid NOT IN (SELECT coursid FROM CoursesTaken WHERE studentid = " + studentId + ") AND courseid IN (Select courseid from PreReq);";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    names = new ArrayList<String>();
                    while (rs.next()){
                        String title = rs.getString("name"); //Name is the string label of a column in database, read through the select query


                        names.add(title);
                    }

                    if (rs.next()) {
                        name1 = rs.getString("Name"); //Name is the string label of a column in database, read through the select query
                        z = "query successful";
                        isSuccess = true;
                        con.close();

                    } else {
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
            return names;
        }
    }

    public class CheckHavent extends AsyncTask<String,String,ArrayList<String>>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";

        protected void onPreExecute()
        {

            //progressBar.setVisibility(View.VISIBLE);



        }

        @Override
        protected void onPostExecute(ArrayList<String> r)
        {
            arrayAdapterToDo2.clear();
            //note the input type of the onPostExecute is changed into ArrayList<String>, which is the output from doInBackGround method
            //progressBar.setVisibility(View.GONE);
            //Toast.makeText(MainActivity.this, r, Toast.LENGTH_LONG).show();
            //iterate through the arrayList and write it into your
            Iterator<String> iterator = r.iterator();
            while (iterator.hasNext()) {
                arrayAdapterToDo2.add(iterator.next().toString());
            }

            if(isSuccess)
            {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }

        @Override
        protected ArrayList<String> doInBackground(String... params)
        {
            ArrayList<String> names2 = null;
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

                    String query = "SELECT * FROM Course WHERE courseid IN (SELECT prereqid FROM PreReq WHERE courseid = (SELECT courseid FROM Course where name = '" + selectedCourse + "')) ;";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    names2 = new ArrayList<String>();
                    ids = new ArrayList<Integer>();
                    while (rs.next()){
                        String title = rs.getString("name"); //Name is the string label of a column in database, read through the select query

                        Integer courseid = rs.getInt("courseid");

                        names2.add(title);
                        ids.add(courseid);
                    }




                    if (rs.next()) {
                        name1 = rs.getString("Name"); //Name is the string label of a column in database, read through the select query
                        z = "query successful";
                        isSuccess = true;
                        con.close();

                    } else {
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
            return names2;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass()
    {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try
        {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //your database connection string goes below
            ConnectionURL = "jdbc:jtds:sqlserver://huckleberries.database.windows.net:1433;DatabaseName=PrereqDB;user=hberryadmin@huckleberries;password=Equifax1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            //ConnectionURL = "jdbc:jtds:sqlserver://huckleberries.database.windows.net:1433;DatabaseName=PrereqDB;user=hberryadmin@huckleberries;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
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
