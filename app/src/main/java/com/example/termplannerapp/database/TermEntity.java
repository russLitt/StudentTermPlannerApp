package com.example.termplannerapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "terms")
public class TermEntity {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private Date date;
    private String termTitle;

    //for creating term and assigning values individually
    @Ignore
    public TermEntity() {
    }

    //for editing an existing term
    public TermEntity(int id, Date date, String termTitle) {
        this.id = id;
        this.date = date;
        this.termTitle = termTitle;
    }

    //for creating new term and assigning id individually
    @Ignore
    public TermEntity(Date date, String termTitle) {
        this.date = date;
        this.termTitle = termTitle;
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
