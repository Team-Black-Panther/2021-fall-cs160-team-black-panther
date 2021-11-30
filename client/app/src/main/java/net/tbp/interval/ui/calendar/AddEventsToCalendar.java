package net.tbp.interval.ui.calendar;
import android.app.Activity;
import android.content.ContentValues;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.SeekBar;
import android.widget.TextView;
import com.example.interval.R;
import androidx.constraintlayout.widget.ConstraintLayout;

import net.tbp.interval.ui.calendar.CalendarProvider;

import java.util.Calendar;
import java.util.Date;

public class AddEventsToCalendar extends Activity {
    private Button closeBtn;
    private String[] morningOrAfternoon;
    //****
    private Date duedate = new Date();
    TextView textViewH;
    TextView textViewM;
//    TextView textViewAP;

    protected void onCreate(Bundle savedInstanceState) {

        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_event_on_calendar to render the screen


        setContentView(R.layout.add_event_on_calendar);


        //Hour
        NumberPicker numberPickerH = findViewById(R.id.numPickerHour);
        textViewH = findViewById(R.id.textViewHour);
        numberPickerH.setMinValue(0);
        numberPickerH.setMaxValue(24);

        //Minute
        NumberPicker numberPickerM = findViewById(R.id.numPickerMin);
        textViewM = findViewById(R.id.textViewMinute);
        numberPickerM.setMinValue(0);
        numberPickerM.setMaxValue(59);

//        //AmPm
//        NumberPicker numberPickerAP = findViewById(R.id.numPickerAmPm);
//        textViewAP = findViewById(R.id.textViewAMPM);
//        morningOrAfternoon = getResources().getStringArray(R.array.AmPm);
//        numberPickerAP.setMinValue(1);
//        numberPickerAP.setMaxValue(2);
//        numberPickerAP.setDisplayedValues(morningOrAfternoon);


        numberPickerH.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textViewH.setText("Hour:" + newVal);
            }
        });

        numberPickerM.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                textViewM.setText("Min:" + newVal);
            }
        });

//        numberPickerAP.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
//            @Override
//            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                textViewAP.setText("AP:" + newVal);
//            }
//        });




        final EditText titleEditText = findViewById(R.id.editTextTitle);
        Button saveBtn = findViewById(R.id.saveEventBtnInCalendar);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //String eventTitle = titleEditText.getText().toString().trim();
                //SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences();


                ContentValues values = new ContentValues();
                // name that user will add to the database
                String name = ((EditText)findViewById(R.id.editTextTitle)).getText().toString();
                // check user pass name if name is not passed when show No name
                if (name.isEmpty()) {
                    name = "No name";
                }
                Log.d("add calendar", name);
                values.put(CalendarProvider.NAME, name);
                values.put(CalendarProvider.DUEDATE, duedate.getTime());
                // description that user will pass to database
                //String description = ((EditText)findViewById(R.id.reminderDescription_label)).getText().toString();
                // check user pass description if description is not passed when show No description


                //values.put(CalendarProvider.DESCRIPTION, description);
                // since it is new reminder then status always false
                //values.put(CalendarProvider.STATUS, false);
                //values.put(CalendarProvider.PRIORITY, priority);
                //values.put(CalendarProvider.DUEDATE, dueDate.getTime());
                // load the uri to insert data
                Uri uri = getContentResolver().insert(CalendarProvider.CONTENT_URI, values);
                // go back to prev screen
                finish();


            }
        });

//    numPickerHour.minValue = 1;
//    numPickerHour.maxValue = 12;
//
//    numPickerMin.minValue = 0;
//    numPickerMin.maxValue = 59;
//
//    val str = arrayOf<String>("AM", "PM");
//    numPickerAmPm.displayedValues = str;


        closeBtn = (Button) findViewById(R.id.closeNewEventsBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check that btn work when user click cancel
                Log.d("add calendar", "cancel");
                // go back to prev screen
                finish();
            }
        });

    }



}