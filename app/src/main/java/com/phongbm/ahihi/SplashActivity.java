package com.phongbm.ahihi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.parse.ParseUser;
import com.phongbm.loginsignup.MainFragment;

public class SplashActivity extends Activity {
    private final int SPLASH_DISPLAY_LENGHT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.demo_material);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ParseUser currentUser = ParseUser.getCurrentUser();
                if (currentUser != null) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    SplashActivity.this.startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, MainFragment.class);
                    SplashActivity.this.startActivity(intent);
                }
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGHT);
    }

}