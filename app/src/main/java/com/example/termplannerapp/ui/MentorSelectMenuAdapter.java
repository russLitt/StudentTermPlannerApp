package com.example.termplannerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.MentorEntity;

import java.util.List;

public class MentorSelectMenuAdapter extends PopupWindow {
    private Context mContext;
    private List<MentorEntity> mMentors;
    private RecyclerView mRecyclerPopUp;
    private MentorPopUpAdapter mMentorsAdapter;

    public MentorSelectMenuAdapter(Context mContext, List<MentorEntity> mMentors) {
        super(mContext);
        this.mContext = mContext;
        this.mMentors = mMentors;
        initView();
    }

    public void setMentorSelectedListener(MentorPopUpAdapter.MentorSelectedListener mentorSelectedListener) {
        mMentorsAdapter.setMentorSelectedListener(mentorSelectedListener);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_selector, null);

        mRecyclerPopUp = view.findViewById(R.id.popup_recyclerview);
        mRecyclerPopUp.setHasFixedSize(true);
        mRecyclerPopUp.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerPopUp.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mMentorsAdapter = new MentorPopUpAdapter(mMentors);
        mRecyclerPopUp.setAdapter(mMentorsAdapter);
        setContentView(view);
    }
}
