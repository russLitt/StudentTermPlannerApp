package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.termplannerapp.viewmodel.MainViewModel;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @OnClick(R.id.terms_list_btn)
    void termsBtnHandler() {
        Intent intent = new Intent(this, TermListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.course_list_btn)
    void coursesBtnHandler() {
        Intent intent = new Intent(this, CourseListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.mentor_list_btn)
    void mentorBtnHandler() {
        Intent intent = new Intent(this, MentorListActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.assessment_list_btn)
    void assessmentBtnHandler() {
        Intent intent = new Intent(this, AssessmentListActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_help) {
            helpMenu();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void helpMenu() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Learn how to use this app");
        builder.setMessage("This app is designed to help students track terms and courses " +
                "as well as assessments and mentors associated with each course." +
                "\n\nTerms, courses, assessments and mentors can be created in any order, " +
                "however a term must exist for a course to be added to it and a course " +
                "must exist for an assessment and or mentors to be added to it." +
                "\n\nFurthermore, a term cannot be deleted if it has courses assigned to it.");
        builder.setPositiveButton("Ok", (dialog, id) -> {
            dialog.dismiss();
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
