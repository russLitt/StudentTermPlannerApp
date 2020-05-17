package com.example.termplannerapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.AssessmentEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessments = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int assessmentId) {
        executor.execute(() -> {
            AssessmentEntity assessment = mRepository.getAssessmentById(assessmentId);
            mLiveAssessments.postValue(assessment);
        });
    }

    public void saveAssessment(String assessmentTitle, String assessmentDueDate, String assessmentType, int courseId) {
        AssessmentEntity assessment = mLiveAssessments.getValue();

        if (assessment == null) {
            if (TextUtils.isEmpty(assessmentTitle.trim())) {
                return;
            }
            assessment = new AssessmentEntity(assessmentTitle.trim(), assessmentDueDate.trim(), assessmentType.trim(), courseId);
        } else {
            assessment.setAssessmentTitle(assessmentTitle.trim());
            assessment.setAssessmentDueDate(assessmentDueDate.trim());
            assessment.setAssessmentType(assessmentType.trim());
            assessment.setCourseId(courseId);
        }
        mRepository.insertAssessment(assessment);
    }

    public void deleteAssessment() {
        mRepository.deleteAssessment(mLiveAssessments.getValue());
    }

}
