package com.example.termplannerapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Switch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.AssessmentEditorViewModel;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.ASSESSMENT_ID_KEY;
import static com.example.termplannerapp.utilities.Constants.EDITING_ASSESSMENT_KEY;

public class AssessmentEditorActivity extends AppCompatActivity {

    @BindView(R.id.assmnt_switch)
    Switch mAssmntSwitch;

    @BindView(R.id.assmnt_title)
    EditText mAssmntTitle;

    @BindView(R.id.assmnt_due_date)
    EditText mAssmntDueDate;

//    @BindView(R.id.assmnt_alert_check)
//    CheckBox mAssmntAlertCheck;

    private AssessmentEditorViewModel mViewModel;
    private boolean mNewAssessment, mEditingAssessment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_editor);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        intiViewModel();

    }

    private void intiViewModel() {
        mViewModel = new ViewModelProvider(this).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessments.observe(this, (AssessmentEntity) -> {
            //if (CourseEntity != null && !mEditingCourse) {
            mAssmntTitle.setText(AssessmentEntity.getAssessmentTitle());
            mAssmntDueDate.setText(AssessmentEntity.getAssessmentDueDate());
            mAssmntSwitch.setText(AssessmentEntity.getAssessmentType());
            //}
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            setTitle(getString(R.string.new_assessment));
            mNewAssessment = true;
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
                mAssmntSwitch.getText().toString());
        finish();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putBoolean(EDITING_ASSESSMENT_KEY, true);
        super.onSaveInstanceState(outState);
    }
}
