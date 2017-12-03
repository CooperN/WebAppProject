package com.example.msis4363.pre_reqapp;

import android.content.Intent;
import android.net.Uri;
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

    public void btnClassesToTake(View v) {
        Intent intent = new Intent(getApplicationContext(), ClassesToTake.class);
        startActivity(intent);

    }

    public void btnFinishedCourses(View v) {
        Intent intent2 = new Intent(getApplicationContext(), FinishedCourses.class);
        startActivity(intent2);

    }

    public void btnDegreeSheet(View v) {
        String url = "https://business.okstate.edu/site-files/docs/msis/2016-2017-MIS.pdf";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
