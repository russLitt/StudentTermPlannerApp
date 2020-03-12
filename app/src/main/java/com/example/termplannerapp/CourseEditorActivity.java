package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseEditorActivity extends AppCompatActivity {

    private CourseEditorViewModel mViewModel;
    private Boolean mNewCourse, mEditingCourse;
    private String status = "";

    @BindView(R.id.course_title)
    TextView mCourseTitle;

    @BindView(R.id.course_start_date)
    EditText mCourseStartDate;

    @BindView(R.id.course_end_date)
    EditText mCourseEndDate;

    @BindView(R.id.course_status_rb_group)
    RadioGroup mCourseStatus;

    @BindView(R.id.rb_completed)
    RadioButton mCompleted;

    @BindView(R.id.rb_in_progress)
    RadioButton mInProgress;

    @BindView(R.id.rb_dropped)
    RadioButton mDropped;

    @BindView(R.id.rb_plan_to_take)
    RadioButton mPlanToTake;

    //used to test for now, will change class target later when its created
    @OnClick(R.id.add_mentor_btn)
    void addMentorBtnHandler() {
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    @OnClick(R.id.add_assessment_btn)
    void addAssessmentBtnHandler() {
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_editor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.bind(this);

    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourse.observe(this, (CourseEntity) -> {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
        });
    }

    public void onRbClicked(View view) {

        int selectedStatus = mCourseStatus.getCheckedRadioButtonId();

        RadioButton radioButton = findViewById(selectedStatus);

        String status = radioButton.getText().toString();

        int result = Integer.parseInt(status);


        //boolean checked = ((RadioButton) view).isChecked();

//        switch(view.getId()) {
//            case R.id.rb_in_progress:
//                if (checked)
//                    status = "in progress";
//                    break;
//            case  R.id.rb_completed:
//                if (checked)
//                    status = "completed";
//                    break;
//            case R.id.rb_dropped:
//                if (checked)
//                    status = "dropped";
//                    break;
//            case R.id.rb_plan_to_take:
//                if (checked)
//                    status = "plan to take";
//                    break;
//        }
    }
}
