package com.jayb.chatincode;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

//Main icon source - https://icons8.com/icon/NZg1FdzqSycS/bill-cipher - Bill Cipher icon by Icons8
/*TODO
* Tablet sizes can be a little weird
* On Pig fragment and Sub fragment after delete activity closes
* Some devices won't load the encryption activities (Likely solved)
* Change Font family for Title
* Limit the amount of chars for Saved Name
* Format the save display a little nicer
* Reset doesn't clear all text from some activities
*
* */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MAIN_ACTIVITY";
    private final String EMAIL_KEY = "EMAIL_KEY";
    private final String PASS_KEY = "PASS_KEY";
    private FirebaseAuth mAuth;
    private EditText emailInput, passInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.loginBtn);

        mAuth = FirebaseAuth.getInstance();
        emailInput = findViewById(R.id.loginEmailInput);
        passInput = findViewById(R.id.loginPassInput);

        if (savedInstanceState != null) {
            emailInput.setText(savedInstanceState.getString(EMAIL_KEY));
            passInput.setText(savedInstanceState.getString(PASS_KEY));
        }
        TextView needAccText = findViewById(R.id.noAccTxt);
        Button signInBtn = findViewById(R.id.loginBtn);

        needAccText.setOnClickListener(this);
        signInBtn.setOnClickListener(this);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(EMAIL_KEY, emailInput.getText().toString());
        outState.putString(PASS_KEY, passInput.getText().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.loginBtn) {
            String username = emailInput.getText().toString();
            String password = passInput.getText().toString();
            if (username.isEmpty() || password.isEmpty()) {
                Log.e(TAG, "Sign-in attempt with missing information. Sign-in aborted.");
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            } else {
                signInUser(username, password);
            }
        } else if (id == R.id.noAccTxt) {
            Intent createAccIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(createAccIntent);
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }


    //Firebase documentation provided authentication code outline to follow https://firebase.google.com/docs/auth/android/password-auth?authuser=0&hl=en
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent homeIntent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        }
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        userLoggedIn(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        userLoggedIn(null);
                    }
                });
    }

    private void userLoggedIn(FirebaseUser user) {
        if (user == null) {
            Toast.makeText(this, "Error logging in. Please try again.", Toast.LENGTH_SHORT).show();
        } else {
            Intent homeIntent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        }
    }

}