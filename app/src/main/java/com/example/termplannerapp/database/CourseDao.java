package com.example.termplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CourseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertCourse(CourseEntity course);

    @Query("SELECT * FROM courses WHERE id = :id")
    CourseEntity getCourseById(int id);

    @Delete
    void deleteCourse(CourseEntity course);

    @Query("SELECT * FROM courses ORDER BY id")
    LiveData<List<CourseEntity>> getAll();

}
