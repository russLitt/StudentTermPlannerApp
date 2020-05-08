package com.example.termplannerapp.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.termplannerapp.database.AppRepository;
import com.example.termplannerapp.database.MentorEntity;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MentorEditorViewModel extends AndroidViewModel {

    public MutableLiveData<MentorEntity> mLiveMentors = new MutableLiveData<>();
    private AppRepository mRepository;
    private Executor executor = Executors.newSingleThreadExecutor();

    public MentorEditorViewModel(@NonNull Application application) {
        super(application);
        mRepository = AppRepository.getInstance(getApplication());
    }

    public void loadData(final int mentorId) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                MentorEntity mentor = mRepository.getMentorById(mentorId);
                mLiveMentors.postValue(mentor);
            }
        });
    }

    public void saveMentor(String mentorName, String mentorEmail, String mentorPhone, int courseId) {
        MentorEntity mentor = mLiveMentors.getValue();

        if (mentor == null) {
            if (TextUtils.isEmpty(mentorName.trim())) {
                return;
            }
            mentor = new MentorEntity(mentorName.trim(), mentorEmail.trim(), mentorPhone.trim(), courseId);
        } else {
            mentor.setMentorName(mentorName.trim());
            mentor.setMentorEmail(mentorEmail.trim());
            mentor.setMentorPhone(mentorPhone.trim());
            mentor.setCourseId(courseId);
        }
        mRepository.insertMentor(mentor);
    }

    public void deleteMentor() {
        mRepository.deleteMentor(mLiveMentors.getValue());
    }
}
