package com.phongbm.call;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.phongbm.ahihi.CommonValue;
import com.phongbm.ahihi.R;

public class InComingCallActivity extends Activity implements View.OnClickListener {
    private TextView btnAnswer, btnHangup;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_call);

        btnAnswer = (TextView) findViewById(R.id.btnAnswer);
        btnAnswer.setOnClickListener(this);
        btnHangup = (TextView) findViewById(R.id.btnHangup);
        btnHangup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnHangup:
                Intent hangup = new Intent(CommonValue.ACTION_HANGUP);
                this.sendBroadcast(hangup);
                this.finish();
                break;

            // nghe cuoc goi
            case R.id.btnAnswer:
                setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
                Intent answer = new Intent(CommonValue.ACTION_ANSWER);
                this.sendBroadcast(answer);
                break;
        }
    }

}
