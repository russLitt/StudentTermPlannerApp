package com.example.termplannerapp.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TermDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTerm(TermEntity termEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<TermEntity> terms);

    @Delete
    void deleteTerm(TermEntity termEntity);

    @Query("SELECT * FROM terms WHERE id = :id")
    TermEntity getTermById(int id);

    @Query("SELECT * FROM terms ORDER BY date DESC")
    LiveData<List<TermEntity>> getAll();

    @Query("DELETE FROM terms")
    int deleteAll();

    @Query("SELECT COUNT(*) FROM terms")
    int getCount();
}
