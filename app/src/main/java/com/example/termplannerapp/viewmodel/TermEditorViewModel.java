package com.example.termplannerapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.TermEntity;

import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static android.content.ContentValues.TAG;

public class TermEditorViewModel extends AndroidViewModel {

    public MutableLiveData<TermEntity> mLiveTerms = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public TermEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int termId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                TermEntity term = mRepository.getTermById(termId);
                mLiveTerms.postValue(term);
            }
        });
    }

    public void saveTerm(String termText, String termStart, String termEnd) {
        TermEntity term = mLiveTerms.getValue();

        if (term == null) {
            if (TextUtils.isEmpty(termText.trim())) {
                return;
            }
//            } else if (termStart != null && termEnd == null) {
//                Toast.makeText(this,"enter end date", Toast.LENGTH_SHORT).show();
//                Log.i(TAG, "saveTerm: got to this method");
//                return;
//            }
            term = new TermEntity(new Date(), termText.trim(), termStart.trim(), termEnd.trim());
        } else {
            term.setTermTitle(termText.trim());
            term.setTermStartDate(termStart.trim());
            term.setTermEndDate(termEnd.trim());
        }
        mRepository.insertTerm(term);
    }

    public void deleteTerm() {
        mRepository.deleteTerm(mLiveTerms.getValue());
    }
}
