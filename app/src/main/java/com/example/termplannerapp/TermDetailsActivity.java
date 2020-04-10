package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.TermEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start_date)
    TextView mTermStartDate;

    @BindView(R.id.term_end_date)
    TextView mTermEndDate;

    private TermEditorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Term Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
        });

        Bundle extras = getIntent().getExtras();
        int termId = extras.getInt(TERM_ID_KEY);
        mViewModel.loadData(termId);
    }
}
