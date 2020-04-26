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

    private List<CourseEntity> mCourses;
    private final Context mContext;

    public CoursesAdapter(List<CourseEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.course_list_item, parent, false);
            return new ViewHolder(view);
        }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CourseEntity course = mCourses.get(position);
        holder.mCourseTitle.setText(course.getCourseTitle());
        //holder.mCourseStartDate.setText(course.getCourseStartDate());
        //holder.mCourseEndDate.setText(course.getCourseEndDate());
        //holder.mCourseStatus.setText(course.getStatus());

        holder.mFab.setOnClickListener(new View.OnClickListener() {
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

    public void setCourses(List<CourseEntity> courses) {
        mCourses = courses;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.course_title)
        TextView mCourseTitle;
        //@BindView(R.id.course_start_date)
//        TextView mCourseStartDate;
//        @BindView(R.id.course_end_date)
//        TextView mCourseEndDate;
//        @BindView(R.id.course_status_rb_group)
//        TextView mCourseStatus;
        @BindView(R.id.course_edit_fab)
        FloatingActionButton mFab;
        @BindView(R.id.course_details_layout)
        ConstraintLayout mCourseDetails;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}