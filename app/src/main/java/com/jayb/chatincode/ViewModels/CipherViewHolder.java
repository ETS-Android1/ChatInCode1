package com.jayb.chatincode.ViewModels;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayb.chatincode.R;

public class CipherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView dateTxt;
    public TextView nameTxt;
    public TextView cipherTxt;
    final CipherAdapter adapter;

    public CipherViewHolder(@NonNull View itemView, CipherAdapter adapter) {
        super(itemView);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        nameTxt = itemView.findViewById(R.id.nameTxt);
        cipherTxt = itemView.findViewById(R.id.cipherTxt);
        this.adapter = adapter;
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        adapter.notifyDataSetChanged();
    }
}
