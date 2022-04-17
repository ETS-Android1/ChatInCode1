package com.jayb.chatincode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Objects;

public class SubstitutionCipher extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "SUBSTITUTION_CIPHER";
    private boolean encrypt;
    private Button encryptDecryptBtn, copyBtn, shareBtn, resetBtn, saveBtn;
    private EditText inputTxtBox, keyInput;
    private TextView outputTxtBox;
    private String output = "";
    private String INPUT_KEY = "INPUT_KEY", OUTPUT_KEY = "OUTPUT_KEY", KEYWORD_KEY = "KEYWORD_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_substitution_cipher);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.subsCipherBtn);

        //TODO update to passed extra
        encrypt = false;
        inputTxtBox = findViewById(R.id.inputTxtBox);
        outputTxtBox = findViewById(R.id.outputTxtBox);
        keyInput = findViewById(R.id.keyInput);

        if(savedInstanceState != null) {
            inputTxtBox.setText(savedInstanceState.getString(INPUT_KEY));
            outputTxtBox.setText(savedInstanceState.getString(OUTPUT_KEY));
            keyInput.setText(savedInstanceState.getString(KEYWORD_KEY));
        }

        encryptDecryptBtn = findViewById(R.id.encrypt_decryptBtn);
        saveBtn = findViewById(R.id.saveBtn);
        copyBtn = findViewById(R.id.copyBtn);
        shareBtn = findViewById(R.id.shareBtn);
        resetBtn = findViewById(R.id.resetBtn);

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
        outState.putString(KEYWORD_KEY, keyInput.getText().toString());

        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.encrypt_decryptBtn) {
            String input = inputTxtBox.getText().toString();
            String key = keyInput.getText().toString();
            if(encrypt) {
                output = keySubstitionEncrypt(input, key);
            }
            else {
                output = keySubstitutionDecrypt(input, key);
            }
            if(output != null) {
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
                outputTxtBox.setText(output);
            }
        }
        else if (id == R.id.saveBtn) {
            //TODO add storing in firebase
        }
        else if (id == R.id.copyBtn) {
            //Check to make sure there is something to copy
            if(!output.isEmpty()) {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                //Create clip of output text box contents
                ClipData clip = ClipData.newPlainText("output", output);
                //Set it to the clipboard
                clipboard.setPrimaryClip(clip);
                Toast.makeText(this, "Success!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(this, "No output to copy", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to copy non-existent output");
            }
        }
        else if (id == R.id.shareBtn) {
            //Check to make sure there is something to share
            if(!output.isEmpty()) {
                //Create the intent that will hold the output
                Intent senderIntent = new Intent();
                senderIntent.setAction(Intent.ACTION_SEND);
                senderIntent.putExtra(Intent.EXTRA_TEXT, output);
                senderIntent.setType("text/plain");
                //Attach the previous intent to an intent that opens the Sharesheet
                Intent shareIntent = Intent.createChooser(senderIntent, null);
                //Open Sharesheet options
                startActivity(shareIntent);
            }
            else {
                Toast.makeText(this, "No output to share", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to share non-existent output");
            }
        }
        else if (id == R.id.resetBtn) {
            inputTxtBox.setText("");
            outputTxtBox.setText("");
            output = "";
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }


    private String keySubstitutionDecrypt(String message, String key) {
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: Plaintext message has no content.");
            return null;
        }
        else if (key.isEmpty()) {
            Toast.makeText(this, "Please enter a key.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: key has no content.");
            return null;
        }
        //All characters in the key need to be unique
        else if(checkStringDuplicates(key)) {
            Toast.makeText(this, "No duplicate letters in key.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: key has duplicate letters.");
            return null;
        }

        //Build the cipher alphabet used to decrypt message
        //Create hashmap with cipher alphabet as key to the regular alphabet's values
        HashMap<Character, Character> cipherAlpha = new HashMap<>();
        //Formatting
        key = key.toUpperCase().trim();
        message = message.toUpperCase();
        key = key.toUpperCase().trim();
        //Add the key to the beginning of the Hashmap
        for (int i = 0; i < key.length(); i++) {
            cipherAlpha.put(key.charAt(i), (char)(i + 65));
        }
        //Add the rest of the alphabet
        int asciiCounter = 0;
        System.out.println("Size: " + cipherAlpha.size());
        for(int i = cipherAlpha.size(); i < 26; i++) {
            //We can't add any duplicate letters in the alphabet
            while(key.contains("" + (char)(asciiCounter + 65))) {
                asciiCounter++;
            }
            cipherAlpha.put((char)(asciiCounter + 65), (char) (i + 65));
            asciiCounter++;
        }

        return convertMessage(message, cipherAlpha);
    }

    private boolean checkStringDuplicates(String string) {
        int counter = 1;
        for(char c : string.toCharArray()) {
            for(int i = counter; i < string.length(); i++) {
                if(c == string.charAt(i)) {
                    return true;
                }
                counter++;
            }
        }
        return false;
    }
    //bos23#!19,.8y
    //19 h13f3 s3d0

    private String keySubstitionEncrypt(String message, String key) {
        if (message.isEmpty()) {
            Toast.makeText(this, "Please enter a message.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: Plaintext message has no content.");
            return null;
        }
        else if (key.isEmpty()) {
            Toast.makeText(this, "Please enter a key.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: key has no content.");
            return null;
        }
        //All characters in the key need to be unique
        else if(checkStringDuplicates(key)) {
            Toast.makeText(this, "No duplicate letters in key.", Toast.LENGTH_LONG).show();
            Log.e(TAG, "Error: key has duplicate letters.");
            return null;
        }
        //Build the cipher alphabet used to encrypt message
        //Create hashmap with regular alphabet as key to the cipher alphabet's values
        HashMap<Character, Character> cipherAlpha = new HashMap<>();
        //Formatting
        key = key.toUpperCase().trim();
        message = message.toUpperCase();
        //Add the key to the beginning of the Hashmap
        for (int i = 0; i < key.length(); i++) {
            cipherAlpha.put((char)(i + 65), key.charAt(i));
        }

        //Add the rest of the alphabet
        int asciiCounter = 0;
        for(int i = cipherAlpha.size(); i < 26; i++) {
            //We can't add any duplicate letters in the alphabet
            while(key.contains("" + (char)(asciiCounter + 65))) {
                asciiCounter++;
            }
            cipherAlpha.put((char)(i + 65), (char)(asciiCounter + 65));
            asciiCounter++;
        }
        return convertMessage(message, cipherAlpha);
    }

    private String convertMessage (String message, HashMap<Character, Character> cipherAlpha) {
        char[] messageArr = message.toCharArray();
        int asciiPlain;
        int counter = 0;
        Character newChar;
        for(char c : messageArr ) {
            asciiPlain = c;
            //Keep all punctuation, symbols, and numbers the same
            if (asciiPlain > 64 && asciiPlain < 91) {
                newChar = cipherAlpha.get(c);
                //Handle null edge case
                if(newChar == null) {
                    Toast.makeText(this, "Error. Please try again.", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "Error: cipherAlpha returned null for value: " + c);
                    return null;
                }
                messageArr[counter] = newChar;
            }
            counter++;
        }
        return new String(messageArr);
    }
}