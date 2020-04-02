package com.example.termplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AssessmentEditorActivity extends AppCompatActivity {

    @BindView(R.id.assmnt_switch)
    Switch mAssmntSwitch;

    @BindView(R.id.assmnt_title)
    EditText mAssmntTitle;

    @BindView(R.id.assmnt_due_date)
    EditText mAssmntDueDate;

    @BindView(R.id.assmnt_alert_check)
    CheckBox mAssmntAlertCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        intiViewModel();

    }

    private void intiViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourses.observe(this, (CourseEntity) -> {
            //if (CourseEntity != null && !mEditingCourse) {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
            mRadioButton.setText(CourseEntity.getStatus());
            //}
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_course));
            mNewCourse = true;
        } else {
            setTitle(getString(R.string.edit_course));
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        }
    }
}
