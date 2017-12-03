package com.example.msis4363.pre_reqapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class mainMenu extends AppCompatActivity {

    Intent intent = getIntent();
    //this is how you receive an arraylist of strings from intent.
    //String userstr = intent.getStringExtra("username");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void selectCoursesButtonClick (View v) {
        Intent intent = new Intent(getApplicationContext(), FinishedCourses.class);
        //intent.putExtra("username", userstr);
        startActivity(intent);
    }

    public void summaryPageButtonClick (View v) {
        Intent intent = new Intent(getApplicationContext(), ClassesToTake.class);
        //intent.putExtra("username", userstr);
        startActivity(intent);
    }

}
