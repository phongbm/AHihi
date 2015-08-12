package com.phongbm.call;

import android.app.Activity;
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
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;
import com.phongbm.libs.CallingRippleView;

import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class OutgoingCallActivity extends Activity implements View.OnClickListener {
    public static final int UPDATE_TIME_CALL = 0;

    private ImageView btnHangup, btnRingtone;
    private TextView txtTime, txtFullName, txtPhoneNumber;
    private CircleImageView imgAvatar;
    private String id, fullName, phoneNumber;
    private Bitmap avatar;
    private CallingRippleView callingRipple;
    private BroadcastOutgoingCall broadcastOutgoingCall;
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
        this.setContentView(R.layout.activity_outgoing_call);
        this.initializeComponent();
        this.registerBroadcastOutgoingCall();
        Intent intent = new Intent(CommonValue.ACTION_OUTGOING_CALL);
        intent.putExtra(CommonValue.INCOMING_CALL_ID, id);
        this.sendBroadcast(intent);
        return;
    }

    private void initializeComponent() {
        btnHangup = (ImageView) findViewById(R.id.btnHangup);
        btnHangup.setOnClickListener(this);
        btnRingtone = (ImageView) findViewById(R.id.btnRingtone);
        btnRingtone.setOnClickListener(this);
        callingRipple = (CallingRippleView) findViewById(R.id.callingRipple);

        Intent intent = this.getIntent();
        id = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);
        fullName = intent.getStringExtra(CommonValue.INCOMING_CALL_FULL_NAME);
        phoneNumber = intent.getStringExtra(CommonValue.INCOMING_CALL_PHONE_NUMBER);
        avatar = BitmapFactory.decodeByteArray(intent.getByteArrayExtra(
                CommonValue.INCOMING_CALL_AVATAR), 0, intent.getByteArrayExtra(
                CommonValue.INCOMING_CALL_AVATAR).length);

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtFullName = (TextView) findViewById(R.id.txtFullName);
        txtPhoneNumber = (TextView) findViewById(R.id.txtPhoneNumber);
        imgAvatar = (CircleImageView) findViewById(R.id.imgAvatar);

        txtFullName.setText(fullName);
        txtPhoneNumber.setText("Mobile " + phoneNumber);
        imgAvatar.setImageBitmap(avatar);
        return;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHangup:
                isCalling = false;
                Intent intent = new Intent(CommonValue.ACTION_END_CALL);
                OutgoingCallActivity.this.sendBroadcast(intent);
                OutgoingCallActivity.this.finish();
                break;
            case R.id.btnRingtone:
                AudioManager audioManager = (AudioManager) OutgoingCallActivity.this
                        .getSystemService(Context.AUDIO_SERVICE);
                audioManager.adjustStreamVolume(AudioManager.STREAM_RING,
                        AudioManager.ADJUST_SAME, AudioManager.FLAG_SHOW_UI);
                break;
        }
        return;
    }

    private void registerBroadcastOutgoingCall() {
        if (broadcastOutgoingCall == null) {
            broadcastOutgoingCall = new BroadcastOutgoingCall();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.STATE_PICK_UP);
            intentFilter.addAction(CommonValue.STATE_END_CALL);
            this.registerReceiver(broadcastOutgoingCall, intentFilter);
        }
        return;
    }

    private class BroadcastOutgoingCall extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.STATE_PICK_UP:
                    isCalling = true;
                    threadTimeCall = new Thread(runnableTimeCall);
                    threadTimeCall.start();
                    OutgoingCallActivity.this.setVolumeControlStream(
                            AudioManager.STREAM_VOICE_CALL);
                    break;
                case CommonValue.STATE_END_CALL:
                    if (isCalling) {
                        isCalling = false;
                        txtTime.setText("End Call: " + time);
                    } else {
                        txtTime.setText("Missed Call");
                    }
                    txtTime.setTextColor(Color.parseColor("#f44336"));
                    callingRipple.setVisibility(RelativeLayout.GONE);
                    (new Handler()).postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            OutgoingCallActivity.this.finish();
                        }
                    }, 3000);
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
        this.unregisterReceiver(broadcastOutgoingCall);
        super.onDestroy();
    }

}