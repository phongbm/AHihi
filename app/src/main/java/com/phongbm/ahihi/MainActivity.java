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
import android.view.View;
import android.widget.ImageView;

import com.phongbm.slidingtab.SlidingTabLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private RecyclerView recyclerViewVNavigation;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DrawerLayout drawerLayoutNavigation;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTabs;
    private ImageView menu;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        initializeComponent();
    }

    private void initializeComponent() {
        // Initialize drawer layout
        drawerLayoutNavigation = (DrawerLayout) findViewById(R.id.drawerLayoutNavigation);

        // Initialize navigation
        linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        navigationAdapter = new NavigationAdapter(MainActivity.this, handler);
        recyclerViewVNavigation = (RecyclerView) findViewById(R.id.recyclerViewNavigation);
        recyclerViewVNavigation.setHasFixedSize(true);
        recyclerViewVNavigation.setAdapter(navigationAdapter);
        recyclerViewVNavigation.setLayoutManager(linearLayoutManager);

        // Initialize view pager
        viewPagerAdapter = new ViewPagerAdapter(MainActivity.this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        // Initialize sliding tab
        slidingTabs = (SlidingTabLayout) findViewById(R.id.slidingTabs);
        slidingTabs.setCustomTabView(R.layout.custom_tab_view, R.id.tabIcon);
        slidingTabs.setDistributeEvenly(true);
        slidingTabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        slidingTabs.setViewPager(viewPager);

        menu = (ImageView) findViewById(R.id.menu);
        menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
        }
    }

}