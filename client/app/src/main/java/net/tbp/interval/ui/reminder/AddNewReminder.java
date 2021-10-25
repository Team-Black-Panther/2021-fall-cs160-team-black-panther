package net.tbp.interval.ui.reminder;

import android.app.Activity;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.navigation.ui.AppBarConfiguration;

import com.example.interval.R;
import com.example.interval.databinding.ActivityMainBinding;

import net.tbp.interval.MainActivity;

// This class will use to add new reminder
public class AddNewReminder extends Activity {
    private Button saveBtn;         // button for user to save new reminder
    private Button cancelBtn;       // button for user to cancel and go back to reminder screen
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.Theme_Interval);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.add_reminder);

        // add onclick listener to save button
        saveBtn = (Button) findViewById(R.id.saveAddNewReminderBtn);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to show console that btn work when user click
                ContentValues values = new ContentValues();
                // title that user will add to the database
                String title = ((EditText)findViewById(R.id.reminderTitle_label)).getText().toString();
                // check user pass title if title is not passed when show No title
                if (title.isEmpty()) {
                    title = "No title";
                }
                values.put(ReminderProvider.TITLE, title);
                // description that user will pass to database
                String description = ((EditText)findViewById(R.id.reminderDescription_label)).getText().toString();
                // check user pass description if description is not passed when show No description
                if (description.isEmpty()) {
                    description = "No description";
                }
                values.put(ReminderProvider.DESCRIPTION, description);
                // since it is new reminder then status always false
                values.put(ReminderProvider.STATUS, false);
                // load the uri to insert data
                Uri uri = getContentResolver().insert(ReminderProvider.CONTENT_URI, values);
                // console to output that user press save
                Log.d("add reminder", "save");
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
