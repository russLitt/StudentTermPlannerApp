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
   // private CourseSelectedListener courseSelectedListener;

    public CoursesAdapter(List<CourseEntity> mCourses, Context mContext) {
        this.mCourses = mCourses;
        this.mContext = mContext;
        //this.courseSelectedListener = courseSelectedListener;
    }

    class Courses extends RecyclerView.ViewHolder {

        public Courses(@NonNull View itemView) {
            super(itemView);
            TextView mCourseTitle;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.course_list_item, parent, false);
        return new ViewHolder(view);
    }

    @NonNull
    public ViewHolder onCreateViewHolder2(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view2 = inflater.inflate(R.layout.course_select_item, parent, false);
        return new ViewHolder(view2);
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

    public class ViewHolder extends RecyclerView.ViewHolder{
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
        //CourseSelectedListener courseSelectedListener;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            //this.courseSelectedListener = courseSelectedListener;

            //itemView.setOnClickListener(this);
        }

//        @Override
//        public void onClick(View view) {
//            courseSelectedListener.onCourseSelected(getAdapterPosition(), mCourses.get(getAdapterPosition()));
//        }
//    }
//
//    public interface CourseSelectedListener {
//        void onCourseSelected(int position, CourseEntity course);
//    }
    }
}