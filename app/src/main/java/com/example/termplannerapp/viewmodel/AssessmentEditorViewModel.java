package com.example.termplannerapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.termplannerapp.database.AppRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AssessmentEditorViewModel extends AndroidViewModel {

    public MutableLiveData<AssessmentEntity> mLiveAssessments = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public AssessmentEditorViewModel(@NonNull Application application) {
        super(application);
        AppRepository mRepository = AppRepository.getInstance(getApplication());
    }
}
