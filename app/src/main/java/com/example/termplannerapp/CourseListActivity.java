package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseListActivity extends AppCompatActivity {

    @BindView(R.id.course_recycler_view)
    RecyclerView mCourseRecyclerView;

    @OnClick(R.id.course_edit_fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, CourseEditorActivity.class);
        startActivity(intent);
    }

    private List<CourseEntity> coursesData = new ArrayList<>();
    private CoursesAdapter mCoursesAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_course_list);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Courses");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesAdapter(coursesData,
                        CourseListActivity.this);
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.mCourses.observe(this, coursesObserver);
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mCourseRecyclerView.getContext(),
                layoutManager.getOrientation());
        mCourseRecyclerView.addItemDecoration(divider);

        mCoursesAdapter = new CoursesAdapter(coursesData, this);
        mCourseRecyclerView.setAdapter(mCoursesAdapter);
    }
}
