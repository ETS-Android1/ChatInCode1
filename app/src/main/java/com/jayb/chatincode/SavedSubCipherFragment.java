package com.jayb.chatincode;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jayb.chatincode.ViewModels.CipherAdapter;
import com.jayb.chatincode.ViewModels.DbHelper;
import com.jayb.chatincode.ViewModels.SavedCipher;

import java.util.LinkedList;

public class SavedSubCipherFragment extends Fragment {
    private RecyclerView recyclerView;
    private CipherAdapter cipherAdapter;
    private LinkedList<SavedCipher> subCiphers = new LinkedList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_saved_sub_cipher, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        assert context != null;
        //Get the ciphers from the db
        subCiphers = DbHelper.getSavedMessages("SubstitutionCipher");
        //Create the recycler view
        recyclerView = view.findViewById(R.id.recyclerViewSub);
        //Give the RecyclerView a default layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //Add lines between items
        recyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL));
        //Insert the ciphers
        cipherAdapter = new CipherAdapter(context, subCiphers);
        //Set the adapter
        recyclerView.setAdapter(cipherAdapter);
    }
}