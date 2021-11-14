package net.tbp.interval.ui.reminder;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.interval.R;

import java.util.Calendar;
import java.util.Date;

// This class will use to add new reminder
public class AddNewReminder extends Activity {
    private Button saveBtn;         // button for user to save new reminder
    private Button cancelBtn;       // button for user to cancel and go back to reminder screen
    private Integer priority = 0;
    private Date dueDate = new Date();

    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.reminder_info);

        // prepare and recieve priority from seekbar
        SeekBar seekbar = findViewById(R.id.prioritySeekBar);
        TextView priorityValue = findViewById(R.id.prioriyValue);
        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                priority = progress;
                switch (progress){
                    case 0:
                        priorityValue.setText("Low");
                        break;
                    case 1:
                        priorityValue.setText("Normal");
                        break;
                    case 2:
                        priorityValue.setText("Important");
                        break;
                    case 3:
                        priorityValue.setText("Critical");
                        break;
                    default:
                        priorityValue.setText("Critical");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        // prepare and recieve priority from calendar
        CalendarView calendarView = findViewById(R.id.reminderCalendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month,
                                            int dayOfMonth) {
                // Create calender object with which will have system date time.
                Calendar calendar = Calendar.getInstance();
                // Set attributes in calender object as per selected date.
                calendar.set(year, month, dayOfMonth);
                dueDate.setTime(calendar.getTimeInMillis());
                Log.e("duedate",dueDate.toString());
            }
        });

        // add onclick listener to save button
        saveBtn = (Button) findViewById(R.id.saveAddNewReminderBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to show console that btn work when user click
                ContentValues values = new ContentValues();
                // name that user will add to the database
                String name = ((EditText)findViewById(R.id.reminderTitle_label)).getText().toString();
                // check user pass name if name is not passed when show No name
                if (name.isEmpty()) {
                    name = "No name";
                }
                values.put(ReminderProvider.NAME, name);
                // description that user will pass to database
                String description = ((EditText)findViewById(R.id.reminderDescription_label)).getText().toString();
                // check user pass description if description is not passed when show No description
                if (description.isEmpty()) {
                    description = "No description";
                }
                values.put(ReminderProvider.DESCRIPTION, description);
                // since it is new reminder then status always false
                values.put(ReminderProvider.STATUS, false);
                values.put(ReminderProvider.PRIORITY, priority);
                values.put(ReminderProvider.DUEDATE, dueDate.getTime());
                values.put(ReminderProvider.COMPLETEDDATE, 0);
                // load the uri to insert data
                Uri uri = getContentResolver().insert(ReminderProvider.CONTENT_URI, values);
                // go back to prev screen
                finish();
            }
        });

        // add onclick listener to cancel button
        cancelBtn = (Button) findViewById(R.id.cancelAddNewReminderBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check that btn work when user click cancel
                Log.d("add reminder", "cancel");
                // go back to prev screen
                finish();
            }
        });
    }
}
