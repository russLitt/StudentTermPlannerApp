package com.example.termplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssessmentDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAssessment(AssessmentEntity assessment);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<AssessmentEntity> assessments);

    @Query("SELECT * FROM assessments ORDER BY id")
    LiveData<List<AssessmentEntity>> getAll();

    //was assessmentId
    @Query("SELECT * FROM assessments WHERE id = :id")
    AssessmentEntity getAssessmentById(int id);

    @Delete
    void deleteAssessment(AssessmentEntity assessment);

    @Query("DELETE FROM assessments")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM assessments")
    int getCount();

    @Query("SELECT * FROM assessments WHERE courseId = :courseId")
    LiveData<List<AssessmentEntity>> getAssessmentByCourse(int courseId);
}
