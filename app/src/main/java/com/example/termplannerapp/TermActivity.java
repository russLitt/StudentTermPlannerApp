package com.example.termplannerapp;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.ui.TermsAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class TermActivity extends AppCompatActivity {

    @BindView(R.id.term_recycler_view)
    RecyclerView mTermRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    private List<TermEntity> termsData = new ArrayList<>();
    private TermsAdapter mTermsAdapter;
}
