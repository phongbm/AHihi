package com.phongbm.ahihi;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.TextView;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.parse.ParseUser;
import com.phongbm.slidingtab.SlidingTabLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private SlidingTabLayout slidingTab;
    private InputMethodManager inputMethodManager;
    private FriendItem friend;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommonValue.REQUEST_ADD_FRIEND:
                    friend = new FriendItem("100", (String) msg.obj);
                    Intent i = new Intent();
                    i.setAction("UPDATE_LIST_FRIEND");
                    sendBroadcast(i);
                    break;
            }
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
        initializeToolbar();
        initializeComponent();
        //startService();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeComponent() {
        inputMethodManager = (InputMethodManager) MainActivity.this.
                getSystemService(Context.INPUT_METHOD_SERVICE);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        viewPagerAdapter = new ViewPagerAdapter(this, this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        slidingTab = (SlidingTabLayout) findViewById(R.id.slidingTab);
        slidingTab.setCustomTabView(R.layout.custom_tab_view, R.id.tabIcon);
        slidingTab.setDistributeEvenly(true);
        slidingTab.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return Color.WHITE;
            }
        });
        slidingTab.setViewPager(viewPager);

        initializeFloatingMenu();
        initializeProfileInformation();
    }

    private void initializeProfileInformation() {
        View header = navigationView.getChildAt(0);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        txtName.setText((String) ParseUser.getCurrentUser().get("fullName"));
        TextView txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        txtEmail.setText("phongbm.it@gmail.com");
    }

    /* private void startService() {
        Intent intentStartService = new Intent();
        intentStartService.setClassName("com.phongbm.ahihi", "com.phongbm.call.MyServiceCall");
        startService(intentStartService);
    }*/

    private void initializeFloatingMenu() {
        int padding = getResources().getDimensionPixelSize(R.dimen.red_button_content_padding);
        int size = getResources().getDimensionPixelSize(R.dimen.blue_button_size);
        int margin = getResources().getDimensionPixelSize(R.dimen.blue_button_content_margin);

        ImageView imgMainMenu = new ImageView(this);
        imgMainMenu.setImageResource(R.drawable.ic_plus);
        imgMainMenu.setPadding(padding, padding, padding, padding);
        FloatingActionButton btnMainMenu = new FloatingActionButton.Builder(this)
                .setContentView(imgMainMenu)
                .setBackgroundDrawable(R.drawable.button_action_red)
                .build();

        SubActionButton.Builder builder = new SubActionButton.Builder(this)
                .setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue));
        builder.setLayoutParams(new LayoutParams(size, size));
        LayoutParams contentParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        contentParams.setMargins(margin, margin, margin, margin);

        ImageView imgSubMenu1 = new ImageView(this);
        ImageView imgSubMenu2 = new ImageView(this);
        ImageView imgSubMenu3 = new ImageView(this);
        imgSubMenu1.setImageResource(R.drawable.ic_search);
        imgSubMenu2.setImageResource(R.drawable.ic_add_friend);
        imgSubMenu3.setImageResource(R.drawable.ic_message);

        SubActionButton subMenuSearch = builder.setContentView(imgSubMenu1, contentParams).build();
        SubActionButton subMenuAddFried = builder.setContentView(imgSubMenu2, contentParams).build();
        SubActionButton subMenuMessage = builder.setContentView(imgSubMenu3, contentParams).build();

        (new FloatingActionMenu.Builder(this)).addSubActionView(subMenuSearch).addSubActionView(subMenuAddFried)
                .addSubActionView(subMenuMessage).attachTo(btnMainMenu).build();

        subMenuAddFried.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddFriendDialog addFriendDialog = new AddFriendDialog(MainActivity.this, handler);
                addFriendDialog.show();
                inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public FriendItem getFriend() {
        return friend;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_notifications:
                break;
        }
        return true;
    }

}