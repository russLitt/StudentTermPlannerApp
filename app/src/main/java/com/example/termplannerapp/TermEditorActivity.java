package com.example.termplannerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;

import java.util.ArrayList;
import java.util.Calendar;
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

    @BindView(R.id.term_details_course_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private CoursesAdapter mCoursesAdapter;
    private Toolbar toolbar;
    private TermEditorViewModel mViewModel;
    private boolean mNewTerm, mEditing;
    private int termId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mEditing = savedInstanceState.getBoolean(EDITING_TERM_KEY);
        }

        mTermStartDate.setInputType(InputType.TYPE_NULL);
        mTermStartDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            DatePickerDialog picker;
            // date picker dialog
            picker = new DatePickerDialog(TermEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            mTermStartDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker.show();
        });

        mTermEndDate.setInputType(InputType.TYPE_NULL);
        mTermEndDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            DatePickerDialog picker;
            // date picker dialog
            picker = new DatePickerDialog(TermEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            mTermEndDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker.show();
        });

        initViewModel();
        initRecyclerView();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            if (termEntity != null && !mEditing) {
                mTextView.setText(termEntity.getTermTitle());
                mTermStartDate.setText(termEntity.getTermStartDate());
                mTermEndDate.setText(termEntity.getTermEndDate());
            }
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesAdapter(coursesData,
                        TermEditorActivity.this, this::onCourseSelected);
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_term));
            mNewTerm = true;
        } else {
            setTitle(getString(R.string.edit_term));
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        }
        mViewModel.getCourseInTerm(termId).observe(this, coursesObserver);
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
            deleteTermHandler();
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteTermHandler() {
        String termTitle = mViewModel.mLiveTerms.getValue().getTermTitle();
        if (coursesData.size() != 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(termTitle + " cannot be deleted");
            builder.setMessage("This term has courses assigned to it, courses must be deleted" +
                    " or removed before the term can be deleted");
            builder.setPositiveButton("Ok", (dialog, id) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Delete " + termTitle + "?");
            builder.setMessage("Are you sure you want to delete this term?");
            builder.setPositiveButton("Yes", (dialog, id) -> {
                dialog.dismiss();
                mViewModel.deleteTerm();
                finish();
            });
            builder.setNegativeButton("Cancel", (dialog, id) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveTerm(mTextView.getText().toString(),
                mTermStartDate.getText().toString(),
                mTermEndDate.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_TERM_KEY, true);
        super.onSaveInstanceState(outState);
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }


    private void onCourseSelected(int position, CourseEntity course) {
    }
}
