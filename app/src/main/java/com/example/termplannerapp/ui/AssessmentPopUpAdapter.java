package com.example.termplannerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.AssessmentEntity;

import java.util.List;

public class AssessmentPopUpAdapter extends RecyclerView.Adapter<AssessmentPopUpAdapter.AssessmentViewHolder> {

    private List<AssessmentEntity> mAssessments;
    private AssessmentPopUpAdapter.AssessmentSelectedListener mAssessmentSelectedListener;

    public AssessmentPopUpAdapter(List<AssessmentEntity> mAssessments) {
        //super();
        this.mAssessments = mAssessments;
    }

    public void setAssessmentSelectedListener(AssessmentPopUpAdapter.AssessmentSelectedListener mAssessmentSelectedListener) {
        this.mAssessmentSelectedListener = mAssessmentSelectedListener;
    }

    @NonNull
    @Override
    public AssessmentPopUpAdapter.AssessmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AssessmentPopUpAdapter.AssessmentViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.assessment_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentPopUpAdapter.AssessmentViewHolder holder, final int position) {
        final AssessmentEntity assessment = mAssessments.get(position);
        holder.mAssessmentTitle.setText(assessment.getAssessmentTitle());
        holder.itemView.setOnClickListener(view -> {
            if (mAssessmentSelectedListener != null) {
                mAssessmentSelectedListener.onAssessmentSelected(position, assessment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    static class AssessmentViewHolder extends RecyclerView.ViewHolder {
        TextView mAssessmentTitle;

        public AssessmentViewHolder(View itemView) {
            super(itemView);
            mAssessmentTitle = itemView.findViewById(R.id.assmnt_title);
        }
    }

    public interface AssessmentSelectedListener {
        void onAssessmentSelected(int position, AssessmentEntity assessment);
    }
}
