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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;


public class Login extends AppCompatActivity {

    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip;

    public Button run;
    public Button register;
    public TextView message;
    public ProgressBar progressBar;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        // End Getting values from button, texts and progress bar
        run = (Button) findViewById(R.id.btnLogin);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        register = (Button) findViewById(R.id.btnSignUp);


        // Declaring Server ip, username, database name and password
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";
        // Declaring Server ip, username, database name and password

        // Setting up the function when button login is clicked
        /*insert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });*/

        run.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                CheckLogin checkLogin = new CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
                checkLogin.execute("");
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
            }
        });
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;
        String un = "";
        String pw = "";
        Integer sid;
        String name1 = "";

        EditText a = (EditText) findViewById(R.id.userName);
        String userstr = a.getText().toString();
        EditText b = (EditText) findViewById(R.id.userPass);
        String passstr = b.getText().toString();


        protected void onPreExecute()
        {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(Login.this, r, Toast.LENGTH_LONG).show();
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
                    String query = "select username, pw, studentid from Student where username = '" + userstr + "';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        un = rs.getString("username"); //Name is the string label of a column in database, read through the select query
                        pw = rs.getString("pw");
                        sid = rs.getInt("studentid");

                        if(userstr.equals(un) && passstr.equals(pw)){
                            isSuccess = false;
                            con.close();

                            Intent intent = new Intent(getApplicationContext(), Main.class);
                            intent.putExtra("studentid", sid);
                            startActivity(intent);
                            finish();
                        }
                        else {
                            z = "Invalid Login";
                            isSuccess = true;
                            con.close();
                        }
                    }
                    else
                    {
                        z = "User does not exist";
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

    /*public void btnLogin(View v) {
        EditText username = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.userPass);

        String usernamstr = username.getText().toString();
        String passwordstr = password.getText().toString();


        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }*/

    /*public void btnSignUpClick(View v) {
        Intent intent = new Intent(getApplicationContext(), Register.class);
        startActivity(intent);
    }*/
}
