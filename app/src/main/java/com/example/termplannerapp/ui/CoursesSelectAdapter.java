package com.example.termplannerapp.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.R;
import com.example.termplannerapp.database.CourseEntity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CoursesSelectAdapter extends RecyclerView.Adapter<CoursesSelectAdapter.ViewHolder> {

    private final List<CourseEntity> mCourses;
    private final Context mContext;

    public CoursesSelectAdapter(List<CourseEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_select_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CourseEntity course = mCourses.get(position);
        holder.mCourseTitle.setText(course.getCourseTitle());
        holder.mCheckBox.setChecked(course.getCourseChecked());
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_title)
        TextView mCourseTitle;
        @BindView(R.id.course_select_check_box)
        CheckBox mCheckBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
