package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.viewmodel.CourseEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;

public class CourseDetailsActivity extends AppCompatActivity {

    @BindView(R.id.course_title)
    TextView mCourseTitle;

    @BindView(R.id.course_start_date)
    TextView mCourseStartDate;

    @BindView(R.id.course_end_date)
    TextView mCourseEndDate;

    @BindView(R.id.rb_completed)
    TextView mRadioButton;

    @BindView(R.id.course_details_assessment_recycler_view)
    RecyclerView mAssessmentRecyclerView;

    private CourseEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Course Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

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
        int courseId = extras.getInt(COURSE_ID_KEY);
        mViewModel.loadData(courseId);
    }
}
