package com.example.termplannerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.MentorEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MentorsAdapter extends RecyclerView.Adapter<MentorsAdapter.ViewHolder> {

    private final List<MentorEntity> mMentors;
    private final Context mContext;

    public MentorsAdapter(List<MentorEntity> mMentors, Context mContext) {
        this.mMentors = mMentors;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.content_mentor_editor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorsAdapter.ViewHolder holder, int position) {
        final MentorEntity mentor = mMentors.get(position);
        holder.mMentorName.setText(mentor.getMentorName());
        holder.mMentorEmail.setText(mentor.getMentorEmail());
        holder.mMentorPhone.setText(mentor.getMentorPhone());

    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.mentor_name)
        TextView mMentorName;
        @BindView(R.id.mentor_phone)
        TextView mMentorPhone;
        @BindView(R.id.mentor_email)
        TextView mMentorEmail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
