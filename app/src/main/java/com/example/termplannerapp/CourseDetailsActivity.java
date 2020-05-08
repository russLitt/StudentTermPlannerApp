package com.example.termplannerapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.AssessmentEntity;
import com.example.termplannerapp.database.MentorEntity;
import com.example.termplannerapp.ui.AssessmentSelectMenuAdapter;
import com.example.termplannerapp.ui.AssessmentsAdapter;
import com.example.termplannerapp.ui.MentorSelectMenuAdapter;
import com.example.termplannerapp.ui.MentorsAdapter;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    @BindView(R.id.note_text)
    TextView mNoteText;

    @BindView(R.id.assessment_add_fab)
    FloatingActionButton mAssessmentAdd;

    @BindView(R.id.share_note_btn)
    Button mShareNoteBtn;
    
    @BindView(R.id.course_details_assessment_recycler_view)
    RecyclerView mAssessmentRecyclerView;

    @BindView(R.id.course_details_mentor_recycler_view)
    RecyclerView mMentorRecyclerView;

    private List<AssessmentEntity> assessmentsData = new ArrayList<>();
    private List<AssessmentEntity> unassignedAssessments = new ArrayList<>();
    private List<MentorEntity> mentorsData = new ArrayList<>();
    private List<MentorEntity> unassignedMentors = new ArrayList<>();

    private CourseEditorViewModel mViewModel;
    private AssessmentsAdapter mAssessmentsAdapter;
    private MentorsAdapter mMentorsAdapter;
    private int courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Course Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mShareNoteBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                sendEmail();
            }
        });
    

        initViewModel();
        initAssessmentRecyclerView();
        initMentorRecyclerView();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(CourseEditorViewModel.class);
        mViewModel.mLiveCourses.observe(this, (CourseEntity) -> {
            mCourseTitle.setText(CourseEntity.getCourseTitle());
            mCourseStartDate.setText(CourseEntity.getCourseStartDate());
            mCourseEndDate.setText(CourseEntity.getCourseEndDate());
            mRadioButton.setText(CourseEntity.getStatus());
            mNoteText.setText(CourseEntity.getNote());
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

        final Observer<List<MentorEntity>> mentorsObserver = mentorEntities -> {
            mentorsData.clear();
            mentorsData.addAll(mentorEntities);

            if (mMentorsAdapter == null) {
                mMentorsAdapter = new MentorsAdapter(mentorsData,
                        CourseDetailsActivity.this, this::onMentorSelected);
                mMentorRecyclerView.setAdapter(mMentorsAdapter);
            } else {
                mMentorsAdapter.notifyDataSetChanged();
            }
        };

        final Observer<List<MentorEntity>> unassignedMentorObserver = mentorEntities -> {
            unassignedMentors.clear();
            unassignedMentors.addAll(mentorEntities);
        };

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            courseId = extras.getInt(COURSE_ID_KEY);
            mViewModel.loadData(courseId);
        } else {
            finish();
        }

        mViewModel.getAssessmentInCourse(courseId).observe(this, assessmentsObserver);
        mViewModel.getUnassignedAssessments().observe(this, unassignedAssessmentObserver);
        mViewModel.getMentorInCourse(courseId).observe(this, mentorsObserver);
        mViewModel.getUnassignedMentors().observe(this, unassignedMentorObserver);
    }

    @OnClick(R.id.assessment_add_fab)
    public void assessmentAddHandler() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add mentor or assessment?");
        builder.setPositiveButton("Assessment", (dialog, id) -> {
            //dialog.dismiss();
            if (unassignedAssessments.size() != 0) {
                final AssessmentSelectMenuAdapter menu = new AssessmentSelectMenuAdapter(this, unassignedAssessments);
                menu.setHeight(1000);
                menu.setOutsideTouchable(true);
                menu.showAsDropDown(mAssessmentAdd);
                menu.setAssessmentSelectedListener((position, assessment) -> {
                    menu.dismiss();
                    assessment.setCourseId(courseId);
                    mViewModel.setAssessmentToCourse(assessment, courseId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned assessments found. " +
                                "Create a new assessment to add it to course.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Mentor", (dialog, id) -> {
            if (unassignedMentors.size() != 0) {
                final MentorSelectMenuAdapter menu = new MentorSelectMenuAdapter(this, unassignedMentors);
                menu.setHeight(1000);
                menu.setOutsideTouchable(true);
                menu.showAsDropDown(mAssessmentAdd);
                menu.setMentorSelectedListener((position, mentor) -> {
                    menu.dismiss();
                    mentor.setCourseId(courseId);
                    mViewModel.setMentorToCourse(mentor, courseId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned mentors found. " +
                                "Create a new mentor to add it to course. ",
                        Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void initAssessmentRecyclerView() {
        mAssessmentRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mAssessmentRecyclerView.setLayoutManager(layoutManager);
    }

    private void initMentorRecyclerView() {
        mMentorRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMentorRecyclerView.setLayoutManager(layoutManager);
    }

    private void onAssessmentSelected(int position, AssessmentEntity assessmentEntity) {
    }

    private void onMentorSelected(int position, MentorEntity mentorEntity) {
    }

    private void sendEmail() {
        Log.i("Send email", "");
        String[] TO = {""};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        String noteSubject = "Note for " + mCourseTitle.getText().toString();
        String noteText = "Note: " + mNoteText.getText().toString();
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, noteSubject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, noteText);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
            finish();
            Log.i("Finished sending email", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CourseDetailsActivity.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();
        }
    }
}
