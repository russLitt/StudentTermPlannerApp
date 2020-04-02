package com.example.termplannerapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "assessments")
public class AssessmentEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String assessmentTitle, assessmentDueDate, assessmentType;
    private int courseId;

    @Ignore
    public AssessmentEntity() {
    }

    @Ignore
    public AssessmentEntity(int id, String assessmentTitle, String assessmentDueDate, String assessmentType, int courseId) {
        this.id = id;
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    @Ignore
    public AssessmentEntity(String assessmentTitle, String assessmentDueDate, String assessmentType, int courseId) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentType = assessmentType;
        this.courseId = courseId;
    }

    @Ignore
    public AssessmentEntity(String assessmentTitle, String assessmentDueDate, String assessmentType) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentType = assessmentType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAssessmentTitle() {
        return assessmentTitle;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public String getAssessmentDueDate() {
        return assessmentDueDate;
    }

    public void setAssessmentDueDate(String assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public String getAssessmentType() {
        return assessmentType;
    }

    public void setAssessmentType(String assessmentType) {
        this.assessmentType = assessmentType;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
