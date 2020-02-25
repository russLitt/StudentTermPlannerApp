package com.example.termplannerapp.database;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.termplannerapp.utilities.SampleTermData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppRepository {
    private static AppRepository ourInstance;

    public LiveData<List<TermEntity>> mTerms;
    private AppDatabase mDb;
    private Executor executor = Executors.newSingleThreadExecutor();

    public static AppRepository getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new AppRepository(context);
        }
        return ourInstance;
    }

    private AppRepository(Context context) {
        mDb = AppDatabase.getInstance(context);
        mTerms = getAllTerms();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleTermData.getTerms()));
    }

//        (ABOVE) METHOD BEFORE USING LAMBDA EXPRESSION
//        executor.execute(new Runnable() {
//            @Override
//            public void run() {
//                mDb.termDao().insertAll(SampleTermData.getTerms());
//            }
//        });


    private LiveData<List<TermEntity>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllTerms() {
        executor.execute(() -> mDb.termDao().deleteAll());
    }
}
