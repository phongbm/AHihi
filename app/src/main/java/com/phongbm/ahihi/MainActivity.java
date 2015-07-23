package com.phongbm.ahihi;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.phongbm.slidingtab.SlidingTabLayout;

public class MainActivity extends FragmentActivity {
    private RecyclerView recyclerViewVNavigation;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DrawerLayout drawerLayoutNavigation;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTabs;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);

        navigationAdapter = new NavigationAdapter(MainActivity.this, handler);
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerViewVNavigation = (RecyclerView) findViewById(R.id.recyclerViewNavigation);
        recyclerViewVNavigation.setHasFixedSize(true);
        recyclerViewVNavigation.setAdapter(navigationAdapter);
        recyclerViewVNavigation.setLayoutManager(linearLayoutManager);

        drawerLayoutNavigation = (DrawerLayout) findViewById(R.id.drawerLayoutNavigation);

        viewPagerAdapter = new ViewPagerAdapter(MainActivity.this, MainActivity.this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        slidingTabs = (SlidingTabLayout) findViewById(R.id.slidingTabs);
        slidingTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabText);
        slidingTabs.setDistributeEvenly(true);
        slidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.parseColor("#2196f3");
            }
        });
        slidingTabs.setViewPager(viewPager);
    }

}