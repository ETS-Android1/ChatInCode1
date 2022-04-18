package com.jayb.chatincode.ViewModels;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.jayb.chatincode.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class CipherAdapter extends RecyclerView.Adapter<CipherViewHolder>{
    private Context context;
    private LinkedList<SavedCipher> cipherList;
    private final LayoutInflater inflater;

    public CipherAdapter(Context context, LinkedList<SavedCipher> cipherList) {
        inflater = LayoutInflater.from(context);
        this.cipherList = cipherList;
        this.context = context;
    }


    @NonNull
    @Override
    public CipherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.list_item, parent, false);
        return new CipherViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CipherViewHolder holder, int position) {
        //Format the saveDate for displaying
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss z");
        Date date = cipherList.get(position).getDateCreated();
        String dateString = f.format(date);
        //Extract data from list and set txtViews
        holder.dateTxt.setText(dateString);
        holder.nameTxt.setText(cipherList.get(position).getSaveName());
        holder.cipherTxt.setText(cipherList.get(position).getCipher());
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCiphers(LinkedList<SavedCipher> cipherList) {
        if (cipherList != null && cipherList.size() > 0) {
            this.cipherList = cipherList;
            Log.d("AHH", String.valueOf(cipherList.size()));
            notifyDataSetChanged();
        }
    }

    @Override
    public int getItemCount() {
        return cipherList.size();
    }
}
