package com.phongbm.loginsignup;

import android.app.Activity;
import android.os.Bundle;

public class MainFragment extends Activity {
    private LoginSignupFragment loginSignupFragment = new LoginSignupFragment();
    private LoginFragment loginFragment = new LoginFragment();
    private SignupFragment signupFragment = new SignupFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.addAllFragments();
        this.showLoginSignupFragment();
    }

    private void addAllFragments() {
        this.getFragmentManager().beginTransaction().add(android.R.id.content,
                loginSignupFragment, "LoginSignup").commit();
        this.getFragmentManager().beginTransaction().add(android.R.id.content,
                loginFragment, "Login").commit();
        this.getFragmentManager().beginTransaction().add(android.R.id.content,
                signupFragment, "Signup").commit();
    }

    public void showLoginSignupFragment() {
        this.getFragmentManager().beginTransaction().show(loginSignupFragment).commit();
        this.getFragmentManager().beginTransaction().hide(loginFragment).commit();
        this.getFragmentManager().beginTransaction().hide(signupFragment).commit();
    }

    public void showLoginFragment() {
        this.getFragmentManager().beginTransaction().show(loginFragment).commit();
        this.getFragmentManager().beginTransaction().hide(signupFragment).commit();
        this.getFragmentManager().beginTransaction().hide(loginSignupFragment).commit();
    }

    public void showSigupFragment() {
        this.getFragmentManager().beginTransaction().show(signupFragment).commit();
        this.getFragmentManager().beginTransaction().hide(loginSignupFragment).commit();
        this.getFragmentManager().beginTransaction().hide(loginFragment).commit();
    }

    @Override
    public void onBackPressed() {
        if (loginSignupFragment.isHidden()) {
            this.showLoginSignupFragment();
        } else {
            super.onBackPressed();
        }
    }

}