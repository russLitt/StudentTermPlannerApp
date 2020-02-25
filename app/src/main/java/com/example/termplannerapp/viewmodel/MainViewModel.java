package com.example.termplannerapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.TermEntity;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<TermEntity> mTerms;
    private AppRepository mRepository;

    public MainViewModel(@NonNull Application application) {
        super(application);

        mRepository = AppRepository.getInstance();
        mTerms = mRepository.mTerms;
    }
}
