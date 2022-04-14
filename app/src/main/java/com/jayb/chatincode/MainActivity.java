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

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private FirebaseAuth mAuth;
    private final String TAG = "MAIN_ACTIVITY";
    private EditText nameInput, passInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        nameInput = findViewById(R.id.loginNameInput);
        passInput = findViewById(R.id.loginPassInput);
        TextView needAccText = findViewById(R.id.noAccTxt);
        Button signInBtn = findViewById(R.id.loginBtn);

        needAccText.setOnClickListener(this);
        signInBtn.setOnClickListener(this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        FirebaseAuth.getInstance().signOut();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.loginBtn) {
            String username = nameInput.getText().toString();
            String password = passInput.getText().toString();
            if(username.isEmpty() || password.isEmpty()) {
                Log.e(TAG, "Sign-in attempt with missing information. Sign-in aborted.");
                Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            }
            else {
                signInUser(username, password);
            }
        }
        else if (id == R.id.noAccTxt) {
            Intent createAccIntent = new Intent(MainActivity.this, CreateAccountActivity.class);
            startActivity(createAccIntent);
        }
    }


    //Firebase documentation provided authentication code outline to follow https://firebase.google.com/docs/auth/android/password-auth?authuser=0&hl=en
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Intent homeIntent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        }
    }

    private void signInUser (String email, String password) {
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
        if(user == null) {
            Toast.makeText(this, "Error logging in. Please try again.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent homeIntent = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        }
    }

}