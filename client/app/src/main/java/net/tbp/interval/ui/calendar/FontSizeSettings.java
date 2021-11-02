//package net.tbp.interval.ui.calendar;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.SeekBar;
//
//import com.example.interval.R;
//
//public class FontSizeSettings extends Activity{
//
//
//    TextView tV;
//    SeekBar sB;
//    int seekValue;
//
//
//    protected void onCreate(Bundle savedInstanceState) {
//        setTheme(R.style.Theme_Interval);
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.font_size_change_popup);
//
//
//        tV = findViewById();
//        sB = findViewById(R.id.seekBar);
//
//
//        sB.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                seekValue = progress;
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//                String temp = "Processing. . .";
//                tV.setText(temp);
//            }
//
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//                tV.setText(tV.getText().toString());
//                tV.setTextSize(seekValue);
//            }
//        }
//    }
//}