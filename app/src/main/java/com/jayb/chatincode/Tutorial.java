package com.jayb.chatincode;

import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class Tutorial extends AppCompatActivity {
    Button homeBtn;
    TextView subLink, pigLink, caesLink;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tutorial");

        subLink = findViewById(R.id.subTutLink);
        pigLink = findViewById(R.id.pigTutLink);
        caesLink = findViewById(R.id.caesTutLink);

        subLink.setMovementMethod(LinkMovementMethod.getInstance());
        pigLink.setMovementMethod(LinkMovementMethod.getInstance());
        caesLink.setMovementMethod(LinkMovementMethod.getInstance());

        homeBtn = findViewById(R.id.homeBtn);
        homeBtn.setOnClickListener(v -> finish());
    }
}