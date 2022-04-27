package net.tbp.interval.ui.backup;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.example.interval.R;

public class SigninSignUp extends Activity{
    String TAG = "SigninSignUpFragment";
    String TAGSQL = "UserSQL";
    private Button signinBtn;       // button for user to go sign in
    private Button signupBtn;       // button for user to go sign up
    int userCount = 0;
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_and_sign_up);
        // load user database
        Cursor uri = getContentResolver().query(UserProvider.CONTENT_URI, null, null, null, null);
        userCount = uri.getCount();
        Log.d("userCount", String.valueOf(userCount));
        if(userCount >= 1){
            finish();
        }
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
