package com.example.msis4363.pre_reqapp;

/**
 * Created by Cooper on 11/6/2017.
 */

public class User {
    int id;
    String username, password, firstname, lastname;

    //create the setter and getter methods

    public void setId (int id) {
        this.id = id;
    }

    public int getId () {
        return this.id;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getUsername () {
        return this.username;
    }

    public void setPassword (String password) {this.password = password;}

    public String getPassword () {return this.password;}

    public void setFirstName (String firstname) {this.firstname = firstname;}

    public String getFirstName () {return this.firstname;}

    public void setLastName (String lastname) {this.lastname = lastname;}

    public String getLastName () {return this.lastname;}

}
