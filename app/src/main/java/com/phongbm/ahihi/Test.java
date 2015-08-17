package com.phongbm.ahihi;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class Test extends AppCompatActivity {
    private TextView txtLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.test);
        this.initializeToolbar();
        this.initializeComponent();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeComponent() {
        txtLogo = (TextView) findViewById(R.id.txtLogo);
        txtLogo.setTypeface(Typeface.createFromAsset(this.getAssets(), "fonts/AIRSTREA.TTF"));
    }

}