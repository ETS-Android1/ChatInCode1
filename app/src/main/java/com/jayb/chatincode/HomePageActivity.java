package com.jayb.chatincode;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.jayb.chatincode.ViewModels.DbHelper;
import com.jayb.chatincode.ViewModels.SavedCipher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;
import java.util.Objects;

public class HomePageActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG = "HOME_PAGE";
    Button pigLatBtn, caesShiftBtn, subCiphBtn, logOutBtn, savedBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        pigLatBtn = findViewById(R.id.pigLatinBtn);
        caesShiftBtn = findViewById(R.id.caesShiftBtn);
        subCiphBtn = findViewById(R.id.subCiphBtn);
        logOutBtn = findViewById(R.id.logOutBtn);
        savedBtn = findViewById(R.id.savedBtn);

        pigLatBtn.setOnClickListener(this);
        caesShiftBtn.setOnClickListener(this);
        subCiphBtn.setOnClickListener(this);
        logOutBtn.setOnClickListener(this);
        savedBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if(id == R.id.pigLatinBtn) {
            Intent pigIntent = new Intent(HomePageActivity.this, PigLatinActivity.class);
            startActivity(pigIntent);
        }
        else if (id == R.id.caesShiftBtn) {
            Intent caesIntent = new Intent(HomePageActivity.this, CaesarShift.class);
            startActivity(caesIntent);
        }
        else if (id == R.id.subCiphBtn) {
            Intent subIntent = new Intent(HomePageActivity.this, SubstitutionCipher.class);
            startActivity(subIntent);
        }
        else if(id == R.id.logOutBtn) {
            DbHelper.logOutCurrUser();
            finish();
        }
        else if(id == R.id.savedBtn) {
            Intent savedIntent = new Intent(HomePageActivity.this, Saved_Ciphers.class);
            startActivity(savedIntent);
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }
}