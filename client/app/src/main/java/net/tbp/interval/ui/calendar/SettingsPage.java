package net.tbp.interval.ui.calendar;

import android.app.Activity;
import android.os.Bundle;

import com.example.interval.R;

public class SettingsPage extends Activity {



    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_Interval);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.color_change_popup);
    }
}
