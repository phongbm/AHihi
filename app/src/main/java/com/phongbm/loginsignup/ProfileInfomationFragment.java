package com.phongbm.loginsignup;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.phongbm.ahihi.R;

public class ProfileInfomationFragment extends Fragment implements View.OnClickListener {
    private View view;
    private EditText edtBirthday;

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
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.edtBirthday:
                break;
        }
    }

}