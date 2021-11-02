package net.tbp.interval.ui.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;


import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.interval.R;

public class ColorSettings extends Activity {

    private int SeekbarR,SeekbarG,SeekbarB;
    SeekBar red_Bar, green_Bar, blue_Bar;
    ConstraintLayout mScreen;
    private Button closeBtn;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.color_change_popup);


        mScreen = findViewById(R.id.myScreen);
        red_Bar = findViewById(R.id.bar_red);
        green_Bar = findViewById(R.id.bar_green);
        blue_Bar = findViewById(R.id.bar_blue);

        BackgroundColorUpdate();


        red_Bar.setOnSeekBarChangeListener(seekBarChangeListener);
        green_Bar.setOnSeekBarChangeListener(seekBarChangeListener);
        blue_Bar.setOnSeekBarChangeListener(seekBarChangeListener);

        closeBtn = (Button) findViewById(R.id.txtclose);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to check that btn work when user click cancel
                Log.d("add reminder", "cancel");
                // go back to prev screen
                finish();
            }
        });
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            BackgroundColorUpdate();

        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }


    };

    private void BackgroundColorUpdate() {
        SeekbarR = red_Bar.getProgress();
        SeekbarG = green_Bar.getProgress();
        SeekbarB = blue_Bar.getProgress();


        mScreen.setBackgroundColor(
                0xff000000
                + SeekbarR * 0x10000
                + SeekbarG * 0x100
                + SeekbarB

        );


    }
}
