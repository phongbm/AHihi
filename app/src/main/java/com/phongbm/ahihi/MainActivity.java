package com.phongbm.ahihi;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.call.CallLogActivity;
import com.phongbm.call.CallLogsDBManager;
import com.phongbm.common.CommonValue;
import com.phongbm.common.GlobalApplication;
import com.phongbm.loginsignup.MainFragment;

import java.util.ArrayList;
import java.util.Collections;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressWarnings("ALL")
public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";
    private static final int REQUEST_ADDITION_FRIEND = 0;

    private GlobalApplication globalApplication;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tab;
    private FriendItem newFriend;
    private Bitmap userAvatar;
    private CircleImageView imgAvatar;
    private FloatingActionButton btnAction;
    private ArrayList<AllFriendItem> allFriendItems;
    private ParseUser currentUser;
    private CallLogsDBManager callLogsDBManager;

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.loadListFriend();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        }, 3000);

        this.setContentView(R.layout.activity_main);
        globalApplication = (GlobalApplication) this.getApplicationContext();
        this.initializeToolbar();
        this.initializeComponent();
        this.initializeProfileInformation();
        this.startService();
        callLogsDBManager = new CallLogsDBManager(this);
    }

    private void loadListFriend() {
        allFriendItems = new ArrayList<>();
        currentUser = ParseUser.getCurrentUser();
        ArrayList<String> listFriendId = (ArrayList<String>) currentUser.get("listFriend");
        if (listFriendId == null || listFriendId.size() == 0) {
            return;
        }
        for (int i = 0; i < listFriendId.size(); i++) {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.getInBackground(listFriendId.get(i), new GetCallback<ParseUser>() {
                @Override
                public void done(final ParseUser parseUser, ParseException e) {
                    if (e != null) {
                        e.printStackTrace();
                        return;
                    }
                    ParseFile parseFile = (ParseFile) parseUser.get("avatar");
                    if (parseFile == null) {
                        return;
                    }
                    parseFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e != null) {
                                return;
                            }
                            Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            allFriendItems.add(new AllFriendItem(parseUser.getObjectId(), avatar,
                                    parseUser.getUsername(), parseUser.getString("fullName")));
                            Collections.sort(allFriendItems);
                        }
                    });
                }
            });
        }
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setTitle(R.string.app_name);
    }

    private void initializeComponent() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.open_navigation_drawer, R.string.close_navigation_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigation = (NavigationView) findViewById(R.id.navigation);
        navigation.setNavigationItemSelectedListener(this);

        viewPagerAdapter = new ViewPagerAdapter(this, this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        int[] tabBackgroundIds = new int[]{R.drawable.bg_tab_message, R.drawable.bg_tab_contact,
                R.drawable.bg_tab_friend, R.drawable.bg_tab_info};
        tab = (TabLayout) findViewById(R.id.tab);
        tab.setupWithViewPager(viewPager);
        for (int i = 0; i < viewPagerAdapter.getCount(); i++) {
            tab.getTabAt(i).setText(null);
            tab.getTabAt(i).setIcon(tabBackgroundIds[i]);
        }

        btnAction = (FloatingActionButton) findViewById(R.id.btnAction);
        btnAction.setOnClickListener(this);
    }

    private void initializeProfileInformation() {
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(this);

        final TextView txtName = (TextView) findViewById(R.id.txtName);
        final TextView txtEmail = (TextView) findViewById(R.id.txtEmail);

        if (globalApplication.getAvatar() != null) {
            imgAvatar.setImageBitmap(globalApplication.getAvatar());
            txtName.setText(globalApplication.getFullName());
            txtEmail.setText(globalApplication.getEmail());
            Log.i(TAG, "Get Profile Information from GlobalApplication");
            return;
        }

        Log.i(TAG, "Get Profile Information from Server");
        ParseFile parseFile = (ParseFile) currentUser.get("avatar");
        if (parseFile != null) {
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        String fullName = currentUser.getString("fullName");
                        userAvatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgAvatar.setImageBitmap(userAvatar);
                        txtName.setText(fullName);
                        txtEmail.setText(currentUser.getEmail());
                        globalApplication.setAvatar(userAvatar);
                        globalApplication.setFullName(fullName);
                        globalApplication.setPhoneNumber(currentUser.getUsername());
                    }
                }
            });
        }
    }

    private void startService() {
        Intent intentStartService = new Intent();
        intentStartService.setClassName(CommonValue.PACKAGE_NAME_MAIN,
                CommonValue.PACKAGE_NAME_COMMON + ".AHihiService");
        this.startService(intentStartService);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        if (menuItem.isChecked()) {
            menuItem.setChecked(false);
        } else {
            menuItem.setChecked(true);
        }
        switch (menuItem.getItemId()) {
            case R.id.nav_call_logs:
                Intent intentCallLogs = new Intent(MainActivity.this, CallLogActivity.class);
                MainActivity.this.startActivity(intentCallLogs);
                break;
            case R.id.nav_log_out:
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Log out?");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ParseUser parseUser = ParseUser.getCurrentUser();
                                parseUser.put("isOnline", false);
                                parseUser.saveInBackground();
                                ParseUser.logOut();
                                callLogsDBManager.deleteAllData();
                                callLogsDBManager.closeDatabase();
                                Intent intentLogout = new Intent(CommonValue.ACTION_LOGOUT);
                                MainActivity.this.sendBroadcast(intentLogout);
                                Intent intent = new Intent(MainActivity.this, MainFragment.class);
                                alertDialog.dismiss();
                                MainActivity.this.startActivity(intent);
                                MainActivity.this.finish();
                            }
                        });
                alertDialog.show();
                break;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_add_user:
                Intent intentAdditionFriend = new Intent(MainActivity.this, AdditionFriend.class);
                MainActivity.this.startActivityForResult(intentAdditionFriend, REQUEST_ADDITION_FRIEND);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public FriendItem getNewFriend() {
        return newFriend;
    }

    private void createNewFriend(final ParseUser parseUser) {
        final ParseFile parseFile = (ParseFile) parseUser.get("avatar");
        if (parseFile != null) {
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e != null) {
                        return;
                    }
                    Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    newFriend = new FriendItem(parseUser.getObjectId(), avatar,
                            parseUser.getUsername(), parseUser.getString("fullName"));
                    Intent intentAddFriend = new Intent();
                    intentAddFriend.setAction(CommonValue.ACTION_ADD_FRIEND);
                    boolean isOnline = parseUser.getBoolean("isOnline");
                    intentAddFriend.putExtra("isOnline", isOnline);
                    MainActivity.this.sendBroadcast(intentAddFriend);
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgAvatar:
                Intent intentProfile = new Intent(this, DetailActivity.class);
                intentProfile.putExtra(CommonValue.USER_ID, currentUser.getObjectId());
                this.startActivity(intentProfile);
                break;
            case R.id.btnAction:
                Intent intentNewMessage = new Intent(this, NewMessageActivity.class);
                globalApplication.setAllFriendItems(allFriendItems);
                this.startActivity(intentNewMessage);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        final CoordinatorLayout coordinator = (CoordinatorLayout) findViewById(R.id.coordinator);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_ADDITION_FRIEND:
                    if (data == null) {
                        return;
                    }
                    String phoneNumber = data.getStringExtra("PHONE_NUMBER");
                    if (phoneNumber.equals(((GlobalApplication) this.getApplication())
                            .getPhoneNumber())) {
                        Snackbar.make(coordinator, "You can not make friends with yourself",
                                Snackbar.LENGTH_LONG)
                                .setAction("ACTION", null)
                                .show();
                        return;
                    }
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setTitle("Addition Friend");
                    progressDialog.setMessage("Please wait...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    final ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", phoneNumber);
                    query.getFirstInBackground(new GetCallback<ParseUser>() {
                        @Override
                        public void done(ParseUser parseUser, ParseException e) {
                            if (e != null) {
                                Snackbar.make(coordinator, "Error",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("ACTION", null)
                                        .show();
                                e.printStackTrace();
                                progressDialog.dismiss();
                                return;
                            }
                            if (parseUser == null) {
                                Snackbar.make(coordinator, "That account does not exist",
                                        Snackbar.LENGTH_LONG)
                                        .setAction("ACTION", null)
                                        .show();
                                progressDialog.dismiss();
                                return;
                            }
                            ArrayList<String> listFriend = (ArrayList<String>) currentUser.get("listFriend");
                            if (listFriend == null)
                                listFriend = new ArrayList<String>();
                            listFriend.add(parseUser.getObjectId());
                            currentUser.put("listFriend", listFriend);
                            currentUser.saveInBackground();
                            createNewFriend(parseUser);
                            progressDialog.dismiss();
                        }
                    });
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(Gravity.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        if (currentUser != null) {
            currentUser.put("isOnline", false);
            currentUser.saveInBackground();
        }
        super.onDestroy();
    }

    @Override
    public void finish() {
        super.finish();
    }

}