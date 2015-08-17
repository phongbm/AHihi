package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;
import com.phongbm.common.CommonValue;
import com.phongbm.loginsignup.MainFragment;

@SuppressLint("ValidFragment")
public class TabOneFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "TabOneFragment";
    private View view;
    private Button btnLoguout, btnGoogleMap, btnStop;
    private boolean isRunning = true;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    Toast.makeText(getActivity(), latitude + ", " + longitude, Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

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

        btnStop = (Button) view.findViewById(R.id.btnStop);
        btnStop.setOnClickListener(this);
    }

    double latitude, longitude;

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
                latitude = 21.038342;
                longitude = 105.782418;

                (new Thread(runnable)).start();

                break;
            case R.id.btnStop:
                isRunning = false;
                break;
        }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (isRunning) {
                Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latitude + "," + longitude);
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setFlags(mapIntent.getFlags() | Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_NO_HISTORY);
                mapIntent.setPackage("com.google.android.apps.maps");
                //if (mapIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(mapIntent);
                //}
                latitude += 0.1;
                longitude += 0.1;
                handler.sendEmptyMessage(1);
                SystemClock.sleep(10000);
                // break;
            }
        }
    };


}