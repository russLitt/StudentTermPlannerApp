package com.example.termplannerapp.database;

import com.example.termplannerapp.utilities.SampleTermData;

import java.util.List;

public class AppRepository {
    private static final AppRepository ourInstance = new AppRepository();
    public List<TermEntity> mTerms;

    public static AppRepository getInstance() {
        return ourInstance;
    }

    private AppRepository() {
        mTerms = SampleTermData.getTerms();
    }
}
