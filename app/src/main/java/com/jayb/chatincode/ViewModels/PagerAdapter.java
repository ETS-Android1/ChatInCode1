package com.jayb.chatincode.ViewModels;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jayb.chatincode.SavedPigLatinFragment;

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
            case 0: return new SavedPigLatinFragment();
            //case 1: return new TabFragment2();
            //case 2: return new TabFragment3();
        }
        return new Fragment();
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }
}
