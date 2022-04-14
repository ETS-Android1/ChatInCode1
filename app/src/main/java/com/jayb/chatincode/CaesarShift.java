package com.jayb.chatincode;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CaesarShift extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caesar_shift);
        //Insert values from string resource in to spinner
        Spinner caesSpinner = (Spinner) findViewById(R.id.caesar_shift_spinner);
        ArrayAdapter<CharSequence> arrAdapt = ArrayAdapter.createFromResource(this, R.array.caesar_shift_array, android.R.layout.simple_spinner_item);
        arrAdapt.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        caesSpinner.setAdapter(arrAdapt);
        caesSpinner.setAdapter(arrAdapt);
    }
}