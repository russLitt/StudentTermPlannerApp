package com.example.termplannerapp;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.AssessmentEditorViewModel;

import java.util.Calendar;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.COURSE_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.EDITING_ASSESSMENT_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {

    @BindView(R.id.assmnt_switch)
    Switch mAssmntSwitch;

    @BindView(R.id.assmnt_title)
    EditText mAssmntTitle;

    @BindView(R.id.assmnt_due_date)
    EditText mAssmntDueDate;

    private AssessmentEditorViewModel mViewModel;
    private boolean mNewAssessment, mEditingAssessment;
    private int courseId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mAssmntDueDate.setInputType(InputType.TYPE_NULL);
        mAssmntDueDate.setOnClickListener(view -> {
            final Calendar cal = Calendar.getInstance();
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);
            int year = cal.get(Calendar.YEAR);
            DatePickerDialog picker;
            // date picker dialog
            picker = new DatePickerDialog(AssessmentEditorActivity.this,
                    (view1, year1, monthOfYear, dayOfMonth) ->
                            mAssmntDueDate.setText((monthOfYear + 1) + "/" + dayOfMonth + "/" + year1), year, month, day);
            picker.show();
        });

        mAssmntSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                mAssmntSwitch.setText("Objective");
            } else {
                mAssmntSwitch.setText("Performance");
            }
        });
        intiViewModel();
    }

    private void intiViewModel() {
        mViewModel = new ViewModelProvider(this).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessments.observe(this, (AssessmentEntity) -> {
            mAssmntTitle.setText(AssessmentEntity.getAssessmentTitle());
            mAssmntDueDate.setText(AssessmentEntity.getAssessmentDueDate());
            mAssmntSwitch.setText(AssessmentEntity.getAssessmentType());
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_assessment));
            mNewAssessment = true;
        } else if (extras.containsKey(COURSE_ID_KEY)) {
            courseId = extras.getInt(COURSE_ID_KEY);
        } else {
            setTitle(getString(R.string.edit_assessment));
            int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
            mViewModel.loadData(assessmentId);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNewAssessment) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_assessment_editor, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            saveAndReturn();
            return true;
        } else if (item.getItemId() == R.id.action_delete_assessment) {
            mViewModel.deleteAssessment();
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        saveAndReturn();
    }

    private void saveAndReturn() {
        mViewModel.saveAssessment(mAssmntTitle.getText().toString(),
                mAssmntDueDate.getText().toString(),
                mAssmntSwitch.getText().toString(),
                courseId);
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_ASSESSMENT_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
