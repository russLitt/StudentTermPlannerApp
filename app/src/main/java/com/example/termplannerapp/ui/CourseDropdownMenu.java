package com.example.termplannerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.CourseEntity;

import java.util.List;

public class CourseDropdownMenu extends PopupWindow {
    private Context mContext;
    private List<CourseEntity> mCourses;
    private RecyclerView mRecyclerPopUp;
    private CoursePopUpAdapter mCoursesAdapter;

    public CourseDropdownMenu(Context mContext, List<CourseEntity> mCourses) {
        //super(mContext);
        this.mContext = mContext;
        this.mCourses = mCourses;
        initView();
    }

    public void setCourseSelectedListener(CoursePopUpAdapter.CourseSelectedListener courseSelectedListener) {
        mCoursesAdapter.setCourseSelectedListener(courseSelectedListener);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_menu, null);

        mRecyclerPopUp = view.findViewById(R.id.recyclerView_popup);
        mRecyclerPopUp.setHasFixedSize(true);
        mRecyclerPopUp.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerPopUp.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mCoursesAdapter = new CoursePopUpAdapter(mCourses);
        mRecyclerPopUp.setAdapter(mCoursesAdapter);
        setContentView(view);
    }

}
