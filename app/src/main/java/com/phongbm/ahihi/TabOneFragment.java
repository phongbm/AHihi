package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.parse.ParseUser;
import com.phongbm.common.CommonValue;
import com.phongbm.loginsignup.MainFragment;

@SuppressLint("ValidFragment")
public class TabOneFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TabOneFragment";
    private View view;
    private Button btnLoguout, btnGoogleMap;

    public TabOneFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_one, null);
        initializeComponent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()...");
        return view;
    }

    private void initializeComponent() {
        btnLoguout = (Button) view.findViewById(R.id.btnLogout);
        btnLoguout.setOnClickListener(this);

        btnGoogleMap = (Button) view.findViewById(R.id.btnGoogleMap);
        btnGoogleMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnLogout:
                ParseUser parseUser = ParseUser.getCurrentUser();
                parseUser.put("isOnline", false);
                parseUser.saveInBackground();
                ParseUser.logOut();

                Intent intentLogout = new Intent(CommonValue.ACTION_LOGOUT);
                TabOneFragment.this.getActivity().sendBroadcast(intentLogout);

                Intent intent = new Intent(TabOneFragment.this.getActivity(), MainFragment.class);
                TabOneFragment.this.getActivity().startActivity(intent);
                TabOneFragment.this.getActivity().finish();
                break;

            case R.id.btnGoogleMap:
                String latitude = "21.038342";
                String longitude = "105.782418";
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivity(mapIntent);
                }
                break;
        }
    }

}