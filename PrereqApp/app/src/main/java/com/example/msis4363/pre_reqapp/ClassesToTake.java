package com.example.msis4363.pre_reqapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.transform.Result;

/**
 * Created by Cooper on 11/7/2017.
 */

public class ClassesToTake extends AppCompatActivity {

    public Connection con;
    public TextView message;
    public Button run;
    //public ProgressBar progressBar;
    private ArrayList<String> arrayListToDo;
    private ArrayAdapter<String> arrayAdapterToDo;

    private String selectedChoice = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_to_take);
        arrayListToDo = new ArrayList<String>();
        arrayAdapterToDo = new ArrayAdapter<String>(this, R.layout.row, R.id.row, arrayListToDo);
        final ListView listView = (ListView) findViewById(R.id.classesToTakeList);

        listView.setAdapter(arrayAdapterToDo);


        run = (Button) findViewById(R.id.run);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        run.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });



    }

    public class CheckLogin extends AsyncTask<String,String,ArrayList<String>>
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

                    String query = "select * from Course";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    names = new ArrayList<String>();
                    while (rs.next()){
                        String cname = rs.getString("name"); //Name is the string label of a column in database, read through the select query
                        String cnum = rs.getString("number");
                        String title = cname + " " + cnum;

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

    public void castSelectedItem (View v) {
        ListView listView = (ListView) findViewById(R.id.classesToTakeList);
        ArrayList<String> toSend = new ArrayList<String>();
        String a ="";
        //this will loop through each item in the list and checks if they are selected.
        for(int i=0 ; i<arrayAdapterToDo.getCount() ; i++){
            if (listView.isItemChecked(i)){

                a = a + arrayListToDo.get(i);
                toSend.add(arrayListToDo.get(i));

            }
        }
        Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent (this, FinishedCourses.class);
        //this is how you send multiple strings to next activity
        intent.putStringArrayListExtra("toSend", toSend);
        startActivity(intent);

    }

}
