package com.example.termplannerapp.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.AssessmentDetailsActivity;
import com.example.termplannerapp.AssessmentEditorActivity;
import com.example.termplannerapp.R;
import com.example.termplannerapp.database.AssessmentEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.ASSESSMENT_ID_KEY;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.ViewHolder> {

    private final List<AssessmentEntity> mAssessments;
    private final Context mContext;
    private AssessmentSelectedListener mAssessmentSelectedListener;

    public interface AssessmentSelectedListener {
        void onAssessmentSelected(int position, AssessmentEntity assessment);
    }

    public AssessmentsAdapter(List<AssessmentEntity> mAssessments, Context mContext,
                              AssessmentSelectedListener mAssessmentSelectedListener) {
        this.mAssessments = mAssessments;
        this.mContext = mContext;
        this.mAssessmentSelectedListener = mAssessmentSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new ViewHolder(view, mAssessmentSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AssessmentEntity assessment = mAssessments.get(position);
        holder.mAssmntTitle.setText(assessment.getAssessmentTitle());

        holder.mFab.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, AssessmentEditorActivity.class);
            intent.putExtra(ASSESSMENT_ID_KEY, assessment.getId());
            mContext.startActivity(intent);
        });

        holder.mAssmntDetails.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, AssessmentDetailsActivity.class);
            intent.putExtra(ASSESSMENT_ID_KEY, assessment.getId());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.assmnt_title)
        TextView mAssmntTitle;
        @BindView(R.id.assessment_edit_fab)
        FloatingActionButton mFab;
        @BindView(R.id.assmnt_details_layout)
        ConstraintLayout mAssmntDetails;
        AssessmentSelectedListener mAssessmentSelectedListener;

        public ViewHolder(@NonNull View itemView, AssessmentSelectedListener mAssessmentSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mAssessmentSelectedListener = mAssessmentSelectedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mAssessmentSelectedListener.onAssessmentSelected(getAdapterPosition(), mAssessments.get(getAdapterPosition()));
        }
    }
}
