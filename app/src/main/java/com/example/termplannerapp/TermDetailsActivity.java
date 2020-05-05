package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.CourseSelectMenuAdapter;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start_date)
    TextView mTermStartDate;

    @BindView(R.id.term_end_date)
    TextView mTermEndDate;

    @BindView(R.id.course_add_fab)
    FloatingActionButton mCourseAdd;

    @BindView(R.id.term_details_course_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private List<CourseEntity> unassignedCourses = new ArrayList<>();
    private Toolbar toolbar;
    private CoursesAdapter mCoursesAdapter;
    private TermEditorViewModel mViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Term Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesAdapter(coursesData,
                        TermDetailsActivity.this, this::onCourseSelected);
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        final Observer<List<CourseEntity>> unassignedCourseObserver = courseEntities -> {
            unassignedCourses.clear();
            unassignedCourses.addAll(courseEntities);
        };

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        } else {
            finish();
        }

        mViewModel.getCourseInTerm(termId).observe(this, coursesObserver);
        mViewModel.getUnassignedCourses().observe(this, unassignedCourseObserver);
    }

    @OnClick(R.id.course_add_fab)
    public void courseAddHandler() {
        if (unassignedCourses.size() != 0) {
            final CourseSelectMenuAdapter menu = new CourseSelectMenuAdapter(this, unassignedCourses);
            menu.setHeight(1000);
            menu.setOutsideTouchable(true);
            menu.showAsDropDown(mCourseAdd);
            menu.setCourseSelectedListener((position, course) -> {
                menu.dismiss();
                course.setTermId(termId);
                mViewModel.setCourseToTerm(course, termId);
            });
        } else {
            Toast.makeText(getApplicationContext(), "No unassigned courses found.  Create a new course to add it to term.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }

    public void onCourseSelected(int position, CourseEntity course) {
    }
}