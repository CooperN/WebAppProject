package com.example.msis4363.pre_reqapp;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Cooper on 12/4/2017.
 */

public class Database {
    // Declaring connection variables
    private Connection con;
    private String un = "hberryadmin";
    private String pass = "Equifax1";
    private String db = "PrereqDB";
    private String ip = "huckleberries.database.windows.net:1433";
    private String SQLquery;
    ResultSet rs;

    private class selectSQL extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String un = "";
        String pw = "";

        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
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
                    String query = SQLquery;
                    Statement stmt = con.createStatement();
                    rs = stmt.executeQuery(query);
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

    public ResultSet select(String query) {
        SQLquery = query;
        Database.selectSQL selectSQL = new Database.selectSQL();// this is the Asynctask, which is used to process in background to reduce load on app process
        selectSQL.execute("");
        return rs;
    }
    public User getUserInfo(Integer userId){
        User student = new User();


        return student;
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
