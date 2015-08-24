package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

<<<<<<< HEAD
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import com.parse.ParseUser;
import com.phongbm.common.CommonValue;
import com.phongbm.loginsignup.MainFragment;

import java.io.IOException;
import java.net.URL;
<<<<<<< HEAD
import java.util.Arrays;
import java.util.List;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

@SuppressLint("ValidFragment")
public class TabOneFragment extends Fragment implements View.OnClickListener,
        Html.ImageGetter {
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
<<<<<<< HEAD
                /*latitude = 21.038342;
                longitude = 105.782418;
                (new Thread(runnable)).start();*/

                String id = ParseUser.getCurrentUser().getObjectId();
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
                String[] ids = new String[]{id};
                query.whereContainedIn("senderId", Arrays.asList(ids));
                query.whereContainedIn("receiverId", Arrays.asList(ids));
                query.orderByDescending("createdAt");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null || list == null || list.size() == 0) {
                            Log.i(TAG, "NULL");
                            return;
                        }
                        for (int i = 0; i < list.size(); i++) {
                            Log.i(TAG, list.get(i).getObjectId());
                        }
                    }
                });
=======
                latitude = 21.038342;
                longitude = 105.782418;

                (new Thread(runnable)).start();

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
                break;
            case R.id.btnStop:
                isRunning = false;

                TextView txtPicture = (TextView) this.view.findViewById(R.id.txtPicture);

                /*SpannableString ss = new SpannableString(" ");
                Drawable d = TabOneFragment.this.getActivity().getResources()
                        .getDrawable(R.drawable.emotion_06);
                d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
                ImageSpan span = new ImageSpan(d, ImageSpan.ALIGN_BASELINE);
                ss.setSpan(span, 0, ss.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                txtPicture.setText(ss);
                Toast.makeText(TabOneFragment.this.getActivity(), ss.length() + "",
                        Toast.LENGTH_SHORT).show();*/

                String stringWithHtml = "Sample string with an <a href=\"http://www.exemplary-link.com\">exemplary link</a>.";
                Spanned spannedValue = Html.fromHtml(stringWithHtml, getImageHTML(), null);
                txtPicture.setText(spannedValue);

                break;
        }
    }

    public Html.ImageGetter getImageHTML() {
        Html.ImageGetter imageGetter = new Html.ImageGetter() {
            public Drawable getDrawable(String source) {
                try {
                    Drawable drawable = Drawable.createFromStream(new URL(source).openStream(), "AHihi");
                    drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
                    return drawable;
                } catch (IOException exception) {
                    Log.v("IOException", exception.getMessage());
                    return null;
                }
            }
        };
        return imageGetter;
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


    @Override
    public Drawable getDrawable(String source) {
        LevelListDrawable d = new LevelListDrawable();
<<<<<<< HEAD
        Drawable empty = getResources().getDrawable(R.drawable.emoticon_06);
=======
        Drawable empty = getResources().getDrawable(R.drawable.emotion_06);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
        d.addLevel(0, 0, empty);
        d.setBounds(0, 0, empty.getIntrinsicWidth(), empty.getIntrinsicHeight());
        return d;
    }

}