package com.example.termplannerapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MentorEditorActivity extends AppCompatActivity {

    @BindView(R.id.mentor_name)
    TextView mMentorName;

    @BindView(R.id.mentor_email)
    TextView mMentorEmail;

    @BindView(R.id.mentor_phone)
    TextView mMentorPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mentor_editor);

        ButterKnife.bind(this);

        initViewModel();
    }

    private void initViewModel() {
    }
}
