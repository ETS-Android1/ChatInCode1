package com.jayb.chatincode;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayb.chatincode.ViewModels.CipherAdapter;
import com.jayb.chatincode.ViewModels.CipherViewHolder;
import com.jayb.chatincode.ViewModels.DbHelper;
import com.jayb.chatincode.ViewModels.PagerAdapter;
import com.jayb.chatincode.ViewModels.SavedCipher;

import java.util.LinkedList;

public class SavedCaesarFragment extends Fragment implements View.OnClickListener{
    private String TAG = "SAVED_CAESAR_FRAGMENT";
    private Button copyBtn, shareBtn, deleteBtn, refreshBtn;
    private RecyclerView recyclerView;
    private Context context;
    private CipherAdapter cipherAdapter;
    private LinkedList<SavedCipher> caesarCiphers = new LinkedList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_caesar, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        assert context != null;
        //Create the recycler view
        recyclerView = view.findViewById(R.id.recyclerViewCaes);
        //Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Add lines between items
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //Insert the ciphers
        cipherAdapter = new CipherAdapter(context, caesarCiphers);
        //Set the adapter
        recyclerView.setAdapter(cipherAdapter);
        //Get the ciphers from the db and update the UI
        DbHelper.getSavedMessagesUpdateAdapter("Caesar Shift", cipherAdapter);
        //Setup buttons
        refreshBtn = view.findViewById(R.id.refreshBtn);
        copyBtn = view.findViewById(R.id.copyBtn);
        shareBtn = view.findViewById(R.id.shareBtn);
        deleteBtn = view.findViewById(R.id.deleteBtn);

        refreshBtn.setOnClickListener(this);
        copyBtn.setOnClickListener(this);
        shareBtn.setOnClickListener(this);
        deleteBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if(id == R.id.refreshBtn) {
            //Get the ciphers from the db and update the UI
            DbHelper.getSavedMessagesUpdateAdapter("Pig Latin", cipherAdapter);
        }
        else if (id == R.id.copyBtn) {
            if(!CipherViewHolder.outputCipher.isEmpty()) {
                // Gets a handle to the clipboard service.
                ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                //Create clip of output text box contents
                ClipData clip = ClipData.newPlainText("output", CipherViewHolder.outputCipher);
                //Set it to the clipboard
                clipboard.setPrimaryClip(clip);
                Toast.makeText(context, "Success!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getContext(), "Please select an item.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to copy unselected.");
            }
        }
        else if (id == R.id.deleteBtn) {
            if(!CipherViewHolder.outputCipher.isEmpty()) {
            }
            //TODO delete from db
            else {
                Toast.makeText(getContext(), "Please select an item.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to delete unselected.");
            }
        }
        else if (id == R.id.shareBtn) {
            if(!CipherViewHolder.outputCipher.isEmpty()) {
                //Create the intent that will hold the output
                Intent senderIntent = new Intent();
                senderIntent.setAction(Intent.ACTION_SEND);
                senderIntent.putExtra(Intent.EXTRA_TEXT, CipherViewHolder.outputCipher);
                senderIntent.setType("text/plain");
                //Attach the previous intent to an intent that opens the Sharesheet
                Intent shareIntent = Intent.createChooser(senderIntent, null);
                //Open Sharesheet options
                startActivity(shareIntent);
            }
            else {
                Toast.makeText(getContext(), "Please select an item.", Toast.LENGTH_LONG).show();
                Log.e(TAG, "Error: Attempt to share unselected.");
            }
        }
        //Edge case error
        else {
            Log.e(TAG, "Error: id in onClick not recognized. ID: " + id);
        }
    }
}