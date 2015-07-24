package com.phongbm.ahihi;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUMBER_OF_FRAGMENT = 4;

    private TabOneFragment tabOneFragment = new TabOneFragment();
    private TabTwoFragment tabTwoFragment = new TabTwoFragment();
    private TabThreeFragment tabThreeFragment = new TabThreeFragment();
    private TabFourFragment tabFourFragment = new TabFourFragment();
    private int[] tabIconIds = new int[]{R.drawable.ic_tab_message, R.drawable.ic_tab_contact,
            R.drawable.ic_tab_friend, R.drawable.ic_tab_info};

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return tabOneFragment;
            case 1:
                return tabTwoFragment;
            case 2:
                return tabThreeFragment;
            case 3:
                return tabFourFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

    public int getPageIcon(int position) {
        return tabIconIds[position];
    }

}