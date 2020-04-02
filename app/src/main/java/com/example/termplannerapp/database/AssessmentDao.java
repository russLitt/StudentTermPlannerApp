package com.example.termplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;

@Dao
public interface AssessmentDao {

    LiveData<List<AssessmentEntity>> getAll();

    AssessmentEntity getAssessmentById(int assessmentId);

    void insertAssessment(AssessmentEntity assessment);

    void deleteAssessment(AssessmentEntity assessment);
}
