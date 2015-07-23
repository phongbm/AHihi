package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;
import com.phongbm.loginsignup.MainFragment;

public class TabOneFragment extends Fragment implements View.OnClickListener {
    private View view;
    private Button btnLoguout;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_one, null);

        btnLoguout = (Button) view.findViewById(R.id.btnLogout);
        btnLoguout.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                ParseUser.logOut();
                Intent intent = new Intent(TabOneFragment.this.getActivity(), MainFragment.class);
                TabOneFragment.this.getActivity().startActivity(intent);
                break;
        }
    }

}