package com.phongbm.ahihi;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    private static final int NUMBER_OF_FRAGMENT = 3;

    private Context context;
    private int[] icons = new int[]{R.drawable.ic_tab_message, R.drawable.ic_tab_friend,
            R.drawable.ic_tab_info};
    private TabOneFragment tabOneFragment = new TabOneFragment();
    private TabTwoFragment tabTwoFragment = new TabTwoFragment();
    private TabThreeFragment tabThreeFragment = new TabThreeFragment();

    public ViewPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);
        this.context = context;
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
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUMBER_OF_FRAGMENT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        Drawable drawable = context.getResources().getDrawable(icons[position]);
        drawable.setBounds(0, 0, 80, 80);
        ImageSpan imageSpan = new ImageSpan(drawable);
        SpannableString spannableString = new SpannableString(" ");
        spannableString.setSpan(imageSpan, 0, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

}