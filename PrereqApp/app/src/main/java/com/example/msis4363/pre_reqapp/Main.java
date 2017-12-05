package com.example.msis4363.pre_reqapp;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Main extends AppCompatActivity {

/*private static int splash_time_out = 5000;*/
    Integer studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        studentId = getIntent().getIntExtra("studentid", 0);
}

    public void btnClassesToTake(View v) {
        Intent intent = new Intent(getApplicationContext(), ClassSummary.class);
        intent.putExtra("studentid", studentId);
        startActivity(intent);
    }

    public void btnFinishedCourses(View v) {
        Intent intent2 = new Intent(getApplicationContext(), SelectCourses.class);
        intent2.putExtra("studentid", studentId);
        startActivity(intent2);
    }

    public void btnPrereqCourses(View v) {
        Intent intent2 = new Intent(getApplicationContext(), PreReq.class);
        intent2.putExtra("studentid", studentId);
        startActivity(intent2);
    }

    public void btnDeleteCourses(View v) {
        Intent intent2 = new Intent(getApplicationContext(), DeleteCourses.class);
        intent2.putExtra("studentid", studentId);
        startActivity(intent2);
    }

    public void btnProfile(View v) {
        Intent intent2 = new Intent(getApplicationContext(), Profile.class);
        intent2.putExtra("studentid", studentId);
        startActivity(intent2);
    }

    public void btnDegreeSheet(View v) {
        String url = "https://business.okstate.edu/undergraduate/degree-requirements.html";
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

}
