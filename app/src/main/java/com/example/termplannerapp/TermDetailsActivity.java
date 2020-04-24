package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.ui.CoursesSelectAdapter;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;
import com.example.termplannerapp.viewmodel.MainViewModel;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start_date)
    TextView mTermStartDate;

    @BindView(R.id.term_end_date)
    TextView mTermEndDate;

    @BindView(R.id.course_title)
    TextView mCourseTitle;

//    @BindView(R.id.course_recycler_view)
//    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private List<String> mCoursesSelected = new ArrayList<>();
    private Toolbar toolbar;
    private CoursesSelectAdapter mCoursesAdapter;
    private TermEditorViewModel mViewModel;
    private CourseEditorViewModel mViewCourseModel;
    private MainViewModel mMainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //getSupportActionBar().setTitle("Term Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        //initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            toolbar.setTitle(termEntity.getTermTitle());
            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
            mCourseTitle.setText(termEntity.getCourseTitle());
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesSelectAdapter(coursesData,
                        TermDetailsActivity.this);
                //mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mMainViewModel.mCourses.observe(this, coursesObserver);

        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        mViewModel.loadData(termId);
    }

//    private void initRecyclerView() {
//        mCourseRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mCourseRecyclerView.setLayoutManager(layoutManager);
//
//        DividerItemDecoration divider = new DividerItemDecoration(mCourseRecyclerView.getContext(),
//                layoutManager.getOrientation());
//        mCourseRecyclerView.addItemDecoration(divider);
//
//        mCoursesAdapter = new CoursesAdapter(coursesData, this);
//        mCourseRecyclerView.setAdapter(mCoursesAdapter);
//    }

}