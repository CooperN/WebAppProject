package com.example.msis4363.pre_reqapp;

import android.os.AsyncTask;
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
import java.sql.ResultSet;
import java.sql.Statement;

public class Login extends AppCompatActivity {

    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // End Getting values from button, texts and progress bar

        // Declaring Server ip, username, database name and password
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";
        // Declaring Server ip, username, database name and password

        // Setting up the function when button login is clicked
        run.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();
                checkLogin.execute("");
            }
        });
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String name1 = "";


        protected void onPreExecute()
        {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(MainActivity.this, r, Toast.LENGTH_LONG).show();
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
    }
}
