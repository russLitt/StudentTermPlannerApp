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

import com.example.termplannerapp.CourseDetailsActivity;
import com.example.termplannerapp.CourseEditorActivity;
import com.example.termplannerapp.R;
import com.example.termplannerapp.TermDetailsActivity;
import com.example.termplannerapp.database.CourseEntity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.ViewHolder> {

    private final List<CourseEntity> mCourses;
    private final Context mContext;
    private CourseSelectedListener mCourseSelectedListener;

    public interface CourseSelectedListener {
        void onCourseSelected(int position, CourseEntity course);
    }

    public CoursesAdapter(List<CourseEntity> mCourses, Context mContext, CourseSelectedListener mCourseSelectedListener) {
        this.mCourses = mCourses;
        this.mContext = mContext;
        this.mCourseSelectedListener = mCourseSelectedListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.course_list_item, parent, false);
            return new ViewHolder(view, mCourseSelectedListener);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CourseEntity course = mCourses.get(position);
        holder.mCourseTitle.setText(course.getCourseTitle());

        holder.mCourseEditFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CourseEditorActivity.class);
                intent.putExtra(COURSE_ID_KEY, course.getId());
                mContext.startActivity(intent);
            }
        });

        holder.mCourseDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CourseDetailsActivity.class);
                intent.putExtra(COURSE_ID_KEY, course.getId());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mCourses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.course_title)
        TextView mCourseTitle;
        @BindView(R.id.course_edit_fab)
        FloatingActionButton mCourseEditFab;
        @BindView(R.id.course_details_layout)
        ConstraintLayout mCourseDetails;
        CourseSelectedListener mCourseSelectedListener;

        public ViewHolder(@NonNull View itemView, CourseSelectedListener mCourseSelectedListener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mCourseSelectedListener = mCourseSelectedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mCourseSelectedListener.onCourseSelected(getAdapterPosition(), mCourses.get(getAdapterPosition()));
        }
    }
}