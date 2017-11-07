package com.example.msis4363.pre_reqapp;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void onFirstRegister(View v) {
        EditText username = (EditText) findViewById(R.id.registerUser);
        EditText password = (EditText) findViewById(R.id.registerPass);
        //add in name fields to Register
        EditText firstname = (EditText) findViewById(R.id.registerFirstName);
        EditText lastname = (EditText) findViewById(R.id.registerLastName);

        String usernamstr = username.getText().toString();
        String passwordstr = password.getText().toString();
        String firstnamestr = firstname.getText().toString();
        String lastnamestr = lastname.getText().toString();

        User u = new User();
        u.setUsername(usernamstr);
        u.setPassword(passwordstr);
        //Use methods form Contact class to set name fields in DB
        u.setFirstName(firstnamestr);
        u.setLastName(lastnamestr);

        //NEIL
        //This code is to insert the variables from above into the database
     //   helper.insertUser(u);

        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
