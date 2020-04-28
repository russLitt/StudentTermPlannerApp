package com.example.termplannerapp;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.CourseEditorViewModel;

import java.util.Objects;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Optional;

import static com.example.termplannerapp.utilities.Constants.EDITING_COURSE_KEY;
import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class CourseEditorActivity extends AppCompatActivity {

    @BindView(R.id.course_title)
    TextView mCourseTitle;

    @BindView(R.id.course_start_date)
    EditText mCourseStartDate;

    @BindView(R.id.course_end_date)
    EditText mCourseEndDate;

    @BindView(R.id.course_status_rb_group)
    RadioGroup mCourseStatus;

    @BindView(R.id.rb_completed)
    RadioButton mRadioButton;

    @BindDrawable(R.drawable.ic_delete_green)
    Drawable mDeleteIcon;

    private CourseEditorViewModel mViewModel;
    private Boolean mNewCourse, mEditingCourse;
    private int termId = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditingCourse = savedInstanceState.getBoolean(EDITING_COURSE_KEY);
        }

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourses.observe(this, (CourseEntity) -> {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
            mRadioButton.setText(CourseEntity.getStatus());
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_course));
            mNewCourse = true;
        } else if (extras.containsKey(TERM_ID_KEY)) {
            termId = extras.getInt(TERM_ID_KEY);
        }
        else {
            setTitle(getString(R.string.edit_course));
            int courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete_course) {
            mViewModel.deleteCourse();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveCourse(mCourseTitle.getText().toString(),
                mCourseStartDate.getText().toString(),
                mCourseEndDate.getText().toString(),
                mRadioButton.getText().toString(),
                termId);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_COURSE_KEY, true);
        super.onSaveInstanceState(outState);
    }

    public void onRbClicked(View view) {
        int selectedStatus = mCourseStatus.getCheckedRadioButtonId();
        mRadioButton = findViewById(selectedStatus);
        Toast.makeText(this, "Status selection: " + mRadioButton.getText(), Toast.LENGTH_SHORT).show();
    }
}
