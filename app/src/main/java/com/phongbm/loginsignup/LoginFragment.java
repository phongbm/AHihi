package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.phongbm.ahihi.MainActivity;
import com.phongbm.ahihi.R;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText edtPhoneNumber, edtPassword;
    private AppCompatButton btnLogin;
    private TextView txtLogo, forgotPassword, register;
    private boolean isFillPhoneNumber, isFillPassword;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.test, null);
        this.initializeToolbar();
        this.initializeComponent();
        return view;
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) this.getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) this.getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getActivity().setTitle("LOG IN");
    }

    private void initializeComponent() {
        txtLogo = (TextView) view.findViewById(R.id.txtLogo);
        txtLogo.setTypeface(Typeface.createFromAsset(this.getActivity().getAssets(), "fonts/AIRSTREA.TTF"));
        btnLogin = (AppCompatButton) view.findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        forgotPassword = (TextView) view.findViewById(R.id.forgotPassword);
        forgotPassword.setOnClickListener(this);
        register = (TextView) view.findViewById(R.id.register);
        register.setOnClickListener(this);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtPhoneNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillPhoneNumber = true;
                    enabledButtonLogin();
                } else {
                    isFillPhoneNumber = false;
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillPassword = true;
                    enabledButtonLogin();
                } else {
                    isFillPassword = false;
                    btnLogin.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void enabledButtonLogin() {
        if (isFillPhoneNumber && isFillPassword) {
            btnLogin.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogin:
                final ProgressDialog progressDialog = new ProgressDialog(this.getActivity());
                progressDialog.setTitle("Logging in");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                ParseUser.logInInBackground(phoneNumber, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser parseUser, ParseException e) {
                        if (parseUser != null) {
                            parseUser.put("isOnline", true);
                            parseUser.saveInBackground();

                            Intent intent = new Intent(LoginFragment.this.getActivity(), MainActivity.class);
                            LoginFragment.this.getActivity().startActivity(intent);

                            progressDialog.dismiss();
                            LoginFragment.this.getActivity().finish();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(LoginFragment.this.getActivity(),
                                    "There was an error logging in", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.forgotPassword:
                break;
            case R.id.register:
                ((MainFragment) this.getActivity()).showSigupFragment();
                break;
        }
    }

}