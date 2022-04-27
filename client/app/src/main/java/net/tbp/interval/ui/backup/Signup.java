package net.tbp.interval.ui.backup;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.interval.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import net.tbp.interval.ui.reminder.ReminderProvider;

public class Signup extends Activity {
    private Button signinBtn;           // button for user to go sign up
    private Button submitBtn;           // button for user to go submit
    private FirebaseAuth mAuth;
    private EditText email, password, confirmPassword;
    private TextView signupStatus;
    String FIREBASETAG = "Firebase";
    protected void onCreate(Bundle savedInstanceState) {
        // set theme for screen
        setTheme(R.style.blackBackground);
        // create the screen
        super.onCreate(savedInstanceState);
        // load content from add_reminder fragment to render the screen
        setContentView(R.layout.sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailSignup);
        password = findViewById(R.id.passwordSignup);
        confirmPassword = findViewById(R.id.confirmPasswordSignup);
        signupStatus = findViewById(R.id.signupStatus);

        //add func to signin button
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

        //add func to submit button
        submitBtn = (Button) findViewById(R.id.submitSignupBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to add sqlite
                ContentValues values = new ContentValues();
                signupStatus.setText("");
                String userEmail = email.getText().toString().trim();
                String userPassword1 = password.getText().toString().trim();
                String userPassword2 = confirmPassword.getText().toString().trim();
                //check condition
                if(TextUtils.isEmpty(userEmail)){
                    email.setError("Email is required.");
                    return;
                }
                if(TextUtils.isEmpty(userPassword1)){
                    password.setError("Password is required.");
                    return;
                }
                if(TextUtils.isEmpty(userPassword2)){
                    confirmPassword.setError("Confirm password is required.");
                    return;
                }
                if(userPassword1.length() < 8){
                    password.setError("Password is required.");
                    return;
                }
                if(!TextUtils.equals(userPassword1, userPassword2)){
                    confirmPassword.setText("");
                    confirmPassword.setError("Password is not match!!");
                }
                if(TextUtils.equals(userPassword1, userPassword2)){
                    // hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    mAuth.createUserWithEmailAndPassword(userEmail, userPassword1).
                            addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(FIREBASETAG, "createUserWithEmail:success.");
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d(FIREBASETAG, user.getUid().toString());
                                signupStatus.setText("Sign up succeeded.");
                                values.put(UserProvider.IDENTIFIER, userEmail);
                                values.put(UserProvider.UID, user.getUid().toString());
                                Uri uri = getContentResolver().insert(UserProvider.CONTENT_URI, values);
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(FIREBASETAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                signupStatus.setText("Failed to create user");
                            }
                        }
                    });
                }
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
