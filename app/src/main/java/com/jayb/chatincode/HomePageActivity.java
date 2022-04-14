package com.jayb.chatincode;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "HOME_PAGE";
    Button pigLatBtn, caesShiftBtn, subCiphBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pigLatBtn = findViewById(R.id.pigLatinBtn);
        caesShiftBtn = findViewById(R.id.caesShiftBtn);
        subCiphBtn = findViewById(R.id.subCiphBtn);

        pigLatBtn.setOnClickListener(this);
        caesShiftBtn.setOnClickListener(this);
        subCiphBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.pigLatinBtn) {
            Intent pigIntent = new Intent(HomePageActivity.this, PigLatinActivity.class);
            startActivity(pigIntent);
        }
        else if (id == R.id.caesShiftBtn) {
            Intent pigIntent = new Intent(HomePageActivity.this, CaesarShift.class);
            startActivity(pigIntent);
        }
        else if (id == R.id.subCiphBtn) {
            Intent pigIntent = new Intent(HomePageActivity.this, SubstitutionCipher.class);
            startActivity(pigIntent);
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }
}