package com.example.msis4363.pre_reqapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;

public class Main extends AppCompatActivity {

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String user = getIntent().getStringExtra("username");

    }

    public void selectCoursesButtonClick (View v) {
        Intent intent = new Intent(getApplicationContext(), FinishedCourses.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }

    public void summaryButtonClick (View v) {
        Intent intent = new Intent(getApplicationContext(), ClassesToTake.class);
        intent.putExtra("username", user);
        startActivity(intent);
    }
}
