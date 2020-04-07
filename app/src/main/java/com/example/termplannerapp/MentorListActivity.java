package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.MentorEntity;
import com.example.termplannerapp.ui.MentorsAdapter;
import com.example.termplannerapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MentorListActivity extends AppCompatActivity {

    @BindView(R.id.mentor_recycler_view)
    RecyclerView mMentorRecyclerView;

    @OnClick(R.id.mentor_edit_fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, MentorEditorActivity.class);
        startActivity(intent);
    }

    private List<MentorEntity> mentorsData = new ArrayList<>();
    private MentorsAdapter mMentorsAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mentor_list);
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Mentors");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        final Observer<List<MentorEntity>> mentorsObserver = mentorEntities -> {
            mentorsData.clear();
            mentorsData.addAll(mentorEntities);

            if (mMentorsAdapter == null) {
                mMentorsAdapter = new MentorsAdapter(mentorsData,
                        MentorListActivity.this);
                mMentorRecyclerView.setAdapter(mMentorsAdapter);
            } else {
                mMentorsAdapter.notifyDataSetChanged();
            }
        };

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.mMentors.observe(this, mentorsObserver);
    }

    private void initRecyclerView() {
        mMentorRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mMentorRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mMentorRecyclerView.getContext(),
                layoutManager.getOrientation());
        mMentorRecyclerView.addItemDecoration(divider);

        mMentorsAdapter = new MentorsAdapter(mentorsData, this);
        mMentorRecyclerView.setAdapter(mMentorsAdapter);
    }
}
