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
    private MyListener dbProcessListener;
    private Integer studentId;
    User student = new User();

    boolean doingUser = false;

    public void setDbProcesslistener(MyListener dbProcessListener){
        this.dbProcessListener = dbProcessListener;
    }

    public class selectSQL extends AsyncTask<String, String, String> {
        String z = "";
        Boolean isSuccess = false;
        String un = "";
        String pw = "";


        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(String r) {
            if (isSuccess) {}
            if (doingUser) {
                doingUser = false;
                dbProcessListener.onEvent(true);
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
                    ResultSet rs = stmt.executeQuery(query);
                    if (doingUser) {
                        if(rs.next()) {
                            isSuccess = true;
                            String uname = rs.getString("username");
                            student.setUn(uname); //Name is the string label of a column in database, read through the select query
                            student.setFname(rs.getString("fname")); //Name is the string label of a column in database, read through the select query
                            student.setLname(rs.getString("lname"));
                            student.setId(rs.getString("studentid"));
                            student.setDegree(rs.getString("pname"));
                        }
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
        Boolean idSuccess = false;
    }

    public void select(String query) {
        SQLquery = query;
        Database.selectSQL selectSQL = new Database.selectSQL();// this is the Asynctask, which is used to process in background to reduce load on app process
        selectSQL.execute("");
    }
    public void getUserInfo(Integer userId){
        studentId = userId;
        SQLquery = "SELECT s.username, s.fname, s.lname,s.studentid, p.pname FROM Program p JOIN StudentDegree sd ON p.programid = sd.programid JOIN Student s ON sd.studentid = s.studentid AND s.studentid = '" + studentId + "'";
        doingUser = true;
        Database.selectSQL selectSQL = new Database.selectSQL();// this is the Asynctask, which is used to process in background to reduce load on app process
        selectSQL.execute("");
    }

    public User getUser(){
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
