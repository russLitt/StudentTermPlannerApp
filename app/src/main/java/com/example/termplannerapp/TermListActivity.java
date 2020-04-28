package com.example.termplannerapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.TermEntity;
import com.example.termplannerapp.ui.TermsAdapter;
import com.example.termplannerapp.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TermListActivity extends AppCompatActivity {

    @BindView(R.id.term_recycler_view)
    RecyclerView mTermRecyclerView;

    @OnClick(R.id.fab)
    void fabClickHandler() {
        Intent intent = new Intent(this, TermEditorActivity.class);
        startActivity(intent);
    }

    private List<TermEntity> termsData = new ArrayList<>();
    private TermsAdapter mTermsAdapter;
    private MainViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_list);
        getSupportActionBar().setTitle("Terms");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);
        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {

        final Observer<List<TermEntity>> termsObserver = new Observer<List<TermEntity>>() {
            @Override
            public void onChanged(List<TermEntity> termEntities) {
                termsData.clear();
                termsData.addAll(termEntities);

                if (mTermsAdapter == null) {
                    mTermsAdapter = new TermsAdapter(termsData,
                            TermListActivity.this);
                    mTermRecyclerView.setAdapter(mTermsAdapter);
                } else {
                    mTermsAdapter.notifyDataSetChanged();
                }
            }
        };

        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mViewModel.mTerms.observe(this, termsObserver);
    }

    private void initRecyclerView() {
        mTermRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mTermRecyclerView.setLayoutManager(layoutManager);

        DividerItemDecoration divider = new DividerItemDecoration(mTermRecyclerView.getContext(),
                layoutManager.getOrientation());
        mTermRecyclerView.addItemDecoration(divider);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_term_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_add_sample_data) {
            //addSampleData();
            return true;
        } else if (id == R.id.action_delete_all) {
            deleteAllTerms();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteAllTerms() {
        mViewModel.deleteAllTerms();
    }
//
//    private void addSampleData() {
//        mViewModel.addSampleData();
//    }
}

