package com.phongbm.ahihi;

import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.common.CommonValue;
import com.phongbm.libs.SquareImageView;

@SuppressWarnings("ConstantConditions")
public class DetailActivity extends AppCompatActivity {
    private CollapsingToolbarLayout collapsingToolbar;
    private SquareImageView imgAvatar;
    private TextView txtFullName, txtPhoneNumber, txtEmail, txtBirthday, txtSex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_detail);
        this.initializeComponent();
        this.loadInformation();
    }

    private void initializeComponent() {
        this.setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle("Profile Information");
        collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,
                android.R.color.transparent));

        imgAvatar = (SquareImageView) findViewById(R.id.imgAvatar);
        txtFullName = (TextView) findViewById(R.id.txtFullName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        txtEmail = (TextView) findViewById(R.id.txtEmail);
        txtBirthday = (TextView) findViewById(R.id.txtBirthday);
        txtSex = (TextView) findViewById(R.id.txtSex);
    }

    private void loadInformation() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        String id = this.getIntent().getStringExtra(CommonValue.USER_ID);
        final ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.getInBackground(id, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                }
                ParseFile parseFile = (ParseFile) parseUser.get("avatar");
                if (parseFile != null) {
                    parseFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e == null) {
                                imgAvatar.setImageBitmap(
                                        BitmapFactory.decodeByteArray(bytes, 0, bytes.length));
                            }
                        }
                    });
                } else {
                    imgAvatar.setImageResource(R.drawable.ic_avatar_default);
                }
                String fullName = parseUser.getString("fullName");
                collapsingToolbar.setTitle(fullName);
                txtFullName.setText(fullName);
                txtPhoneNumber.setText(parseUser.getUsername());
                txtEmail.setText(parseUser.getEmail());
                txtBirthday.setText(parseUser.getString("birthday"));
                boolean sex = parseUser.getBoolean("sex");
                txtSex.setText(sex ? "Male" : "Female");

                progressDialog.dismiss();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}