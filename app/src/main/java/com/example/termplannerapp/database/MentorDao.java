package com.example.termplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
interface MentorDao {

    @Query("SELECT * FROM mentors WHERE id = :id")
    MentorEntity getMentorById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMentor(MentorEntity mentor);

    @Delete
    void deleteMentor(MentorEntity mentor);

    @Query("SELECT * FROM mentors ORDER BY id")
    LiveData<List<MentorEntity>> getAll();

    @Query("SELECT * FROM mentors WHERE courseId = :courseId")
    LiveData<List<MentorEntity>> getMentorByCourse(int courseId);
}
