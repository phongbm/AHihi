package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.phongbm.ahihi.R;

public class LoginSignupFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btnLogin, btnSignup;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login_signup, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        btnLogin = (TextView) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        btnSignup = (TextView) view.findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                ((MainFragment) this.getActivity()).showLoginFragment();
                break;
            case R.id.btnSignup:
                ((MainFragment) this.getActivity()).showSigupFragment();
                break;
        }
    }

}