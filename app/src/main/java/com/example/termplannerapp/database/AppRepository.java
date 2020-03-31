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
    public LiveData<List<CourseEntity>> mCourses;
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
        mCourses = getAllCourses();
    }

    public void addSampleData() {
        executor.execute(() -> mDb.termDao().insertAll(SampleTermData.getTerms()));
    }

    //term methods
    private LiveData<List<TermEntity>> getAllTerms() {
        return mDb.termDao().getAll();
    }

    public void deleteAllTerms() {
        executor.execute(() -> mDb.termDao().deleteAll());
    }

    public TermEntity getTermById(int termId) {
        return mDb.termDao().getTermById(termId);
    }

    public void insertTerm(TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().insertTerm(term);
            }
        });
    }

    public void deleteTerm(final TermEntity term) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.termDao().deleteTerm(term);
            }
        });
    }

    //course methods

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public void deleteAllCourses() {
        executor.execute(() -> mDb.courseDao().deleteAll());
    }

    public void insertCourse(CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().insertCourse(course);
            }
        });
    }

    public void deleteCourse(final CourseEntity course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                mDb.courseDao().deleteCourse(course);
            }
        });
    }

    //mentor methods
}
