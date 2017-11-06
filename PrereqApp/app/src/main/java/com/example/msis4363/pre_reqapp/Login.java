package com.example.msis4363.pre_reqapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    //Executes code for login button click
    public void onLogin(View v){

        //Save login information
        EditText userName = (EditText) findViewById(R.id.userName);
        EditText userPass = (EditText) findViewById(R.id.userPass);

        String userName =


    }


}
