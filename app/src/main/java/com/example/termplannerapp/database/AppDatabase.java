package com.example.termplannerapp.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {TermEntity.class, CourseEntity.class}, version = 3, exportSchema = false)
@TypeConverters(DateConverter.class)

public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "AppDatabase.db";
    public static volatile AppDatabase instance;
    public static final Object LOCK = new Object();

    public abstract TermDao termDao();
    public abstract CourseDao courseDao();
    public abstract MentorDao mentorDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return instance;
    }
}
