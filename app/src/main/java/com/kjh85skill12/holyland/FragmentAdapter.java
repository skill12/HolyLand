package com.kjh85skill12.holyland;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class FragmentAdapter extends FragmentPagerAdapter {

    Fragment[] frags = new Fragment[3];
    String[] titles = new String[]{"Bible","Pilgrim","Comunity"};

    public FragmentAdapter(FragmentManager fm) {
        super(fm);

        frags[0] = new Frag01Bible();
        frags[1] = new Frag02Pilgrim();
        frags[2] = new Frag03PhotoBoard();

    }

    @Override
    public Fragment getItem(int position) {
        return frags[position];
    }

    @Override
    public int getCount() {
        return frags.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
