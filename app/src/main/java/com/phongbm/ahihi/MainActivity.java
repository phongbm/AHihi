package com.phongbm.ahihi;

<<<<<<< HEAD
import android.app.Activity;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.call.CallLogActivity;
<<<<<<< HEAD
import com.phongbm.common.CommonMethod;
import com.phongbm.common.CommonValue;
import com.phongbm.common.GlobalApplication;
import com.phongbm.image.ImageActivity;
=======
import com.phongbm.common.CommonValue;
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements
<<<<<<< HEAD
        NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private static final String TAG = "MainActivity";

    private GlobalApplication globalApplication;
=======
        NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private InputMethodManager inputMethodManager;
    private FriendItem newFriend;
    private Bitmap userAvatar;
<<<<<<< HEAD
    private CircleImageView imgAvatar;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
            }
        }
    };

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo == null ? false : true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
<<<<<<< HEAD
        globalApplication = (GlobalApplication) this.getApplicationContext();
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
        this.initializeToolbar();
        this.initializeComponent();
        this.initializeProfileInformation();
        this.startService();
    }

    private void initializeToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
    }

    private void initializeComponent() {
        inputMethodManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);

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

        navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);
<<<<<<< HEAD
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(this);
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

        viewPagerAdapter = new ViewPagerAdapter(this, this.getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(viewPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.bg_tab_message);
        tabLayout.getTabAt(1).setIcon(R.drawable.bg_tab_contact);
        tabLayout.getTabAt(2).setIcon(R.drawable.bg_tab_friend);
        tabLayout.getTabAt(3).setIcon(R.drawable.bg_tab_info);
    }

    private void initializeProfileInformation() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        View header = navigationView.getChildAt(0);
        TextView txtName = (TextView) header.findViewById(R.id.txtName);
        txtName.setText((String) currentUser.get("fullName"));
        TextView txtEmail = (TextView) header.findViewById(R.id.txtEmail);
        txtEmail.setText(currentUser.getEmail());
<<<<<<< HEAD

=======
        final CircleImageView imgAvatar = (CircleImageView) header.findViewById(R.id.imgAvatar);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
        ParseFile parseFile = (ParseFile) currentUser.get("avatar");
        if (parseFile != null) {
            parseFile.getDataInBackground(new GetDataCallback() {
                @Override
                public void done(byte[] bytes, ParseException e) {
                    if (e == null) {
                        userAvatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgAvatar.setImageBitmap(userAvatar);
<<<<<<< HEAD
                        globalApplication.setAvatar(userAvatar);
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
                    }
                }
            });
        }
    }

    private void startService() {
        Intent intentStartService = new Intent();
        intentStartService.setClassName("com.phongbm.ahihi", "com.phongbm.common.AHihiService");
        startService(intentStartService);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        switch (menuItem.getItemId()) {
            case R.id.nav_call_logs:
                Intent intentCallLogs = new Intent(MainActivity.this, CallLogActivity.class);
                MainActivity.this.startActivity(intentCallLogs);
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
                AddFriendDialog addFriendDialog = new AddFriendDialog();
                addFriendDialog.show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void finish() {
        super.finish();
    }

    @Override
    protected void onDestroy() {
        ParseUser parseUser = ParseUser.getCurrentUser();
        if (parseUser != null) {
            parseUser.put("isOnline", false);
            parseUser.saveInBackground();
        }
        super.onDestroy();
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
                    sendBroadcast(intentAddFriend);
                }
            });
        }
    }

<<<<<<< HEAD
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imgAvatar:
                startActivitySetAvatar();
                break;
        }
    }

    private void startActivitySetAvatar() {
        Intent intentAcount = new Intent();
        intentAcount.setClass(MainActivity.this, ImageActivity.class);
        MainActivity.this.startActivityForResult(intentAcount, CommonValue.REQUECODE_SET_AVATAR);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CommonValue.REQUECODE_SET_AVATAR && resultCode == Activity.RESULT_OK) {
            byte[] bytes = data.getByteArrayExtra(CommonValue.BYTE_AVATAR);
            Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            CommonMethod.uploadAvatar(ParseUser.getCurrentUser(), avatar);
            imgAvatar.setImageBitmap(avatar);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    private class AddFriendDialog extends Dialog implements android.view.View.OnClickListener {
        private EditText edtPhoneNumber;
        private AppCompatButton btnAddFriend;

        public AddFriendDialog() {
            super(MainActivity.this);
            this.requestWindowFeature(Window.FEATURE_NO_TITLE);
            this.setContentView(R.layout.dialog_addfriend);
            this.initializeComponent();
        }

        private void initializeComponent() {
            edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
            btnAddFriend = (AppCompatButton) findViewById(R.id.btnAddFriend);
            btnAddFriend.setOnClickListener(this);
            edtPhoneNumber.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s != null && s.length() > 0) {
                        btnAddFriend.setEnabled(true);
                    } else {
                        btnAddFriend.setEnabled(false);
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.btnAddFriend:
                    String phoneNumber = edtPhoneNumber.getText().toString().trim();
                    final ParseUser currentUser = ParseUser.getCurrentUser();
                    ParseQuery<ParseUser> query = ParseUser.getQuery();
                    query.whereEqualTo("username", phoneNumber);
                    query.findInBackground(new FindCallback<ParseUser>() {
                        @Override
                        public void done(List<ParseUser> list, ParseException e) {
                            if (e == null) {
                                if (list.size() == 0) {
                                    Toast.makeText(MainActivity.this, "That account does not exist",
                                            Toast.LENGTH_SHORT).show();
                                    AddFriendDialog.this.dismiss();
                                    return;
                                }
                                ArrayList<String> listFriend = (ArrayList<String>)
                                        currentUser.get("listFriend");
                                if (listFriend == null)
                                    listFriend = new ArrayList<String>();
                                listFriend.add(list.get(0).getObjectId());
                                currentUser.put("listFriend", listFriend);
                                currentUser.saveInBackground();

                                createNewFriend(list.get(0));

                                AddFriendDialog.this.dismiss();
                            }
                        }
                    });
                    break;
            }
        }
    }

}