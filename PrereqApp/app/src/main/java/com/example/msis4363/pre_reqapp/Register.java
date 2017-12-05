package com.example.msis4363.pre_reqapp;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends AppCompatActivity implements OnItemSelectedListener {

    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip;
    String major;
    Integer majorid;
    public Button insert;
    public ProgressBar progressBar2;
    public TextView message;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Spinner spinner = (Spinner) findViewById(R.id.registrationspinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.majors_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // End Getting values from button, texts and progress bar
        insert = (Button) findViewById(R.id.Register);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        // Declaring Server ip, username, database name and password
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";
        // Declaring Server ip, username, database name and password

        spinner.setOnItemSelectedListener(this);

        insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Insert Insert = new Insert();// this is the Asynctask, which is used to process in background to reduce load on app process
                Insert.execute("");
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        major = parent.getItemAtPosition((position)).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    /*@Override
    public void SpinnerActivity extends Activity implements AdapterView.OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            // An item was selected. You can retrieve the selected item using
            // parent.getItemAtPosition(pos)
            major = parent.getItemAtPosition((pos)).toString();
        }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
        major = "MSIS";
    }
}*/

    public class Insert extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";

        EditText a = (EditText) findViewById(R.id.registerUser);
        String userstr = a.getText().toString();
        EditText c = (EditText) findViewById(R.id.registerFirstName);
        String fnamestr = c.getText().toString();
        EditText d = (EditText) findViewById(R.id.registerLastName);
        String lnamestr = d.getText().toString();

        EditText b = (EditText) findViewById(R.id.registerPass);
        String passstr = b.getText().toString();


        protected void onPreExecute()
        {
            progressBar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar2.setVisibility(View.GONE);
            if(isSuccess)
            {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }


        @Override
        protected String doInBackground(String... params)
        {
            if(major.equals("MSIS")){
                majorid = 1;
            }
            else if(major.equals("MKTG")){
                majorid = 3;
            }
            else if(major.equals("MGMT")){
                majorid = 2;
            }
            else if(major.equals("")){
                z = "Please select a major";
                return z;
            }
            else {
                z = "Please select a different major";
                return z;
            }
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
                    String query = "INSERT INTO Student VALUES ('" + userstr + "', '" + passstr + "', '" + fnamestr + "', '" + lnamestr + "', '" + majorid + "');";
                    query = query + "INSERT INTO StudentDegree VALUES((SELECT studentid FROM Student WHERE username = '" + userstr + "'), '" + majorid + "');";
                    // '" + pidstr + "'

                    Statement stmt = con.createStatement();
                    stmt.executeUpdate(query);
                    name1 = "Insert sucessful";
                    z = "Sucessful Registration";
                    isSuccess=true;
                    con.close();
                    Intent intent = new Intent(getApplicationContext(), Login.class);
                    startActivity(intent);
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
