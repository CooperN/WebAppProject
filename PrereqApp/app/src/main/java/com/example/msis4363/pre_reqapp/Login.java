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
        EditText fieldUserName = (EditText) findViewById(R.id.userName);
        EditText fieldUserPass = (EditText) findViewById(R.id.userPass);

        String userName = fieldUserName.getText().toString().toLowerCase();
        String password = fieldUserPass.getText().toString();




    }

    public class LogIn extends AppCompatActivity {

        Database helper = new Database(this);

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
        }

        public void onLoginPressed (View v) {
            EditText a = (EditText) findViewById(R.id.userName);
            String userstr = a.getText().toString();
            EditText b = (EditText) findViewById(R.id.userPass);
            String passstr = b.getText().toString();


            String pass = helper.searchPass(userstr);
            //created name strings so that the helper can pull from the DB
            String firstnamestr = helper.searchFirstName(userstr);
            String lastnamestr = helper.searchLastName(userstr);

            if(passstr.equals(pass)){
                Intent intent2 = new Intent(getApplicationContext(), Profile.class);
                //added the name strings into the welcome message that gets passed on to the Profile page
                intent2.putExtra("FLString", "Welcome " + firstnamestr + " " + lastnamestr + " " + "to your profile");
                startActivity(intent2);


            } else {
                Toast toast = Toast.makeText(LogIn.this, "Password does not match", Toast.LENGTH_SHORT);
                toast.show();
            }


        }

        public void onRegisterPressed(View v){
            Intent intent3 = new Intent(getApplicationContext(), Register.class);
            startActivity(intent3);
        }
    }



}
