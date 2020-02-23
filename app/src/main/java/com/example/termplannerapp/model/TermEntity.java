package com.example.termplannerapp.model;

import java.util.Date;

public class TermEntity {
    private int id;
    private Date date;
    private String termTitle;

    //for creating term and assigning values individually
    public TermEntity() {
    }

    //for editing an existing term
    public TermEntity(int id, Date date, String termTitle) {
        this.id = id;
        this.date = date;
        this.termTitle = termTitle;
    }

    //for creating new term and assigning id individually
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
