package com.example.termplannerapp;

import android.app.DatePickerDialog;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.EDITING_COURSE_KEY;
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

    @BindView(R.id.course_dates_checkbox)
    CheckBox mCheckBox;

    @BindView(R.id.course_note)
    TextView mCourseNote;

    private CourseEditorViewModel mViewModel;
    private Boolean mNewCourse, mEditingCourse;
    private int termId = -1;

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

        mCourseStartDate.setInputType(InputType.TYPE_NULL);
        mCourseStartDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            DatePickerDialog picker;
            // date picker dialog
            picker = new DatePickerDialog(CourseEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            mCourseStartDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker.show();
        });

        mCourseEndDate.setInputType(InputType.TYPE_NULL);
        mCourseEndDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            DatePickerDialog picker;
            // date picker dialog
            picker = new DatePickerDialog(CourseEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            mCourseEndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker.show();
        });

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourses.observe(this, (CourseEntity) -> {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
            mRadioButton.setText(CourseEntity.getStatus());
            mCourseNote.setText(CourseEntity.getNote());
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_course));
            mNewCourse = true;
        } else if (extras.containsKey(TERM_ID_KEY)) {
            termId = extras.getInt(TERM_ID_KEY);
        } else {
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
                mCourseNote.getText().toString(),
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
