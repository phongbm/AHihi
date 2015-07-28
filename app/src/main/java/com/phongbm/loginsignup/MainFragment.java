package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.phongbm.ahihi.R;

public class MainFragment extends Activity {
    private LoginSignupFragment loginSignupFragment = new LoginSignupFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.showLoginSignupFragment();
    }

    public void showLoginSignupFragment() {
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_in_left, R.anim.anim_out_right)
                .replace(android.R.id.content, loginSignupFragment).commit();
    }

    public void showLoginFragment() {
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                .replace(android.R.id.content, new LoginFragment()).commit();
    }

    public void showSigupFragment() {
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                .replace(android.R.id.content, new SignupFragment()).commit();
    }

    public void showProfileInfomationFragment() {
        this.getFragmentManager().beginTransaction()
                .setCustomAnimations(R.anim.anim_in_right, R.anim.anim_out_left)
                .replace(android.R.id.content, new ProfileInfomationFragment()).commit();
    }

    @Override
    public void onBackPressed() {
        if (!loginSignupFragment.isVisible()) {
            this.showLoginSignupFragment();
        } else {
            super.onBackPressed();
        }
    }

}