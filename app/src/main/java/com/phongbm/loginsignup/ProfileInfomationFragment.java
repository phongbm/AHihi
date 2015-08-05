package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.phongbm.ahihi.R;

public class ProfileInfomationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText edtBirthday, edtFirstName, edtLastName, edtEmail;
    private TextView btnOK;
    private TextWatcher textWatcherFirstName, textWatcherLastName, textWatcherEmail;
    private boolean isFillFirstName, isFillLastName, isFillEmail;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_infomation, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        edtBirthday = (EditText) view.findViewById(R.id.edtBirthday);
        edtBirthday.setOnClickListener(this);
        btnOK = (TextView) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
        edtFirstName = (EditText) view.findViewById(R.id.edtFirstName);
        edtLastName = (EditText) view.findViewById(R.id.edtLastName);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        textWatcherFirstName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillFirstName = true;
                    enabledButtonOK();
                } else {
                    isFillFirstName = false;
                    btnOK.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        textWatcherLastName = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillLastName = true;
                    enabledButtonOK();
                } else {
                    isFillLastName = false;
                    btnOK.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        textWatcherEmail = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    isFillEmail = true;
                    enabledButtonOK();
                } else {
                    isFillEmail = false;
                    btnOK.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edtFirstName.addTextChangedListener(textWatcherFirstName);
        edtLastName.addTextChangedListener(textWatcherLastName);
        edtEmail.addTextChangedListener(textWatcherEmail);
    }

    private void enabledButtonOK() {
        if (isFillFirstName && isFillLastName && isFillEmail) {
            btnOK.setEnabled(true);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtBirthday:
                showDatePickerDialog();
                break;
            case R.id.btnOK:
                ((MainFragment) this.getActivity()).showProfilePictureFragment();
                break;
        }
    }

    public void showDatePickerDialog() {
        DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                edtBirthday.setText((dayOfMonth < 10 ? "0" + dayOfMonth : dayOfMonth) + "/" +
                        ((monthOfYear + 1) < 10 ? "0" + (monthOfYear + 1) : (monthOfYear + 1)) + "/" +
                        year);
            }
        };
        String date = edtBirthday.getText().toString();
        String dates[] = date.split("/");
        int day = Integer.parseInt(dates[0]);
        int month = Integer.parseInt(dates[1]) - 1;
        int year = Integer.parseInt(dates[2]);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getActivity(),
                onDateSetListener, year, month, day);
        datePickerDialog.show();
    }

}