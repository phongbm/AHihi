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

public class ProfilePictureFragment extends Fragment implements View.OnClickListener {
    private View view;
    private TextView btnBack, btnSkip, btnOK;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_profile_picture, null);
        initializeComponent();
        return view;
    }

    private void initializeComponent() {
        btnBack = (TextView) view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(this);
        btnSkip = (TextView) view.findViewById(R.id.btnSkip);
        btnSkip.setOnClickListener(this);
        btnOK = (TextView) view.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                ((MainFragment) this.getActivity()).showProfileInfomationFragmentBack();
                break;
            case R.id.btnSkip:
                break;
            case R.id.btnOK:
                break;
        }
    }

}