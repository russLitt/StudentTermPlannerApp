package com.example.termplannerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.AssessmentEntity;

import java.util.List;

public class AssessmentSelectMenuAdapter extends PopupWindow {
    private Context mContext;
    private List<AssessmentEntity> mAssessments;
    private RecyclerView mRecyclerPopUp;
    private AssessmentPopUpAdapter mAssessmentsAdapter;

    public AssessmentSelectMenuAdapter(Context mContext, List<AssessmentEntity> mAssessments) {
        super(mContext);
        this.mContext = mContext;
        this.mAssessments = mAssessments;
        initView();
    }

    public void setAssessmentSelectedListener(AssessmentPopUpAdapter.AssessmentSelectedListener assessmentSelectedListener) {
        mAssessmentsAdapter.setAssessmentSelectedListener(assessmentSelectedListener);
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.popup_selector, null);

        mRecyclerPopUp = view.findViewById(R.id.popup_recyclerview);
        mRecyclerPopUp.setHasFixedSize(true);
        mRecyclerPopUp.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mRecyclerPopUp.addItemDecoration(new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL));

        mAssessmentsAdapter = new AssessmentPopUpAdapter(mAssessments);
        mRecyclerPopUp.setAdapter(mAssessmentsAdapter);
        setContentView(view);
    }
}
