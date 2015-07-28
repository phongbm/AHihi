package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.phongbm.ahihi.R;

public class SignupFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText edtPhoneNumber, edtPassword, edtConfirmPassword;
    private TextView btnSignup;
    private CheckBox checkBoxAgree;
    private TextWatcher textWatcherPhoneNumber, textWatcherPassword, textWatcherConfirmPassword;
    private boolean isFillPhoneNumber, isFillPassword, isFillConfirmPassword, isCheckBoxChecked;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_signup, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        btnSignup = (TextView) view.findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(this);
        edtPhoneNumber = (EditText) view.findViewById(R.id.edtPhoneNumber);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtConfirmPassword = (EditText) view.findViewById(R.id.edtConfirmPassword);
        checkBoxAgree = (CheckBox) view.findViewById(R.id.checkBoxAgree);
        checkBoxAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (checkBoxAgree.isChecked()) {
                    isCheckBoxChecked = true;
                    enabledButtonSignup();
                } else {
                    isCheckBoxChecked = false;
                    btnSignup.setEnabled(false);
                }
            }
        });
        textWatcherPhoneNumber = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillPhoneNumber = true;
                    enabledButtonSignup();
                } else {
                    isFillPhoneNumber = false;
                    btnSignup.setEnabled(false);
                }
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
                    enabledButtonSignup();
                } else {
                    isFillPassword = false;
                    btnSignup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        textWatcherConfirmPassword = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillConfirmPassword = true;
                    enabledButtonSignup();
                } else {
                    isFillConfirmPassword = false;
                    btnSignup.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edtPhoneNumber.addTextChangedListener(textWatcherPhoneNumber);
        edtPassword.addTextChangedListener(textWatcherPassword);
        edtConfirmPassword.addTextChangedListener(textWatcherConfirmPassword);
    }

    private void enabledButtonSignup() {
        if (isFillPhoneNumber && isFillPassword && isFillConfirmPassword && isCheckBoxChecked
                && edtPassword.getText().toString().equals(edtConfirmPassword.getText().toString())) {
            btnSignup.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignup:
                /*final ProgressDialog progressDialog = new ProgressDialog(this.getActivity());
                progressDialog.setTitle("Signing up");
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();

                String phoneNumber = edtPhoneNumber.getText().toString();
                String password = edtPassword.getText().toString();
                ParseUser parseUser = new ParseUser();
                parseUser.setUsername(phoneNumber);
                parseUser.setPassword(password);
                parseUser.signUpInBackground(new SignUpCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            ((MainFragment) SignupFragment.this.getActivity())
                                    .showProfileInfomationFragment();
                            progressDialog.dismiss();
                            Toast.makeText(SignupFragment.this.getActivity(),
                                    "Registered successfully", Toast.LENGTH_SHORT).show();
                        } else {
                            progressDialog.dismiss();
                            Toast.makeText(SignupFragment.this.getActivity(),
                                    "There was an error signing up", Toast.LENGTH_LONG).show();
                        }
                    }
                });*/
                ((MainFragment) SignupFragment.this.getActivity())
                        .showProfileInfomationFragment();
                break;
        }
    }

}