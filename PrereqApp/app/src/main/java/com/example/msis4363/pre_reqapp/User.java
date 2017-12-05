package com.example.msis4363.pre_reqapp;

/**
 * Created by Cooper on 12/4/2017.
 */

public class User {
    private String un, fname, lname, degree, id;

    public void setUn(String un) {
        this.un = un;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public void setLname(String lname) {
        this.lname = lname;
    }
    public void setDegree(String degree) {
        this.degree = degree;
    }
    public void setId(String id) {
        this.id = id;
    }

    public String getDegree() {
        return degree;
    }
    public String getFname() {
        return fname;
    }
    public String getId() {
        return id;
    }
    public String getLname() {
        return lname;
    }
    public String getUn() {
        return un;
    }
}
