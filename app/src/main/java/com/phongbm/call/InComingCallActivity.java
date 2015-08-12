package com.phongbm.call;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;
import com.phongbm.music.RingtoneManager;

public class InComingCallActivity extends Activity implements View.OnClickListener {
    private TextView btnAnswer, btnHangup;
    private RingtoneManager ringtoneManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        btnAnswer = (TextView) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);
        btnHangup = (TextView) findViewById(R.id.btnHangup);
        btnHangup.setOnClickListener(this);

        ringtoneManager = new RingtoneManager(this);

        this.setVolumeControlStream(AudioManager.STREAM_RING);

        ringtoneManager.playRingtone();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHangup:
                ringtoneManager.stopRingtone();

                this.setVolumeControlStream(AudioManager.USE_DEFAULT_STREAM_TYPE);

                Intent hangup = new Intent(CommonValue.ACTION_HANGUP);
                this.sendBroadcast(hangup);
                this.finish();
                break;

            // nghe cuoc goi
            case R.id.btnAnswer:
                ringtoneManager.stopRingtone();

                this.setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);

                Intent answer = new Intent(CommonValue.ACTION_ANSWER);
                this.sendBroadcast(answer);
                break;
        }
    }

}
