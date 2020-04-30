package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.ui.CourseDropdownMenu;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.ui.CoursesSelectAdapter;
import com.example.termplannerapp.viewmodel.CourseEditorViewModel;
import com.example.termplannerapp.viewmodel.MainViewModel;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start_date)
    TextView mTermStartDate;

    @BindView(R.id.term_end_date)
    TextView mTermEndDate;

    @BindView(R.id.course_add_fab)
    FloatingActionButton mCourseAdd;

    @BindView(R.id.term_details_course_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private List<CourseEntity> unassignedCourses = new ArrayList<>();
    private Toolbar toolbar;
    private CoursesAdapter mCoursesAdapter;
    private TermEditorViewModel mViewModel;
    private CourseEditorViewModel mCourseViewModel;
    private MainViewModel mMainViewModel;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Term Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            //toolbar.setTitle(termEntity.getTermTitle());
            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesAdapter(coursesData,
                        TermDetailsActivity.this, this::onCourseSelected); //may have to change this
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mMainViewModel.mCourses.observe(this, coursesObserver);
        //mCourseViewModel.getCourseInTerm(termId).observe(this, coursesObserver);

        final Observer<List<CourseEntity>> unassignedCourseObserver = courseEntities -> {
            unassignedCourses.clear();
            unassignedCourses.addAll(courseEntities);
        };

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        } else {
            finish();
        }

        mViewModel.getCourseInTerm(termId).observe(this, coursesObserver);
        mViewModel.getUnassignedCourses().observe(this, unassignedCourseObserver);

    }

    @OnClick(R.id.course_add_fab)
    public void courseAddButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("New or existing course?");
        builder.setMessage("Create and add new course or add existing course to term?");
        builder.setIcon(R.drawable.ic_add);
        builder.setPositiveButton("New", (dialog, id) -> {
            dialog.dismiss();
            Intent intent = new Intent(this, CourseEditorActivity.class);
            intent.putExtra(TERM_ID_KEY, termId);
            this.startActivity(intent);
        });
        builder.setNegativeButton("Existing", (dialog, id) -> {
            if (unassignedCourses.size() >= 1) { // determines if at least one unassigned course is listed
                final CourseDropdownMenu menu = new CourseDropdownMenu(this, unassignedCourses);
                //menu.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
                menu.setHeight(1000);
                //menu.setWidth(getPxFromDp(800));
                menu.setOutsideTouchable(true);
                menu.setFocusable(true);
                menu.showAsDropDown(mCourseAdd);
                menu.setCourseSelectedListener((position, course) -> {
                    menu.dismiss();
                    course.setTermId(termId);
                    mViewModel.overwriteCourse(course, termId);
                });
            } else {
                Toast.makeText(getApplicationContext(), "No unassigned courses found.  Create a new course.", Toast.LENGTH_SHORT).show();
            }

        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getPxFromDp(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }
//    private void initRecyclerView() {
//        mCourseRecyclerView.setHasFixedSize(true);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mCourseRecyclerView.setLayoutManager(layoutManager);
//
//        DividerItemDecoration divider = new DividerItemDecoration(mCourseRecyclerView.getContext(),
//                layoutManager.getOrientation());
//        mCourseRecyclerView.addItemDecoration(divider);
//
//        mCoursesAdapter = new CoursesAdapter(coursesData, this);
//        mCourseRecyclerView.setAdapter(mCoursesAdapter);
    //  }

    //@Override
    public void onCourseSelected(int position, CourseEntity course) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Remove this course from term?");
        builder.setMessage("Course won't be deleted - only removed from term.");
        //builder.setIcon(android.R.drawable.ic_dialog_alert);
        builder.setPositiveButton("Continue", (dialog, id) -> {
            dialog.dismiss();
            mViewModel.overwriteCourse(course, -1);
            mCoursesAdapter.notifyDataSetChanged();
        });
        builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}