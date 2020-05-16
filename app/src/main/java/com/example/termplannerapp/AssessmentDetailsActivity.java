package com.example.termplannerapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.termplannerapp.viewmodel.AssessmentEditorViewModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        assert extras != null;
        int assessmentId = extras.getInt(ASSESSMENT_ID_KEY);
        mViewModel.loadData(assessmentId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_assessment_details, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.start_notification) {
            Toast.makeText(this, "Assessment goal date notification set", Toast.LENGTH_SHORT).show();

            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
                //convert string date to date object
                Date date = sdf.parse(mAssmntDueDate.getText().toString());

                //convert date string to time in millis for alarm manager
                assert date != null;
                long startDate = date.getTime();

                Intent intent = new Intent(AssessmentDetailsActivity.this, AppAlerts.class);
                intent.putExtra("key", mAssmntTitle.getText().toString() + " due today: " + mAssmntDueDate.getText().toString());
                PendingIntent sender = PendingIntent.getBroadcast(AssessmentDetailsActivity.this, 0, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
