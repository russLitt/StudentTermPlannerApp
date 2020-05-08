package com.example.termplannerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.MentorEntity;

import java.util.List;

public class MentorPopUpAdapter extends RecyclerView.Adapter<MentorPopUpAdapter.MentorViewHolder> {
    private List<MentorEntity> mMentors;
    private MentorPopUpAdapter.MentorSelectedListener mMentorSelectedListener;

    public MentorPopUpAdapter(List<MentorEntity> mMentors) {
        this.mMentors = mMentors;
    }

    public void setMentorSelectedListener(MentorPopUpAdapter.MentorSelectedListener mMentorSelectedListener) {
        this.mMentorSelectedListener = mMentorSelectedListener;
    }

    @NonNull
    @Override
    public MentorPopUpAdapter.MentorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MentorPopUpAdapter.MentorViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mentor_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MentorPopUpAdapter.MentorViewHolder holder, final int position) {
        final MentorEntity mentor = mMentors.get(position);
        holder.mMentorName.setText(mentor.getMentorName());
        holder.itemView.setOnClickListener(view -> {
            if (mMentorSelectedListener != null) {
                mMentorSelectedListener.onMentorSelected(position, mentor);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMentors.size();
    }

    static class MentorViewHolder extends RecyclerView.ViewHolder {
        TextView mMentorName;

        public MentorViewHolder(View itemView) {
            super(itemView);
            mMentorName = itemView.findViewById(R.id.mentor_name);
        }
    }

    public interface MentorSelectedListener {
        void onMentorSelected(int position, MentorEntity mentor);
    }
}
