package net.tbp.interval.ui.tracker;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.interval.R;
import com.example.interval.databinding.FragmentTrackerBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import net.tbp.interval.ui.reminder.ReminderProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

// this class will use to render the tracker page
public class TrackerFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    // TAG that will use to console to inspect
    String TAG = "TrackerFragment";
    private TrackerViewModel trackerViewModel;
    private FragmentTrackerBinding binding;
    //variable that relate to date
    private Date date =  new Date();
    Calendar cal = Calendar.getInstance();
    int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
    Date weekMedalStart;
    Date twoWeekMedalStart;
    Date threeWeekMedalStart;
    Date monthMedalStart;
    Date goalEndDate;
    Date currentWeekStart;
    Date currentWeekEnd;

    // list that will store reminder
    private List<Tracker> trackersList = new ArrayList<>();

    // variable that relate to render goal path
    int weekMedalShow = 2;
    int twoWeekMedalShow = 2;
    int threeWeekMedalShow = 2;
    int monthMedalShow = 2;
    ImageView weekMedal;
    ImageView twoWeekMedal;
    ImageView threeWeekMedal;
    ImageView monthMedal;

    // variable that will use to render chart
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntries;
    private static final int MAX_X_VALUE = 7;
    private static final int MAX_Y_VALUE = 100;
    private static final int MIN_Y_VALUE = 0;
    private static final String SET_LABEL = "%Day Task completed";
    private static final String[] DAYS = { "SUN", "MON", "TUE", "WED", "THU", "FRI", "SAT" };
    ArrayList<BarEntry> values = new ArrayList<>();

    // variable that show week progress
    ProgressBar weekProgress;
    TextView weekProgressLabel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        trackerViewModel =
                new ViewModelProvider(this).get(TrackerViewModel.class);

        binding = FragmentTrackerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // set goal date
        setGoalDate();
        // load reminder database
        LoaderManager.getInstance(this).initLoader(0,null, (LoaderManager.LoaderCallbacks<Cursor>)this);

        // link medal to xml file
        weekMedal = root.findViewById(R.id.week_medal);
        twoWeekMedal = root.findViewById(R.id.twoweek_medal);
        threeWeekMedal = root.findViewById(R.id.threeweek_medal);
        monthMedal = root.findViewById(R.id.month_medal);
        barChart = (BarChart) root.findViewById(R.id.bargraph);
        weekProgress = root.findViewById(R.id.weekly_task_progressBar);
        weekProgressLabel = root.findViewById(R.id.weekly_goal_progress);
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        // console that trackerfragment onResume start
        Log.d(TAG, "onResume start");

        int numberOfReminder = trackersList.size();
        if(numberOfReminder == 0){
            weekProgressLabel.setText("No task in this week");
        }

        if(numberOfReminder >= 1){
            setGoalMedal(numberOfReminder);
            setProgress(numberOfReminder);
        }

        BarData data = createChartData();
        configureChartAppearance();
        prepareChartData(data);

        // console that trackerfragment onResume end
        Log.d(TAG, "onResume end");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // set apperace of chart
    private void configureChartAppearance() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        barChart.setDrawGridBackground(true);

        // X axis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return DAYS[(int) value];
            }
        });
        // set position to bottom of X asis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setTextSize(12f);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);

        // Y Axis
        YAxis axisLeft = barChart.getAxisLeft();
        axisLeft.setGranularity(20f);
        axisLeft.setAxisMinimum(0);
        axisLeft.setAxisMaximum(100f);
        axisLeft.setTextSize(10f);
        axisLeft.setDrawLabels(true);

        YAxis axisRight = barChart.getAxisRight();
        axisRight.setAxisMinimum(0);
        axisRight.setDrawAxisLine(false);
        axisRight.setDrawGridLines(false);
        axisRight.setDrawLabels(false);
    }

    // set data to chart
    private void prepareChartData(BarData data) {
        data.setDrawValues(false);
        barChart.setData(data);
        barChart.invalidate();
    }

    // create data that will use in chart
    private BarData createChartData() {
        BarDataSet set1 = new BarDataSet(values, SET_LABEL);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(dataSets);
        return data;
    }

    // helper func to load sql
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        // Uri to the content provider LocationContentProvider
        Uri uri = ReminderProvider.CONTENT_URI;
        Log.d(TAG, "onCreateLoader");
        // set sortOrder duedate DESC
        return new CursorLoader(getContext(), uri, null, null, null, "duedate DESC");
    }

    @Override
    public void onLoadFinished(@NonNull Loader loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished");
        // variable that will use to store data from sql
        int reminderCount = 0;
        int id;
        String name = "name";
        Boolean status;
        Date duedate;
        Date completeddate;
        reminderCount = cursor.getCount();
        trackersList.clear();
        cursor.moveToFirst();
        for(int i = 0; i < reminderCount; i++) {
            // get id from sql
            id = cursor.getInt(cursor.getColumnIndex(ReminderProvider._ID));
            // get name from sql
            name = cursor.getString(cursor.getColumnIndex(ReminderProvider.NAME));
            // get status from sql
            status = cursor.getInt(cursor.getColumnIndex(ReminderProvider.STATUS)) > 0;
            Long duedateFromSql = cursor.getLong(cursor.getColumnIndex(ReminderProvider.DUEDATE));
            duedate = new Date();
            duedate.setTime(duedateFromSql);
            Long completeddateFromSql = cursor.getLong(cursor.getColumnIndex(ReminderProvider.COMPLETEDDATE));
            completeddate = new Date();
            completeddate.setTime(completeddateFromSql);
            // console each reminder
            Log.d(TAG, " id: " + id
                    + " name: " + name
                    + " status: " + status
                    + " duedate: " + duedate
                    + " completeddate: " + completeddate
            );
            // add data to reminder class
            Tracker tracker = new Tracker(id, name,status, duedate, completeddate);
            // add reminder to arraylist
            trackersList.add(tracker);
            // move cursor to the next one
            cursor.moveToNext();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }

    // func that will use to set start and end goal date
    public void setGoalDate(){
        Log.d("setGoalDate", "check start and end date that will use to draw goal path");
        // set start date that will use in goal path
        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, -dayOfTheWeek);
        for(int i = 1; i < 5; i++){
            cal.add(Calendar.DATE,  - 7);
            switch (i){
                case 1:
                    weekMedalStart = cal.getTime();
                    Log.d(TAG, "weekMedalStart: "+ String.valueOf(weekMedalStart));
                    break;
                case 2:
                    twoWeekMedalStart = cal.getTime();
                    Log.d(TAG, "twoWeekMedalStart: "+ String.valueOf(twoWeekMedalStart));
                    break;
                case 3:
                    threeWeekMedalStart = cal.getTime();
                    Log.d(TAG, "threeWeekMedalStart: "+ String.valueOf(threeWeekMedalStart));
                    break;
                case 4:
                    monthMedalStart = cal.getTime();
                    Log.d(TAG, "monthMedalStart: "+ String.valueOf(monthMedalStart));
                    break;
                default:
                    Log.d(TAG, "ERROR");
            }
        }
        // set end date that will use in goal path
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR, 23);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, +27);
        goalEndDate = cal.getTime();
        Log.d(TAG, "goalEnd: "+ String.valueOf(goalEndDate));

        cal.add(Calendar.DATE, +7);
        currentWeekEnd = cal.getTime();
        Log.d(TAG, "currentWeekEnd: "+ String.valueOf(currentWeekEnd));

        cal.set(Calendar.MINUTE, 1);
        cal.set(Calendar.HOUR, 12);
        cal.set(Calendar.SECOND, 0);
        cal.add(Calendar.DATE, -7);
        currentWeekStart = cal.getTime();
        Log.d(TAG, "currentWeekStart: "+ String.valueOf(currentWeekStart));
    }

    // func that will use to set start and end goal date
    public void setGoalMedal(int numberOfReminder){
        // check weekmedal
        for(int i = 0; i < numberOfReminder; i++){
            Log.d(TAG, String.valueOf(trackersList.get(i).getDuedate()));
            if((trackersList.get(i).getDuedate().getTime() >= weekMedalStart.getTime()) &&
                    (trackersList.get(i).getDuedate().getTime() <= goalEndDate.getTime())){
                if(!trackersList.get(i).getStatus()){
                    weekMedalShow = 0;
                    break;
                } else {
                    if(trackersList.get(i).getDuedate().getTime() >= trackersList.get(i).getCompleteddate().getTime()){
                        weekMedalShow = 1;
                    } else {
                        weekMedalShow = 0;
                        break;
                    }
                }
            }

            else if(trackersList.get(i).getDuedate().getTime() < weekMedalStart.getTime()){
                if(weekMedalShow == 1){
                    if((trackersList.get(i).getDuedate().getTime() >= twoWeekMedalStart.getTime()) &&
                            (trackersList.get(i).getDuedate().getTime() <= goalEndDate.getTime())){
                        if(!trackersList.get(i).getStatus()){
                            twoWeekMedalShow = 0;
                            break;
                        } else {
                            if(trackersList.get(i).getDuedate().getTime() >= trackersList.get(i).getCompleteddate().getTime()){
                                twoWeekMedalShow = 1;
                            } else {
                                twoWeekMedalShow = 0;
                                break;
                            }
                        }
                    }
                } else {
                    break;
                }
            }

            else if(trackersList.get(i).getDuedate().getTime() < twoWeekMedalStart.getTime()){
                if(twoWeekMedalShow == 1){
                    if((trackersList.get(i).getDuedate().getTime() >= threeWeekMedalStart.getTime()) &&
                            (trackersList.get(i).getDuedate().getTime() <= goalEndDate.getTime())){
                        if(!trackersList.get(i).getStatus()){
                            threeWeekMedalShow = 0;
                            break;
                        } else {
                            if(trackersList.get(i).getDuedate().getTime() >= trackersList.get(i).getCompleteddate().getTime()){
                                threeWeekMedalShow = 1;
                            } else {
                                threeWeekMedalShow = 0;
                                break;
                            }
                        }
                    }
                } else {
                    break;
                }
            }

            else if(trackersList.get(i).getDuedate().getTime() < threeWeekMedalStart.getTime()){
                if(threeWeekMedalShow == 1){
                    if((trackersList.get(i).getDuedate().getTime() >= threeWeekMedalStart.getTime()) &&
                            (trackersList.get(i).getDuedate().getTime() <= goalEndDate.getTime())){
                        if(!trackersList.get(i).getStatus()){
                            monthMedalShow = 0;
                            break;
                        } else {
                            if(trackersList.get(i).getDuedate().getTime() >= trackersList.get(i).getCompleteddate().getTime()){
                                monthMedalShow = 1;
                            } else {
                                monthMedalShow = 0;
                                break;
                            }
                        }
                    }
                } else {
                    break;
                }
            }

            else if(trackersList.get(i).getDuedate().getTime() < monthMedalStart.getTime()){
                break;
            }
        }

        if(weekMedalShow == 1){
            weekMedal.setImageResource(R.drawable.bronze);
        }
        if(twoWeekMedalShow == 1){
            weekMedal.setImageResource(R.drawable.silver);
        }
        if(threeWeekMedalShow == 1){
            weekMedal.setImageResource(R.drawable.gold);
        }
        if(monthMedalShow == 1){
            weekMedal.setImageResource(R.drawable.trophy);
        }
    }

    // func that will use to set progressbar
    public void setProgress(int numberOfReminder){
        int count = 0;
        int countComplete = 0;
        int[] countDay = new int[8];
        int[] countCompletedDay = new int[8];
        for(int i = 0; i < numberOfReminder; i++) {
            Log.d(TAG, String.valueOf(trackersList.get(i).getDuedate()));
            if ((trackersList.get(i).getDuedate().getTime() >= currentWeekStart.getTime()) &&
                    (trackersList.get(i).getDuedate().getTime() <= currentWeekEnd.getTime())) {
                cal.setTime(trackersList.get(i).getDuedate());
                int k = cal.get(Calendar.DAY_OF_WEEK);
                count++;
                countDay[k]++;
                if (trackersList.get(i).getStatus()) {
                    countComplete++;
                    countCompletedDay[k]++;
                }
            }
        }
        if(count == 0){
            weekProgressLabel.setText("No task in this week");
        }
        else if(count > 0){
            int result = countComplete * 100/count;
            Log.d(TAG, String.valueOf(countComplete) + " : " + String.valueOf(count));
            weekProgress.setProgress(result);
            weekProgressLabel.setText(String.valueOf(result) + " %");
        }

        for(int j = 1; j < 8; j++){
            float x = j-1;
            float y = 0;
            if(countDay[j]>0){
                y = countCompletedDay[j] * 100 / countDay[j];
            }
            values.add(new BarEntry(x, y));
        }
    }
}