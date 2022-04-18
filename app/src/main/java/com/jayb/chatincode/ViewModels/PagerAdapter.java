package com.jayb.chatincode.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jayb.chatincode.SavedCaesarFragment;
import com.jayb.chatincode.SavedPigLatinFragment;
import com.jayb.chatincode.SavedSubCipherFragment;

public class PagerAdapter extends FragmentStateAdapter {
    private int numOfTabs;
    public PagerAdapter(FragmentActivity fa, int NumOfTabs) {
        super(fa);
        this.numOfTabs = NumOfTabs;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0: return new SavedCaesarFragment();
            case 1: return new SavedPigLatinFragment();
            case 2: return new SavedSubCipherFragment();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }
}
