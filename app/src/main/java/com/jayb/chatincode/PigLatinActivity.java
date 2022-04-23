package com.jayb.chatincode;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.jayb.chatincode.ViewModels.DbHelper;

import java.util.Objects;

public class PigLatinActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "PIG_LATIN";
    private final String INPUT_KEY = "INPUT_KEY";
    private final String OUTPUT_KEY = "OUTPUT_KEY";
    private boolean encrypt;
    private EditText inputTxtBox;
    private TextView outputTxtBox;
    private String output = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pig_latin);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.pigLatinBtn);

        String ENCRYPT_KEY = "ENCRYPT_KEY";
        encrypt = getIntent().getExtras().getBoolean(ENCRYPT_KEY);
        inputTxtBox = findViewById(R.id.inputTxtBox);
        outputTxtBox = findViewById(R.id.outputTxtBox);
        TextView inputLbl = findViewById(R.id.inputLbl);
        TextView outputLbl = findViewById(R.id.outputLbl);

        if (savedInstanceState != null) {
            inputTxtBox.setText(savedInstanceState.getString(INPUT_KEY));
            output = savedInstanceState.getString(OUTPUT_KEY);
            outputTxtBox.setText(output);
            encrypt = savedInstanceState.getBoolean(ENCRYPT_KEY);
        }

        Button encryptDecryptBtn = findViewById(R.id.encrypt_decryptBtn);
        if (!encrypt) {
            encryptDecryptBtn.setText(R.string.decryptRadBtn);
            inputLbl.setText(R.string.yourCiphLbl);
            outputLbl.setText(R.string.yourMessageLbl);
            inputTxtBox.setHint(R.string.enterCiphHint);
        }
        Button saveBtn = findViewById(R.id.saveBtn);
        Button copyBtn = findViewById(R.id.copyBtn);
        Button shareBtn = findViewById(R.id.shareBtn);
        Button resetBtn = findViewById(R.id.deleteBtn);

        encryptDecryptBtn.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        resetBtn.setOnClickListener(this);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString(INPUT_KEY, inputTxtBox.getText().toString());
        outState.putString(OUTPUT_KEY, outputTxtBox.getText().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.encrypt_decryptBtn) {
            String input = inputTxtBox.getText().toString();
            if (encrypt) {
                output = pigLatinEncrypt(input);
            } else {
                output = pigLatinDecrypt(input);
            }
            if (output != null) {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                outputTxtBox.setText(output);
            }
        } else if (id == R.id.saveBtn) {
            if (!output.isEmpty()) {
                //Get the name to save it under
                final String[] savedName = {""};
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Save As: ");
                EditText inputBox = new EditText(this);
                inputBox.setInputType(InputType.TYPE_CLASS_TEXT);
                builder.setView(inputBox);
                builder.setPositiveButton("Save", (dialog, which) -> {
                    savedName[0] = inputBox.getText().toString();
                    if (savedName[0].isEmpty()) {
                        Toast.makeText(PigLatinActivity.this, "Name can't be blank", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    DbHelper.addCipherToDb(savedName[0], output, "Pig Latin", PigLatinActivity.this);

                });
                builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                builder.show();
            } else {
                Toast.makeText(this, "No output to save", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to save non-existent output");
            }
        } else if (id == R.id.copyBtn) {
            //Check to make sure there is something to copy
            if (!output.isEmpty()) {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //Create clip of output text box contents
                ClipData clip = ClipData.newPlainText("output", output);
                //Set it to the clipboard
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "No output to copy", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to copy non-existent output");
            }
        } else if (id == R.id.shareBtn) {
            //Check to make sure there is something to share
            if (!output.isEmpty()) {
                //Create the intent that will hold the output
                Intent senderIntent = new Intent();
                senderIntent.setAction(Intent.ACTION_SEND);
                senderIntent.putExtra(Intent.EXTRA_TEXT, output);
                senderIntent.setType("text/plain");
                //Attach the previous intent to an intent that opens the Sharesheet
                Intent shareIntent = Intent.createChooser(senderIntent, null);
                //Open Sharesheet options
                startActivity(shareIntent);
            } else {
                Toast.makeText(this, "No output to share", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to share non-existent output");
            }
        } else if (id == R.id.deleteBtn) {
            inputTxtBox.setText("");
            outputTxtBox.setText("");
            output = "";
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }

    private String pigLatinDecrypt(String input) {
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: Plaintext message has no content.");
            return null;
        }
        //Keep formatting consistent
        String[] words = input.toUpperCase().trim().split(" ");
        int counter = 0;
        for (String word : words) {
            String[] temp = word.split("-");
            String lastWord = temp[1];
            Log.d("WHYOHWHY", "lastWord is " + lastWord);
            //Handle the word only needing to remove "way" (started with a vowel during encryption)
            if (lastWord.equals("WAY")) {
                words[counter] = word.substring(0, (word.length() - 4));
            } else {
                //Remove the ay
                lastWord = lastWord.substring(0, lastWord.length() - 2);
                words[counter] = lastWord + temp[0];
            }
            counter++;
        }
        return TextUtils.join(" ", words);
    }


    private String pigLatinEncrypt(String input) {
        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: Plaintext message has no content.");
            return null;
        }
        String vowels = "AEIOU";
        //Keep formatting consistent
        String[] words = input.toUpperCase().trim().split(" ");
        int counter = 0;
        for (String word : words) {
            char[] letters = word.toCharArray();
            if(letters.length == 0) { continue; }
            boolean firstVowel = vowels.contains("" + letters[0]);
            if (!firstVowel) {
                int index = 0;
                //Find the first vowel
                for (int i = 1; i < word.length(); i++) {
                    if (vowels.contains("" + letters[i])) {
                        index = i;
                        break;
                    }
                }
                //Move up to the first vowel to the end of the word and add "ay"
                words[counter] = word.substring(index) + "-" + word.substring(0, index) + "AY";
            } else {
                //If a word starts with a vowel add the word "way" at the end of the word.
                words[counter] += "-WAY";
            }
            counter++;
        }
        return TextUtils.join(" ", words);
    }
}
// IH-AY OSHJ-AY OWH-AY ARE-WAY OUY-AY UDED-AY