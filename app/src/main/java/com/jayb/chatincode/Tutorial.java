package com.jayb.chatincode;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Tutorial extends AppCompatActivity {
    Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutorial");

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> finish());
    }
}