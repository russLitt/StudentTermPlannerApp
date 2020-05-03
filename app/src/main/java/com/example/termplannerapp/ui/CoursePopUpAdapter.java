package com.example.termplannerapp.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.CourseEntity;

import java.util.List;

public class CoursePopUpAdapter extends RecyclerView.Adapter<CoursePopUpAdapter.CourseViewHolder> {
    private List<CourseEntity> mCourses;
    private CourseSelectedListener mCourseSelectedListener;

    public CoursePopUpAdapter(List<CourseEntity> mCourses) {
        //super();
        this.mCourses = mCourses;
    }

    public void setCourseSelectedListener(CoursePopUpAdapter.CourseSelectedListener mCourseSelectedListener) {
        this.mCourseSelectedListener = mCourseSelectedListener;
    }

    @NonNull
    @Override
    public CourseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CourseViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.course_select_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, final int position) {
        final CourseEntity course = mCourses.get(position);
        holder.mCourseTitle.setText(course.getCourseTitle());
        holder.itemView.setOnClickListener(view -> {
            if (mCourseSelectedListener != null) {
                mCourseSelectedListener.onCourseSelected(position, course);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    static class CourseViewHolder extends RecyclerView.ViewHolder {
        TextView mCourseTitle;

        public CourseViewHolder(View itemView) {
            super(itemView);
            mCourseTitle = itemView.findViewById(R.id.course_title);
        }
    }

    public interface CourseSelectedListener {
        void onCourseSelected(int position, CourseEntity course);
    }
}
