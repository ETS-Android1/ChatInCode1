package com.jayb.chatincode;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jayb.chatincode.ViewModels.PagerAdapter;

public class Saved_Ciphers extends AppCompatActivity {
    private final String TAG = "SAVED_CIPHERS";
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_ciphers);
        tabLayout = findViewById(R.id.tabLayout);

        // Set the text for each tab.
        tabLayout.addTab(tabLayout.newTab().setText(R.string.caesarShiftTitle));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.pigLatinTitle));
        tabLayout.addTab(tabLayout.newTab().setText(R.string.subCipherTitle));

        // Set the tabs to fill the entire layout.
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPager2 = findViewById(R.id.viewPager2);
        final PagerAdapter adapter = new PagerAdapter(this, tabLayout.getTabCount());
        viewPager2.setAdapter(adapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
            }
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                viewPager2.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
            }
        });
        new TabLayoutMediator(tabLayout, viewPager2, ((tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText(R.string.caesarShiftTitle);
                    break;
                case 1:
                    tab.setText(R.string.pigLatinTitle);
                    break;
                case 2:
                    tab.setText(R.string.subCipherTitle);
                    break;
            }
        })).attach();
    }

}