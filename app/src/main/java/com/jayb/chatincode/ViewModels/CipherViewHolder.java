package com.jayb.chatincode.ViewModels;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jayb.chatincode.R;


//TODO We lose selected when orientation changes
public class CipherViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    //We can ignore the possibility of a memory leak as it is not relevant to this use-case
    @SuppressLint("StaticFieldLeak")
    public static View viewSelected;
    public static String outputCipher = "", savedName = "";
    public static int position = -1;
    final CipherAdapter adapter;
    public TextView dateTxt;
    public TextView nameTxt;
    public TextView cipherTxt;

    public CipherViewHolder(@NonNull View itemView, CipherAdapter adapter) {
        super(itemView);
        dateTxt = itemView.findViewById(R.id.dateTxt);
        nameTxt = itemView.findViewById(R.id.nameTxt);
        cipherTxt = itemView.findViewById(R.id.cipherTxt);
        this.adapter = adapter;
        itemView.setTag(adapter.getItemCount());
        itemView.setBackgroundResource(R.color.palette_silver);
        itemView.setOnClickListener(this);
    }

    public static void setViewSelectedOrientationChange(View v) {
        viewSelected = v;
        viewSelected.setBackgroundResource(R.color.palette_mid);
    }

    public static void clearSelected() {
        if (viewSelected != null) {
            viewSelected.setBackgroundResource(R.color.palette_silver);
        }
        outputCipher = "";
        viewSelected = null;
        savedName = "";
        position = -1;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onClick(View v) {
        //Deselect previous (if exists)
        if (viewSelected == null) {
            viewSelected = v;
        } else {
            viewSelected.setBackgroundResource(R.color.palette_silver);
        }
        //Set selected
        viewSelected = v;
        viewSelected.setBackgroundResource(R.color.palette_mid);
        outputCipher = cipherTxt.getText().toString();
        savedName = nameTxt.getText().toString();
        position = (int) v.getTag();
        adapter.notifyDataSetChanged();
    }


}
