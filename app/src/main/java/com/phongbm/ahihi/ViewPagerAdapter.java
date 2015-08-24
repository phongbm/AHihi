package com.phongbm.ahihi;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerAdapter extends FragmentPagerAdapter {
<<<<<<< HEAD
=======
    private int[] tabIconIds = new int[]{R.drawable.bg_tab_message, R.drawable.bg_tab_contact,
            R.drawable.bg_tab_friend, R.drawable.bg_tab_info};

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    private ArrayList<Fragment> fragments;

    public ViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new TabOneFragment(context));
        fragments.add(new TabContactFragment(context));
        fragments.add(new TabFriendFragment(context));
        fragments.add(new TabFourFragment(context));
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }

<<<<<<< HEAD
=======
    public int getPageIcon(int position) {
        return tabIconIds[position];
    }

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
}