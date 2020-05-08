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

import com.example.termplannerapp.MentorDetailsActivity;
import com.example.termplannerapp.MentorEditorActivity;
import com.example.termplannerapp.MentorListActivity;
import com.example.termplannerapp.R;
import com.example.termplannerapp.database.MentorEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.MENTOR_ID_KEY;

public class MentorsAdapter extends RecyclerView.Adapter<MentorsAdapter.ViewHolder> {

    private final List<MentorEntity> mMentors;
    private final Context mContext;
    private MentorSelectedListener mMentorSelectedListener;

    public interface MentorSelectedListener {
        void onMentorSelected(int position, MentorEntity mentor);
    }

    public MentorsAdapter(List<MentorEntity> mMentors, Context mContext, MentorSelectedListener mMentorSelectedListener) {
        this.mMentors = mMentors;
        this.mContext = mContext;
        this.mMentorSelectedListener = mMentorSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.mentor_list_item, parent, false);
        return new ViewHolder(view, mMentorSelectedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorsAdapter.ViewHolder holder, int position) {
        final MentorEntity mentor = mMentors.get(position);
        holder.mMentorName.setText(mentor.getMentorName());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MentorEditorActivity.class);
                intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                mContext.startActivity(intent);
            }
        });

        holder.mMentorDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MentorDetailsActivity.class);
                intent.putExtra(MENTOR_ID_KEY, mentor.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.mentor_name)
        TextView mMentorName;
        @BindView(R.id.mentor_edit_fab)
        FloatingActionButton mFab;
        @BindView(R.id.mentor_details_layout)
        ConstraintLayout mMentorDetails;
        MentorSelectedListener mMentorSelectedListener;

        public ViewHolder(@NonNull View itemView, MentorSelectedListener mMentorSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mMentorSelectedListener = mMentorSelectedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mMentorSelectedListener.onMentorSelected(getAdapterPosition(), mMentors.get(getAdapterPosition()));
        }
    }
}
