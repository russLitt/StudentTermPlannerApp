package com.example.termplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;

import butterknife.OnClick;

public class CourseListActivity extends AppCompatActivity {

    @OnClick(R.id.fab_add_class)
    void fabClickHandler() {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.content_course_list);
        super.onCreate(savedInstanceState);


    }
}
