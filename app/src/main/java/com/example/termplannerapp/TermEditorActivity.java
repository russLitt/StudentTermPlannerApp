package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.ui.CoursesSelectAdapter;
import com.example.termplannerapp.viewmodel.MainViewModel;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.EDITING_TERM_KEY;
import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermEditorActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    EditText mTextView;

    @BindView(R.id.term_start_date)
    EditText mTermStartDate;

    @BindView(R.id.term_end_date)
    EditText mTermEndDate;

    @BindView(R.id.course_select_check_box)
    CheckBox mCheckBox;

    @BindView(R.id.course_title)
    TextView mCourseTitle;

    @BindView(R.id.course_select_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private Toolbar toolbar;
    private CoursesSelectAdapter mCoursesAdapter;
    private TermEditorViewModel mViewModel;
    private MainViewModel mMainViewModel;
    private boolean mNewTerm, mEditing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        //setContentView(R.layout.activity_course_select_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_TERM_KEY);
        }

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            if (termEntity != null && !mEditing) {
                mTextView.setText(termEntity.getTermTitle());
                mTermStartDate.setText(termEntity.getTermStartDate());
                mTermEndDate.setText(termEntity.getTermEndDate());
                //mCheckBox.setText(termEntity.getCourseChecked());
            }
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesSelectAdapter(coursesData,
                        TermEditorActivity.this);
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        mMainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mMainViewModel.mCourses.observe(this, coursesObserver);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_term));
            mNewTerm = true;
        } else {
            setTitle(getString(R.string.edit_term));
            int termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        }
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mCourseRecyclerView.getContext(),
                layoutManager.getOrientation());
        mCourseRecyclerView.addItemDecoration(divider);

        mCoursesAdapter = new CoursesSelectAdapter(coursesData, this);
        mCourseRecyclerView.setAdapter(mCoursesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewTerm) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_term_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete_term) {
            mViewModel.deleteTerm();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTerm(mTextView.getText().toString(),
                mTermStartDate.getText().toString(),
                mTermEndDate.getText().toString(),
                mCheckBox.isChecked());
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_TERM_KEY, true);
        super.onSaveInstanceState(outState);
    }

    public void onCourseChecked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        if (checked) {
            Toast.makeText(this, "Course selected: " + mCourseTitle.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}

