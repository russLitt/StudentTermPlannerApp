package com.example.termplannerapp;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.termplannerapp.database.AppDatabase;
import com.example.termplannerapp.database.TermDao;
import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.utilities.SampleTermData;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    public static final String TAG = "Junit";
    private AppDatabase mDb;
    private TermDao mDao;

    @Before
    public void createDb() {
        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
        mDb = Room.inMemoryDatabaseBuilder(context,
                AppDatabase.class).build();

        mDao = mDb.termDao();
        Log.i(TAG, "createDb");
    }

    @After
    public void closeDb() {
        mDb.close();
        Log.i(TAG, "closeDb");
    }

    @Test
    public void createAndRetrieveTerms() {
        mDao.insertAll(SampleTermData.getTerms());
        int count = mDao.getCount();
        Log.i(TAG, "createAndRetrieveTerms: count= " + count);
        assertEquals(SampleTermData.getTerms().size(), count);
    }

    @Test
    public void compareStrings() {
        mDao.insertAll(SampleTermData.getTerms());
        TermEntity original = SampleTermData.getTerms().get(0);
        TermEntity fromDb = mDao.getTermById(1);
        assertEquals(original.getTermTitle(), fromDb.getTermTitle());
        assertEquals(1, fromDb.getId());
    }
}
