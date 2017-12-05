package com.example.msis4363.pre_reqapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
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

public class Profile extends AppCompatActivity implements MyListener{
    Integer studentId;

    // Declaring connection variables
    public Connection con;
    String un, pass, db, ip, fname, lname, newText;
    public TextView message;
    public ProgressBar progressBar;
    User currentUser = new User();

    Database database = new Database();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        studentId = getIntent().getIntExtra("studentid", 0);
        database.setDbProcesslistener(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        ip = "huckleberries.database.windows.net:1433";
        db = "PrereqDB";
        un = "hberryadmin";
        pass = "Equifax1";

        database.getUserInfo(studentId);

        Profile.CheckLogin checkLogin = new Profile.CheckLogin();// this is the Asynctask, which is used to process in background to reduce load on app process
        checkLogin.execute("");

        Profile.AddHours addhours = new Profile.AddHours();
        addhours.execute("");

    }

        
    @Override
    public void onEvent(boolean blnProcessIsFinished) {
        if(blnProcessIsFinished) {
            currentUser = database.getUser();
            TextView t1 = (TextView) findViewById(R.id.textViewFname);
            TextView t2 = (TextView) findViewById(R.id.textViewLname);
            TextView t3 = (TextView) findViewById(R.id.textViewUname);
            TextView t4 = (TextView) findViewById(R.id.textViewMajor);
            t1.setText(currentUser.getFname());
            t2.setText(currentUser.getLname());
            t3.setText(currentUser.getUn());
            t4.setText(currentUser.getDegree());
        }
    }

    public class CheckLogin extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String un = "";
        String pw = "";

        protected void onPreExecute() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if (isSuccess) {

            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = connectionclass();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "select * from Student where studentid = '" + studentId + "';";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    if (rs.next()) {
                        un = rs.getString("username"); //Name is the string label of a column in database, read through the select query
                        pw = rs.getString("pw");
                        fname = rs.getString("fname"); //Name is the string label of a column in database, read through the select query
                        lname = rs.getString("lname");
                    }
                    isSuccess = true;
                    con.close();
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
                Log.d("sql error", z);
            }
            return z;
        }
    }

    public class AddHours extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        Integer hleft;
        String pw = "";

        protected void onPreExecute() {
            progressBar.setVisibility(View.GONE);
        }

        @Override
        protected void onPostExecute(String r) {
            progressBar.setVisibility(View.GONE);
            if (isSuccess) {

                TextView hours = (TextView) findViewById(R.id.textViewHLeft);

                hours.setText("Hours Taken: " + String.valueOf(hleft));
            }
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                con = connectionclass();        // Connect to database
                if (con == null) {
                    z = "Check Your Internet Access!";
                } else {
                    // Change below query according to your own database.
                    String query = "Select sum(c_hours) AS HourTotal from Course where courseid IN (Select DISTINCT coursid from CoursesTaken where studentid = " + studentId + ");";
                    Statement stmt = con.createStatement();
                    ResultSet rs = stmt.executeQuery(query);

                    if (rs.next()) {
                        hleft = rs.getInt("HourTotal");
                    }
                    isSuccess = true;
                }
            } catch (Exception ex) {
                isSuccess = false;
                z = ex.getMessage();
                Log.d("sql error", z);
            }
            return z;
        }
    }

    @SuppressLint("NewApi")
    public Connection connectionclass() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            //your database connection string goes below
            ConnectionURL = "jdbc:jtds:sqlserver://huckleberries.database.windows.net:1433;DatabaseName=PrereqDB;user=hberryadmin@huckleberries;password=Equifax1;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            //ConnectionURL = "jdbc:jtds:sqlserver://huckleberries.database.windows.net:1433;DatabaseName=PrereqDB;user=hberryadmin@huckleberries;password={your_password_here};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("error here 1 : ", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("error here 2 : ", e.getMessage());
        } catch (Exception e) {
            Log.e("error here 3 : ", e.getMessage());
        }
        return connection;

    }
}

