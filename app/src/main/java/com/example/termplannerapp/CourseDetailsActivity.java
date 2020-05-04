package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.AssessmentEntity;
import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.AssessmentsAdapter;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

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

    private List<AssessmentEntity> assessmentsData = new ArrayList<>();
    private List<AssessmentEntity> unassignedAssessments = new ArrayList<>();
    private CourseEditorViewModel mViewModel;
    private AssessmentsAdapter mAssessmentsAdapter;

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
        initRecyclerView();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourses.observe(this, (CourseEntity) -> {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
            mRadioButton.setText(CourseEntity.getStatus());
        });

        final Observer<List<AssessmentEntity>> assessmentsObserver = assessmentEntities -> {
            assessmentsData.clear();
            assessmentsData.addAll(assessmentEntities);

            if (mAssessmentsAdapter == null) {
                mAssessmentsAdapter = new AssessmentsAdapter(assessmentsData,
                        CourseDetailsActivity.this, this::onAssessmentSelected);
                mAssessmentRecyclerView.setAdapter(mAssessmentsAdapter);
            } else {
                mAssessmentsAdapter.notifyDataSetChanged();
            }
        };

        final Observer<List<AssessmentEntity>> unassignedAssessmentObserver = assessmentEntities -> {
            unassignedAssessments.clear();
            unassignedAssessments.addAll(assessmentEntities);
        };


        Bundle extras = getIntent().getExtras();
        int assessmentId = extras.getInt(COURSE_ID_KEY); //CHECK
        mViewModel.loadData(assessmentId);
    }

    private void onAssessmentSelected(int i, AssessmentEntity assessmentEntity) {
    }
}
