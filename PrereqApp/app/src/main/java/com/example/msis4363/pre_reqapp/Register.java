package com.example.msis4363.pre_reqapp;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Register extends AppCompatActivity {

    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip;

    public Button insert;
    public ProgressBar progressBar2;
    public TextView message;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);



        // End Getting values from button, texts and progress bar
        insert = (Button) findViewById(R.id.Register);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);

        // Declaring Server ip, username, database name and password
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";
        // Declaring Server ip, username, database name and password

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

    public class Insert extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";

        EditText a = (EditText) findViewById(R.id.registerUser);
        String userstr = a.getText().toString();
        EditText b = (EditText) findViewById(R.id.registerPass);
        String passstr = b.getText().toString();
        EditText c = (EditText) findViewById(R.id.registerFirstName);
        String fnamestr = c.getText().toString();
        EditText d = (EditText) findViewById(R.id.registerLastName);
        String lnamestr = d.getText().toString();
        /*Spinner e = (Spinner) findViewById(R.id.spinner);
        String pidstr = e.getSelectedItem().toString();*/

        protected void onPreExecute()
        {
            progressBar2.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar2.setVisibility(View.GONE);
            Toast.makeText(Register.this, r, Toast.LENGTH_LONG).show();
            if(isSuccess)
            {
                message = (TextView) findViewById(R.id.textView2);
                message.setText(name1);

            }
        }


        @Override
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
                    String query = "INSERT INTO Student VALUES ('" + userstr + "', '" + passstr + "', '" + fnamestr + "', '" + lnamestr + "', 1);";
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
