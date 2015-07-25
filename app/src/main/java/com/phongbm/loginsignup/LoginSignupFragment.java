package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phongbm.ahihi.R;

public class LoginSignupFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView txtWelcome, btnLogin, btnSignup;
    private ImageView imgLogo;
    private RelativeLayout layout;

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
        imgLogo = (ImageView) view.findViewById(R.id.imgLogo);
        txtWelcome = (TextView) view.findViewById(R.id.txtWelcome);
        layout = (RelativeLayout) view.findViewById(R.id.layout);
        startAnimation();
    }

    public void startAnimation() {
        imgLogo.setAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_updown));
        txtWelcome.setAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_rightleft));
        layout.setAnimation(AnimationUtils.loadAnimation(this.getActivity(), R.anim.anim_leftright));
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