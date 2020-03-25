package com.example.termplannerapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.termplannerapp.viewmodel.TermEditorViewModel;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_TERM_TITLE = "EXTRA_TERM_TITLE";

    @OnClick(R.id.term_details_btn)
    void btnClickHandler() {
        Intent intent = new Intent(this, TermDetailsActivity.class);
        startActivity(intent);
    }

    @BindView(R.id.term_recycler_view)
    RecyclerView mTermRecyclerView;

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start)
    TextView mTermStartDate;

    @BindView(R.id.term_end)
    TextView mTermEndDate;


    private TermEditorViewModel mViewModel;
    private Button mTermDetailsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTermDetailsBtn = findViewById(R.id.term_details_btn);
        mTermDetailsBtn.setOnClickListener(v -> openTermDetails());

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    public void openTermDetails() {
        TextView termTitle = mTextView;
        String text = mTextView.getText().toString();

        Intent intent = new Intent(this, TermDetailsActivity.class);
        intent.putExtra(EXTRA_TERM_TITLE, text);
        startActivity(intent);
    }

    private void initRecyclerView() {
        mTermRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTermRecyclerView.setLayoutManager(layoutManager);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {

            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
        });
    }
}