package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CourseListActivity extends AppCompatActivity {

    @BindView(R.id.course_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> courseData = new ArrayList<>();
    private CoursesAdapter mCourseAdapter;
    private CourseEditorViewModel mCourseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_list_item);
        //Toolbar toolbar = findViewById(R.id.course_toolbar);
        //setSupportActionBar(toolbar);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        final Observer<List<CourseEntity>> courseObserver =
                courseEntities -> {
                    courseData.clear();
                    courseData.addAll(courseEntities);

                    if(mCourseAdapter == null) {
                        mCourseAdapter = new CoursesAdapter(courseData, CourseListActivity.this);
                        mCourseRecyclerView.setAdapter(mCourseAdapter);
                    } else {
                        mCourseAdapter.notifyDataSetChanged();
                    }
                };
//        mCourseViewModel = ViewModelProviders.of(this).get(CourseEditorViewModel.class);
//        mCourseViewModel.mLiveCourses.observe(this, courseObserver);
    }

}
