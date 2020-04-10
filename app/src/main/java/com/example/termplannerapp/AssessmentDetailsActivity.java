package com.example.termplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.example.termplannerapp.database.AssessmentEntity;
import com.example.termplannerapp.viewmodel.AssessmentEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.ASSESSMENT_ID_KEY;

public class AssessmentDetailsActivity extends AppCompatActivity {

    @BindView(R.id.assmnt_switch)
    TextView mAssmntSwitch;

    @BindView(R.id.assmnt_title)
    TextView mAssmntTitle;

    @BindView(R.id.assmnt_due_date)
    TextView mAssmntDueDate;

    private AssessmentEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assessment_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Assessment Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(AssessmentEditorViewModel.class);
        mViewModel.mLiveAssessments.observe(this, (AssessmentEntity) -> {
            mAssmntSwitch.setText(AssessmentEntity.getAssessmentType());
            mAssmntTitle.setText(AssessmentEntity.getAssessmentTitle());
            mAssmntDueDate.setText(AssessmentEntity.getAssessmentDueDate());
        });

        Bundle extras = getIntent().getExtras();
        int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
        mViewModel.loadData(assessmentId);
    }

}
