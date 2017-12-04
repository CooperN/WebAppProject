package com.example.msis4363.pre_reqapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LoginSplashScreen extends AppCompatActivity {
    Integer studentId;

    // Declaring connection variables
    public Connection con;
    String un,pass,db,ip, fname, newText;
    public TextView message;
    public ProgressBar progressBar;

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash_screen);
        studentId = getIntent().getIntExtra("studentid", 0);

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(getApplicationContext(), Main.class);
                i.putExtra("studentid", studentId);

                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);



        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";

        LoginSplashScreen.CheckLogin checkLogin = new LoginSplashScreen.CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute("");
    }

    public class CheckLogin extends AsyncTask<String,String,String>
    {
        String z = "";
        Boolean isSuccess = false;

        protected void onPreExecute()
        {
            progressBar.setVisibility(View.GONE);
        }
        @Override
        protected void onPostExecute(String r)
        {
            progressBar.setVisibility(View.GONE);
            if(isSuccess)
            {
                TextView t=(TextView)findViewById(R.id.textView4);
                newText = "Welcome "+fname;
                t.setText(newText);
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
                    String query = "select fname from Student where studentid = '" + studentId + "';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if(rs.next())
                    {
                        fname = rs.getString("fname"); //Name is the string label of a column in database, read through the select query
                    }
                    isSuccess = true;
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
