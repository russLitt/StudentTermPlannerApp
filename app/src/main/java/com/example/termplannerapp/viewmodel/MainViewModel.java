package com.example.termplannerapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.AssessmentEntity;
import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.database.MentorEntity;
import com.example.termplannerapp.database.TermEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public LiveData<List<TermEntity>> mTerms;
    public LiveData<List<CourseEntity>> mCourses;
    public LiveData<List<MentorEntity>> mMentors;
    public LiveData<List<AssessmentEntity>> mAssessments;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance(application.getApplicationContext());
        mTerms = mRepository.mTerms;
        mCourses = mRepository.mCourses;
        mMentors = mRepository.mMentors;
        mAssessments = mRepository.mAssessments;
    }

    public void addSampleData() {
        mRepository.addSampleData();
    }

    public void deleteAllTerms() {
        mRepository.deleteAllTerms();
    }
}
