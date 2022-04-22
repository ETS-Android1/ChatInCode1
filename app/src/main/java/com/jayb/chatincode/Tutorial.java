package com.jayb.chatincode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class Tutorial extends AppCompatActivity {
    private final String TAG = "TUTORIAL";
    Button homeBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> finish());
    }
}