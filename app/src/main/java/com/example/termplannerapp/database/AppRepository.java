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
    public LiveData<List<MentorEntity>> mMentors;
    public LiveData<List<AssessmentEntity>> mAssessments;
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
        mMentors = getAllMentors();
        mAssessments = getAllAssessments();
    }

//    public void addSampleData() {
//        executor.execute(() -> mDb.termDao().insertAll(SampleTermData.getTerms()));
//    }

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
        executor.execute(() -> mDb.termDao().insertTerm(term));
    }

    public void deleteTerm(final TermEntity term) {
        executor.execute(() -> mDb.termDao().deleteTerm(term));
    }

    //course methods

    private LiveData<List<CourseEntity>> getAllCourses() {
        return mDb.courseDao().getAll();
    }

    public CourseEntity getCourseById(int courseId) {
        return mDb.courseDao().getCourseById(courseId);
    }

    public LiveData<List<CourseEntity>> getCourseByTerm(final int termId) {
        return mDb.courseDao().getCourseByTerm(termId);
    }

    public void deleteAllCourses() {
        executor.execute(() -> mDb.courseDao().deleteAll());
    }

    public void insertCourse(CourseEntity course) {
        executor.execute(() -> mDb.courseDao().insertCourse(course));
    }

    public void deleteCourse(final CourseEntity course) {
        executor.execute(() -> mDb.courseDao().deleteCourse(course));
    }

    //mentor methods

    private LiveData<List<MentorEntity>> getAllMentors() {
        return mDb.mentorDao().getAll();
    }


    public MentorEntity getMentorById(int mentorId) {
        return mDb.mentorDao().getMentorById(mentorId);
    }

    public void insertMentor(MentorEntity mentor) {
        executor.execute(() -> mDb.mentorDao().insertMentor(mentor));
    }

    public void deleteMentor(final MentorEntity mentor) {
        executor.execute(() -> mDb.mentorDao().deleteMentor(mentor));
    }

    //assessment methods

    private LiveData<List<AssessmentEntity>> getAllAssessments() {
        return mDb.assessmentDao().getAll();
    }

    public AssessmentEntity getAssessmentById(int assessmentId) {
        return mDb.assessmentDao().getAssessmentById(assessmentId);
    }

    public void insertAssessment(AssessmentEntity assessment) {
        executor.execute(() -> mDb.assessmentDao().insertAssessment(assessment));
    }

    public void deleteAssessment(AssessmentEntity assessment) {
        executor.execute(() -> mDb.assessmentDao().deleteAssessment(assessment));
    }
}
