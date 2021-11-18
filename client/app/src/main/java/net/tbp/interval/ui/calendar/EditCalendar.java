package net.tbp.interval.ui.calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.interval.R;

import net.tbp.interval.ui.calendar.CalendarProvider;

import java.util.Calendar;
import java.util.Date;

// This class will use to edit a reminder
public class EditCalendar extends Activity {
    private Button saveBtn;         // button for user to save new reminder
    private Button cancelBtn;       // button for user to cancel and go back to reminder screen
    // variable that might use when a user click save
    private Integer id;
    private String name;
    //private String description;
    //private Integer priority = 0;
    private Date dueDate = new Date();
    private Long dueDateLong;
    private static final String TAG = "EditCalendar";
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.add_event_on_calendar);

        // load incoming intent
        getIncomingIntent();

        // prepare and recieve priority from seekbar
//        SeekBar seekbar = findViewById(R.id.prioritySeekBar);
//        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                priority = progress;
//                priorityValue(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//            }
//        });

        // prepare and recieve priority from calendar
        CalendarView calendarView = findViewById(R.id.calendar_1);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // Create calender object with which will have system date time.
                Calendar calendar = Calendar.getInstance();
                // Set attributes in calender object as per selected date.
                calendar.set(year, month, dayOfMonth);
                dueDate.setTime(calendar.getTimeInMillis());
                dueDateLong = dueDate.getTime();
                Log.e("duedate",dueDate.toString());
            }
        });

        // add onclick listener to save button
        saveBtn = (Button) findViewById(R.id.saveEventBtnInCalendar);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check that btn work when user click save
                Log.d(TAG, "save");
                // preload sql
                ContentValues values = new ContentValues();
                // name that user will add to the database
                String name = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();
                values.put(CalendarProvider.NAME, name);
                // description that user will pass to database
//                description = ((EditText)findViewById(R.id.reminderDescription_label)).getText().toString();
//                values.put(ReminderProvider.DESCRIPTION, description);
//
//                values.put(ReminderProvider.PRIORITY, priority);
                values.put(CalendarProvider.DUEDATE, dueDateLong);

                // update data to sql
                getContentResolver().update(CalendarProvider.CONTENT_URI, values, CalendarProvider._ID + "="
                        + id, null);

                // go back to prev screen
                finish();
            }
        });

        // add onclick listener to cancel button
//        cancelBtn = (Button) findViewById(R.id.cancelAddNewReminderBtn);
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // to check that btn work when user click cancel
//                Log.d(TAG, "cancel");
//                // go back to prev screen
//                finish();
//            }
//        });
    }

    // func that will check the incoming intent and assign value to variable
    private void getIncomingIntent() {
        Log.d(TAG, "getIncomingIntent");
        if(getIntent().hasExtra("name" ) && getIntent().hasExtra("description") &&
                getIntent().hasExtra("name" ) && getIntent().hasExtra("description")){
            Log.d(TAG, "getIncomingIntent: found necessary Intent extras.");
            // assign data to variable
            id = getIntent().getIntExtra("id", 0);
            name = getIntent().getStringExtra("name");
            //description = getIntent().getStringExtra("description");
            //priority = getIntent().getIntExtra("priority", 0);
            dueDateLong = getIntent().getLongExtra("dueDate", 0);
            // set data to textView
            //setData(name, description, priority, dueDateLong);
        }
    }

    // set incoming data to textView
//    private void setData(String name, String description, Integer priority , Long dueDate){
//        // console check
//        Log.d(TAG, "setData");
//        TextView reminderName = findViewById(R.id.reminderTitle_label);
//        reminderName.setText(name);
//        TextView reminderDescription = findViewById(R.id.reminderDescription_label);
//        reminderDescription.setText(description);
//        SeekBar seekbar = findViewById(R.id.prioritySeekBar);
//        seekbar.setProgress(priority);
//        priorityValue(priority);
//        CalendarView  reminderDueDate = findViewById(R.id.reminderCalendarView);
//        reminderDueDate.setDate(dueDate);
//    }

    // func that will use to set show type of reminder priority
//    private void priorityValue(int priority){
//        TextView priorityValue = findViewById(R.id.prioriyValue);
//        switch (priority){
//            case 0:
//                priorityValue.setText("Low");
//                break;
//            case 1:
//                priorityValue.setText("Normal");
//                break;
//            case 2:
//                priorityValue.setText("Important");
//                break;
//            case 3:
//                priorityValue.setText("Critical");
//                break;
//            default:
//                priorityValue.setText("Critical");
//        }
//    }
}
