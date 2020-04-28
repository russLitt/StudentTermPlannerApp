package com.example.termplannerapp.database;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "courses")
public class CourseEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String courseTitle, courseStartDate, courseEndDate, status;
    private int termId;

    @Ignore
    public CourseEntity() {
    }

    @Ignore
    public CourseEntity(int id, String courseTitle, String courseStartDate, String courseEndDate, String status, int termId) {
        this.id = id;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.status = status;
        this.termId = termId;
    }

    @Ignore
    public CourseEntity(String courseTitle, String courseStartDate, String courseEndDate, String status, int termId) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.status = status;
        this.termId = termId;
    }

    public CourseEntity(String courseTitle, String courseStartDate, String courseEndDate, String status) {
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseEndDate = courseEndDate;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTermId() {
        return termId;
    }

    public void setTermId(int termId) {
        this.termId = termId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getCourseStartDate() {
        return courseStartDate;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public String getCourseEndDate() {
        return courseEndDate;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

