package com.phongbm.call;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;

import de.hdodenhof.circleimageview.CircleImageView;

public class OutgoingCallActivity extends Activity implements View.OnClickListener {
    private ImageView btnEnCall, btnCallSpeaker;
    private TextView txtCallerName;
    private String id, name;
    private Bitmap avatar;
    private CircleImageView imgCallerAvatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_call);
        this.initializeComponent();

        this.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

        Intent intent = new Intent(CommonValue.ACTION_OUTGOING_CALL);
        intent.putExtra(CommonValue.INCOMING_CALL_ID, id);
        sendBroadcast(intent);
    }

    private void initializeComponent() {
        id = getIntent().getStringExtra(CommonValue.INCOMING_CALL_ID);
        name = getIntent().getStringExtra(CommonValue.INCOMING_CALL_NAME);
        avatar = BitmapFactory.decodeByteArray(getIntent().
                getByteArrayExtra(CommonValue.INCOMING_CALL_AVATAR), 0, getIntent()
                .getByteArrayExtra(CommonValue.INCOMING_CALL_AVATAR).length);
        
        btnEnCall = (ImageView) findViewById(R.id.btnEndCall);
        btnEnCall.setOnClickListener(this);

        txtCallerName = (TextView) findViewById(R.id.txtCallerName);
        txtCallerName.setText(name);
        imgCallerAvatar = (CircleImageView) findViewById(R.id.imgCallerAvatar);
        imgCallerAvatar.setImageBitmap(avatar);

        btnCallSpeaker = (ImageView) findViewById(R.id.btnCallSpeaker);
        btnCallSpeaker.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEndCall:
                Intent intent = new Intent(CommonValue.END_CALL);
                sendBroadcast(intent);
                finish();
                break;
            case R.id.btnCallSpeaker:
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setMode(AudioManager.MODE_IN_CALL);
                audioManager.setSpeakerphoneOn(true);
                break;
        }
    }

}