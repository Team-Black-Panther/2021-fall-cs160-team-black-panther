package net.tbp.interval.ui.backup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.interval.R;

public class SigninSignUp extends Activity {
    private Button signinBtn;       // button for user to go sign in
    private Button signupBtn;       // button for user to go sign up
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.sign_in_and_sign_up);

        signinBtn = (Button) findViewById(R.id.sign_inBtn);
        signinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initial intent that will use to call Signin class to let user signin their info
                Intent informationIntent = new Intent(v.getContext(), Signin.class);
                startActivity(informationIntent);
                finish();
            }
        });

        signupBtn = (Button) findViewById(R.id.sign_upBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initial intent that will use to call Signup class to render screen to let user signup their info
                Intent informationIntent = new Intent(v.getContext(), Signup.class);
                startActivity(informationIntent);
                finish();
            }
        });
    }
}
