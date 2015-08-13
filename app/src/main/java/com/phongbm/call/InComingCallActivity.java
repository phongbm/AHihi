package com.phongbm.call;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.ahihi.MainActivity;
import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;
import com.phongbm.libs.CallingRippleView;
import com.phongbm.music.RingtoneManager;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class InComingCallActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "InComingCallActivity";
    public static final int UPDATE_TIME_CALL = 0;

    private ImageView btnAnswer, btnHangup;
    private TextView txtTime, txtFullName, txtPhoneNumber;
    private CircleImageView imgAvatar;
    private CallingRippleView callingRipple;
    private RingtoneManager ringtoneManager;
    private BroadcastInComingCall broadcastInComingCall;
    private int timeCall = 0;
    private String time;
    private boolean isCalling = false;
    private Thread threadTimeCall;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_TIME_CALL:
                    txtTime.setText("Time call: " + time);
                    break;
            }
            return;
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_incoming_call);
        this.initializeComponent();
        this.registerBroadcastInComingCall();
        this.setVolumeControlStream(AudioManager.STREAM_RING);
        ringtoneManager = new RingtoneManager(this);
        ringtoneManager.playRingtone();

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(InComingCallActivity.this)
                        .setSmallIcon(R.drawable.ic_notification_calling)
                        .setContentTitle("AHihi").setContentText("Calling...");
        Intent i = new Intent(InComingCallActivity.this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(
                InComingCallActivity.this, 1, i, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)
                InComingCallActivity.this.getSystemService(
                        Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, builder.build());

        return;
    }

    private void initializeComponent() {
        btnAnswer = (ImageView) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);
        btnHangup = (ImageView) findViewById(R.id.btnHangup);
        btnHangup.setOnClickListener(this);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtFullName = (TextView) findViewById(R.id.txtFullName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);
        callingRipple = (CallingRippleView) findViewById(R.id.callingRipple);

        ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
        parseQuery.whereEqualTo("objectId", this.getIntent().getStringExtra
                (CommonValue.OUTGOING_CALL_ID));
        parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if (parseUser == null) {
                    Log.i(TAG, "parseUser NULL");
                    return;
                }
                txtFullName.setText((String) parseUser.get("fullName"));
                txtPhoneNumber.setText("Mobile " + parseUser.getUsername());
                ParseFile parseFile = (ParseFile) parseUser.get("avatar");
                if (parseFile == null) {
                    return;
                }
                parseFile.getDataInBackground(new GetDataCallback() {
                    @Override
                    public void done(byte[] bytes, ParseException e) {
                        if (e != null) {
                            return;
                        }
                        Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        imgAvatar.setImageBitmap(avatar);
                    }
                });
            }
        });
        return;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAnswer:
                isCalling = true;
                btnAnswer.setEnabled(false);
                ringtoneManager.stopRingtone();
                this.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                Intent intentAnswer = new Intent(CommonValue.ACTION_ANSWER);
                this.sendBroadcast(intentAnswer);
                break;
            case R.id.btnHangup:
                isCalling = false;
                ringtoneManager.stopRingtone();
                this.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);
                Intent intentHangup = new Intent(CommonValue.ACTION_HANGUP);
                this.sendBroadcast(intentHangup);
                this.finish();
                break;
        }
        return;
    }

    private void registerBroadcastInComingCall() {
        if (broadcastInComingCall == null) {
            broadcastInComingCall = new BroadcastInComingCall();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.STATE_ANSWER);
            intentFilter.addAction(CommonValue.STATE_END_CALL);
            this.registerReceiver(broadcastInComingCall, intentFilter);
        }
        return;
    }

    private class BroadcastInComingCall extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.STATE_ANSWER:
                    threadTimeCall = new Thread(runnableTimeCall);
                    threadTimeCall.start();
                    break;
                case CommonValue.STATE_END_CALL:
                    if (timeCall != 0) {
                        isCalling = false;
                        txtTime.setText("End Call: " + time);
                        txtTime.setTextColor(Color.parseColor("#f44336"));
                        callingRipple.setVisibility(RelativeLayout.GONE);
                        (new Handler()).postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                InComingCallActivity.this.finish();
                            }
                        }, 3000);
                    } else {
                        ringtoneManager.stopRingtone();
                        NotificationCompat.Builder builder =
                                new NotificationCompat.Builder(InComingCallActivity.this)
                                        .setSmallIcon(R.drawable.ic_notification_missed_call)
                                        .setContentTitle("AHihi").setContentText("Missed Call");
                        Intent i = new Intent(InComingCallActivity.this, MainActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(
                                InComingCallActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                        builder.setContentIntent(pendingIntent);
                        NotificationManager notificationManager = (NotificationManager)
                                InComingCallActivity.this.getSystemService(
                                        Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(0, builder.build());
                        InComingCallActivity.this.finish();
                    }
                    break;
            }
            return;
        }
    }


    private Runnable runnableTimeCall = new Runnable() {
        @Override
        public void run() {
            while (isCalling) {
                timeCall += 1000;
                convertTimeToString();
                handler.sendEmptyMessage(UPDATE_TIME_CALL);
                SystemClock.sleep(1000);
            }
        }
    };

    private void convertTimeToString() {
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeCall);
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeCall) - minutes * 60;
        time = (minutes < 10 ? "0" + minutes : "" + minutes)
                + ":" + (seconds < 10 ? "0" + seconds : "" + seconds);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastInComingCall);
        ((NotificationManager) InComingCallActivity.this.
                getSystemService(Context.NOTIFICATION_SERVICE)).cancel(1);
        super.onDestroy();
    }

}