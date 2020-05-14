package com.example.termplannerapp;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.termplannerapp.database.CourseEntity;
import com.example.termplannerapp.ui.CourseSelectMenuAdapter;
import com.example.termplannerapp.ui.CoursesAdapter;
import com.example.termplannerapp.viewmodel.TermEditorViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.termplannerapp.AppAlerts.CHANNEL_TERM_DATES;
import static com.example.termplannerapp.utilities.Constants.TERM_ID_KEY;

public class TermDetailsActivity extends AppCompatActivity {

    @BindView(R.id.term_text)
    TextView mTextView;

    @BindView(R.id.term_start_date)
    TextView mTermStartDate;

    @BindView(R.id.term_end_date)
    TextView mTermEndDate;

    @BindView(R.id.course_add_fab)
    FloatingActionButton mCourseAdd;

    @BindView(R.id.term_details_course_recycler_view)
    RecyclerView mCourseRecyclerView;

    private List<CourseEntity> coursesData = new ArrayList<>();
    private List<CourseEntity> unassignedCourses = new ArrayList<>();
    private Toolbar toolbar;
    private CoursesAdapter mCoursesAdapter;
    private TermEditorViewModel mViewModel;
    private int termId;
    NotificationManagerCompat mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_term_details);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Term Details");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ButterKnife.bind(this);

        mNotificationManager = NotificationManagerCompat.from(this);

        initRecyclerView();
        initViewModel();
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(this).get(TermEditorViewModel.class);
        mViewModel.mLiveTerms.observe(this, (termEntity) -> {
            mTextView.setText(termEntity.getTermTitle());
            mTermStartDate.setText(termEntity.getTermStartDate());
            mTermEndDate.setText(termEntity.getTermEndDate());
        });

        final Observer<List<CourseEntity>> coursesObserver = courseEntities -> {
            coursesData.clear();
            coursesData.addAll(courseEntities);

            if (mCoursesAdapter == null) {
                mCoursesAdapter = new CoursesAdapter(coursesData,
                        TermDetailsActivity.this, this::onCourseSelected);
                mCourseRecyclerView.setAdapter(mCoursesAdapter);
            } else {
                mCoursesAdapter.notifyDataSetChanged();
            }
        };

        final Observer<List<CourseEntity>> unassignedCourseObserver = courseEntities -> {
            unassignedCourses.clear();
            unassignedCourses.addAll(courseEntities);
        };

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            termId = extras.getInt(TERM_ID_KEY);
            mViewModel.loadData(termId);
        } else {
            finish();
        }

        mViewModel.getCourseInTerm(termId).observe(this, coursesObserver);
        mViewModel.getUnassignedCourses().observe(this, unassignedCourseObserver);
    }

    @OnClick(R.id.course_add_fab)
    public void courseAddHandler() {
        if (unassignedCourses.size() != 0) {
            final CourseSelectMenuAdapter menu = new CourseSelectMenuAdapter(this, unassignedCourses);
            menu.setHeight(1000);
            menu.setOutsideTouchable(true);
            menu.showAsDropDown(mCourseAdd);
            menu.setCourseSelectedListener((position, course) -> {
                menu.dismiss();
                course.setTermId(termId);
                mViewModel.setCourseToTerm(course, termId);
            });
        } else {
            Toast.makeText(getApplicationContext(), "No unassigned courses found.  Create a new course to add it to term.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void initRecyclerView() {
        mCourseRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mCourseRecyclerView.setLayoutManager(layoutManager);
    }

    public void onCourseSelected(int position, CourseEntity course) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.term_details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.notifications) {

            String currentDate = new SimpleDateFormat("M/d/yyyy", Locale.getDefault()).format(new Date());

            try {
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
                Date date = sdf.parse(mTermStartDate.getText().toString());
                Date date2 = sdf.parse(mTermEndDate.getText().toString());

                assert date != null;
                long startDate = date.getTime();
                assert date2 != null;
                long endDate = date2.getTime();

                System.out.println(date);
                System.out.println(date2);


                Intent intent = new Intent(TermDetailsActivity.this, AppAlerts.class);
                PendingIntent sender = PendingIntent.getBroadcast(TermDetailsActivity.this, 0, intent, 0);
                //if (currentDate.equals(mTermStartDate.getText().toString())) {
                intent.putExtra("key", mTextView.getText().toString() + " begins today: " + mTermStartDate.getText().toString());

                AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                alarmManager.set(AlarmManager.RTC_WAKEUP, startDate, sender);

                //Intent intent2 = new Intent(TermDetailsActivity.this, AppAlerts.class);
                intent.putExtra("key", mTextView.getText().toString() + " ends today: " + mTermEndDate.getText().toString());
                //PendingIntent sender2 = PendingIntent.getBroadcast(TermDetailsActivity.this, 0, intent, 0);
                //AlarmManager alarmManager2 = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                assert alarmManager != null;
                alarmManager.set(AlarmManager.RTC_WAKEUP, endDate, sender);


                // }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}


//        } else if (id == R.id.notifications2) {
//            String currentDate = new SimpleDateFormat("M/d/yyyy", Locale.getDefault()).format(new Date());
//
//            try {
//                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("M/d/yyyy", Locale.US);
//                Date date2 = sdf.parse(mTermEndDate.getText().toString());
//
//                assert date2 != null;
//                long endDate = date2.getTime();
//
//                System.out.println(date2);
//                Intent intent = new Intent(TermDetailsActivity.this, AppAlerts.class);
//
//                //if (currentDate.equals(mTermEndDate.getText().toString())) {
//
////                    Notification notification2 = new NotificationCompat.Builder(TermDetailsActivity.this, CHANNEL_TERM_DATES)
////                            .setSmallIcon(R.drawable.ic_notification)
//                            //intent.putExtra("key","Term end date");
//                            intent.putExtra("key", mTextView.getText().toString() + " ends today: " + mTermEndDate.getText().toString());
//                    PendingIntent sender = PendingIntent.getBroadcast(TermDetailsActivity.this, 0, intent, 0);
//                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
//                    assert alarmManager != null;
//                    alarmManager.set(AlarmManager.RTC_WAKEUP, endDate, sender);
//                    //mNotificationManager.notify(2, notification2);
//                //}
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        }


