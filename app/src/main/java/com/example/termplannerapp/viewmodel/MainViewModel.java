package com.example.termplannerapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.utilities.SampleTermData;

import java.util.List;

public class MainViewModel extends AndroidViewModel {

    public List<TermEntity> mTerms = SampleTermData.getTerms();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }
}
