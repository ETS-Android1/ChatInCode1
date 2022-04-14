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

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    private final String TAG = "CREATE_ACCOUNT_ACTIVITY";
    Button createAccBtn;
    EditText nameInput;
    EditText passInput;
    EditText passConfInput;
    TextView haveAccTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        mAuth = FirebaseAuth.getInstance();

        nameInput = findViewById(R.id.createNameInput);
        passInput = findViewById(R.id.createPassInput);
        passConfInput = findViewById(R.id.confirmPassInput);
        haveAccTxt = findViewById(R.id.haveAccTxt);
        createAccBtn = findViewById(R.id.createAccBtn);

        createAccBtn.setOnClickListener(this);
        haveAccTxt.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        String username = nameInput.getText().toString();
        String pass = passInput.getText().toString();
        String passConf = passConfInput.getText().toString();
        if(id == R.id.createAccBtn) {
            //Validate entries
            if(validateUserInput(username, pass, passConf)) {
                createNewUser(username, pass);
            }
        }
        else if (id == R.id.haveAccTxt) {
            Intent signInIntent = new Intent(CreateAccountActivity.this, MainActivity.class);
            startActivity(signInIntent);
        }
    }

    private boolean validateUserInput(String username, String pass, String passConf) {

        if(username.isEmpty() || pass.isEmpty() || passConf.isEmpty()) {
            Log.e(TAG, "Create account attempt with missing information. Create account aborted.");
            Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_LONG).show();
            return false;
        }
        else if (!pass.equals(passConf)) {
            Log.e(TAG, "Create account attempt with mismatch passwords. Create account aborted.");
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_LONG).show();
            return false;
        }
        return isValidPassword(pass);


    }

    private boolean isValidPassword(String pass) {
        //Needs to be 8 chars long, and at least one uppercase, one lowercase, one special character (This is a cryptography app after all :) )
        return pass.length() >= 8 && pass.matches("^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$");
    }


    //Firebase documentation provided authentication code outline to follow https://firebase.google.com/docs/auth/android/password-auth?authuser=0&hl=en
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            finish();
        }
    }

    private void createNewUser(String username, String password) {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "createUserWithEmail:success. User: " + username);
                        FirebaseUser user = mAuth.getCurrentUser();
                        userSignedIn(user);
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.e(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(CreateAccountActivity.this, "Authentication failed.", Toast.LENGTH_LONG).show();

                        userSignedIn(null);
                    }
                });
    }

    private void userSignedIn(FirebaseUser user) {
        if(user == null) {
            Toast.makeText(this, "Error creating user. Please try again.", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent homeIntent = new Intent(CreateAccountActivity.this, HomePageActivity.class);
            startActivity(homeIntent);
        }
    }
}