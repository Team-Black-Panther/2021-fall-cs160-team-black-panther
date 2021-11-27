package net.tbp.interval.ui.backup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.interval.R;

public class Signup extends Activity {
    private Button signinBtn;           // button for user to go sign up
    private Button submitBtn;           // button for user to go submit
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.sign_up);

        signinBtn = (Button) findViewById(R.id.gotoSigninBtn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initial intent that will use to call Signin class to let user signin their info
                Intent informationIntent = new Intent(v.getContext(), Signin.class);
                startActivity(informationIntent);
                finish();
            }
        });

        submitBtn = (Button) findViewById(R.id.submitSignupBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }


}
