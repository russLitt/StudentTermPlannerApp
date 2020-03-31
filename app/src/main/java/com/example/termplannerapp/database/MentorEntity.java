package com.example.termplannerapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "mentors")
public class MentorEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String mentorName, mentorEmail, mentorPhone;
    private int courseId;

    @Ignore
    public MentorEntity() {
    }

    @Ignore
    public MentorEntity(int id, String mentorName, String mentorEmail, String mentorPhone, int courseId) {
        this.id = id;
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorPhone = mentorPhone;
        this.courseId = courseId;
    }

    @Ignore
    public MentorEntity(String mentorName, String mentorEmail, String mentorPhone, int courseId) {
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorPhone = mentorPhone;
        this.courseId = courseId;
    }

    public MentorEntity(String mentorName, String mentorEmail, String mentorPhone) {
        this.mentorName = mentorName;
        this.mentorEmail = mentorEmail;
        this.mentorPhone = mentorPhone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMentorName() {
        return mentorName;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public String getMentorEmail() {
        return mentorEmail;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    public String getMentorPhone() {
        return mentorPhone;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }
}
