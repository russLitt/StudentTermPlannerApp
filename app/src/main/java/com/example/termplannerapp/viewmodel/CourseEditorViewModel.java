package com.example.termplannerapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.database.TermEntity;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CourseEditorViewModel extends AndroidViewModel {

    public MutableLiveData<CourseEntity> mLiveCourses = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public CourseEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int courseId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                CourseEntity course = mRepository.getCourseById(courseId);
                mLiveCourses.postValue(course);
            }
        });
    }

    public void saveCourse(String courseTitle, String courseStart, String courseEnd, String courseStatus, int termId) {
        CourseEntity course = mLiveCourses.getValue();

        if (course == null) {
            if (TextUtils.isEmpty(courseTitle.trim())) {
                return;
            }
            course = new CourseEntity(courseTitle.trim(), courseStart.trim(), courseEnd.trim(), courseStatus.trim(), termId);
        } else {
            course.setCourseTitle(courseTitle.trim());
            course.setCourseStartDate(courseStart.trim());
            course.setCourseEndDate(courseEnd.trim());
            course.setStatus(courseStatus.trim());
            course.setTermId(termId);
        }
        mRepository.insertCourse(course);
    }

    public void deleteCourse() {
        mRepository.deleteCourse(mLiveCourses.getValue());
    }

}