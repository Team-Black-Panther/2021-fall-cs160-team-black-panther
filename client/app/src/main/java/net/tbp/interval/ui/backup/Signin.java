package net.tbp.interval.ui.backup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.interval.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.tbp.interval.ui.reminder.EditReminder;
import net.tbp.interval.ui.reminder.ReminderProvider;

// class that will use to take care when user signin
public class Signin extends Activity {
    private Button signupBtn;           // button for user to go sign up
    private Button submitBtn;           // button for user to go submit
    private FirebaseAuth mAuth;
    private EditText email, password;
    private TextView signinStatus;
    String FIREBASETAG = "Firebase";
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.sign_in);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailSignin);
        password = findViewById(R.id.passwordSignin);
        signinStatus = findViewById(R.id.signinStatus);
        // sign up button
        signupBtn = (Button) findViewById(R.id.gotoSignupBtn);
        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // initial intent that will use to call Signin class to let user signin their info
                Intent informationIntent = new Intent(v.getContext(), Signup.class);
                startActivity(informationIntent);
                finish();
            }
        });
        //sign in button
        submitBtn = (Button) findViewById(R.id.submitSigninBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to add sqlite
                ContentValues values = new ContentValues();
                signinStatus.setText("");
                String userEmail = email.getText().toString().trim();
                String userPassword = password.getText().toString().trim();
                //check condition
                if(TextUtils.isEmpty(userEmail)){
                    email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(userPassword)){
                    password.setError("Password is required.");
                    return;
                }
                Log.d("email", userEmail);
                mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(FIREBASETAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            signinStatus.setText("Authentication succeeded.");
                            values.put(UserProvider.IDENTIFIER, userEmail);
                            values.put(UserProvider.UID, user.getUid().toString());
                            Uri uri = getContentResolver().insert(UserProvider.CONTENT_URI, values);
                            finish();
                        } else {
                            // hide keyboard
                            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                            // If sign in fails, display a message to the user.
                            Log.w(FIREBASETAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Signin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            signinStatus.setText("Authentication failed.");
                        }
                    }
                });
            }
        });
    }

    // use to hide keyboard
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if(inputMethodManager.isAcceptingText()){
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(),
                    0
            );
        }
    }
}
