package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.MentorEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.MENTOR_ID_KEY;

public class MentorDetailsActivity extends AppCompatActivity {

    @BindView(R.id.mentor_name)
    TextView mMentorName;

    @BindView(R.id.mentor_email)
    TextView mMentorEmail;

    @BindView(R.id.mentor_phone)
    TextView mMentorPhone;

    private MentorEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Mentor Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(MentorEditorViewModel.class);
        mViewModel.mLiveMentors.observe(this, (MentorEntity) -> {
            mMentorName.setText(MentorEntity.getMentorName());
            mMentorEmail.setText(MentorEntity.getMentorEmail());
            mMentorPhone.setText(MentorEntity.getMentorPhone());
        });

        Bundle extras = getIntent().getExtras();
        int mentorId = extras.getInt(MENTOR_ID_KEY);
        mViewModel.loadData(mentorId);
    }
}
