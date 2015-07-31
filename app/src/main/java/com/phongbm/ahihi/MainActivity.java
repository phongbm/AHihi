package com.phongbm.ahihi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phongbm.slidingtab.SlidingTabLayout;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private RecyclerView recyclerViewVNavigation;
    private NavigationAdapter navigationAdapter;
    private LinearLayoutManager linearLayoutManager;
    private DrawerLayout drawerLayoutNavigation;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTabs;
    private ImageView menu, addFriend;
    private TextView txtInternet, txtTitlePage;
    private InputMethodManager inputMethodManager;
    private FriendItem friend;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommonValue.START_ACCOUNT_MANAGEMENT:
                    Intent intentAM = new Intent(MainActivity.this, AccountManagementActivity.class);
                    MainActivity.this.startActivity(intentAM);
                    break;
                case CommonValue.REQUEST_ADD_FRIEND:
                    friend = new FriendItem("100", (String) msg.obj);
                    Intent i = new Intent();
                    i.setAction("UPDATE_LIST_FRIEND");
                    sendBroadcast(i);
                    break;
            }
            // drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            // drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    };

    public static final boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : true;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        initializeComponent();
        Intent i = new Intent();
        i.setClassName("com.phongbm.ahihi", "com.phongbm.call.MyServiceCall");
        startService(i);

    }

    private void initializeComponent() {
        inputMethodManager = (InputMethodManager) MainActivity.this.
                getSystemService(Context.INPUT_METHOD_SERVICE);

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
        viewPagerAdapter = new ViewPagerAdapter(this, this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                txtTitlePage.setText(viewPagerAdapter.getPageTitle(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

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
        addFriend = (ImageView) findViewById(R.id.addFriend);
        addFriend.setOnClickListener(this);
        txtInternet = (TextView) findViewById(R.id.txtInternet);
        if (!isNetworkConnected(this)) {
            txtInternet.setVisibility(LinearLayout.VISIBLE);
        }
        txtTitlePage = (TextView) findViewById(R.id.txtTitlePage);
        txtTitlePage.setText(viewPagerAdapter.getPageTitle(0));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu:
                drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                drawerLayoutNavigation.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case R.id.addFriend:
                AddFriendDialog addFriendDialog = new AddFriendDialog(MainActivity.this, handler);
                addFriendDialog.show();
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public FriendItem getFriend() {
        return friend;
    }

}