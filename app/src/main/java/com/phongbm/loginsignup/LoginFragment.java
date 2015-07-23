package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.phongbm.ahihi.MainActivity;
import com.phongbm.ahihi.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private View view;
    private ImageView previous;
    private EditText edtPhoneNumber, edtPassword;
    private TextView btnLogin;
    private TextWatcher textWatcherPhoneNumber, textWatcherPassword;
    private boolean isFillPhoneNumber, isFillPassword;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        previous = (ImageView) view.findViewById(R.id.previous);
        previous.setOnClickListener(this);
        btnLogin = (TextView) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        textWatcherPhoneNumber = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillPhoneNumber = true;
                } else {
                    isFillPhoneNumber = false;
                    btnLogin.setEnabled(false);
                }
                enabledButtonLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        textWatcherPassword = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillPassword = true;
                } else {
                    isFillPassword = false;
                    btnLogin.setEnabled(false);
                }
                enabledButtonLogin();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edtPhoneNumber.addTextChangedListener(textWatcherPhoneNumber);
        edtPassword.addTextChangedListener(textWatcherPassword);
    }

    private void enabledButtonLogin() {
        if (isFillPhoneNumber && isFillPassword) {
            btnLogin.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.previous:
                ((MainFragment) this.getActivity()).showLoginSignupFragment();
                break;
            case R.id.btnLogin:
                String phoneNumber = edtPhoneNumber.getText().toString();
                String password = edtPassword.getText().toString();
                ParseUser.logInInBackground(phoneNumber, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            Intent intent = new Intent(LoginFragment.this.getActivity(), MainActivity.class);
                            LoginFragment.this.getActivity().startActivity(intent);
                            LoginFragment.this.getActivity().finish();
                            Toast.makeText(LoginFragment.this.getActivity(),
                                    "Logged successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(LoginFragment.this.getActivity(),
                                    "There was an error logging in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
        }
    }

}