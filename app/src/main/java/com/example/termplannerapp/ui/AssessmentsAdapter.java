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
import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;

public class AssessmentsAdapter extends RecyclerView.Adapter<AssessmentsAdapter.ViewHolder> {

    private final List<AssessmentEntity> mAssessments;
    private final Context mContext;

    public AssessmentsAdapter(List<AssessmentEntity> mAssessments, Context mContext) {
        this.mAssessments = mAssessments;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.assessment_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final AssessmentEntity assessment = mAssessments.get(position);
        holder.mAssmntTitle.setText(assessment.getAssessmentTitle());
//        holder.mAssmntDueDate.setText(assessment.getAssessmentDueDate());
//        holder.mAssmntSwitch.setText(assessment.getAssessmentType());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AssessmentEditorActivity.class);
                intent.putExtra(ASSESSMENT_ID_KEY, assessment.getId());
                mContext.startActivity(intent);
            }
        });

        holder.mAssmntDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, AssessmentDetailsActivity.class);
                intent.putExtra(ASSESSMENT_ID_KEY, assessment.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mAssessments.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.assmnt_title)
        TextView mAssmntTitle;
//        @BindView(R.id.assmnt_due_date)
//        TextView mAssmntDueDate;
//        @BindView(R.id.assmnt_switch)
//        TextView mAssmntSwitch;
        @BindView(R.id.assessment_edit_fab)
        FloatingActionButton mFab;
        @BindView(R.id.assmnt_details_layout)
        ConstraintLayout mAssmntDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
