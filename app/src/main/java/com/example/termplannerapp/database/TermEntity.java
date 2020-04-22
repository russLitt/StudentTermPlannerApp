package com.example.termplannerapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String termTitle, termStartDate, termEndDate, courseTitle;
    private boolean courseChecked;

    @Ignore
    public TermEntity() {
    }

    //for editing an existing term
    public TermEntity(int id, Date date, String termTitle, String termStartDate,
                      String termEndDate, boolean courseChecked, String courseTitle) {
        this.id = id;
        this.date = date;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.courseChecked = courseChecked;
        this.courseTitle = courseTitle;
    }

    //for creating new term and assigning id individually
    @Ignore
    public TermEntity(Date date, String termTitle, String termStartDate, String termEndDate, boolean courseChecked,
                      String courseTitle) {
        this.date = date;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
        this.courseChecked = courseChecked;
        this.courseTitle = courseTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTermTitle() {
        return termTitle;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public String getTermStartDate() {
        return termStartDate;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public String getTermEndDate() {
        return termEndDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    public boolean getCourseChecked() {
        return courseChecked;
    }

    public void setCourseChecked(Boolean courseChecked) {
        this.courseChecked = courseChecked;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    //for debugging
    @Override
    public String toString() {
        return "TermEntity{" +
                "id=" + id +
                ", date=" + date +
                ", termTitle='" + termTitle + '\'' +
                '}';
    }
}
