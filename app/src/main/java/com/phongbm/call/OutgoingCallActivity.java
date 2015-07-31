package com.phongbm.call;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.phongbm.ahihi.CommonValue;
import com.phongbm.ahihi.R;

public class OutgoingCallActivity extends Activity implements View.OnClickListener {
    private ImageView btnEnCall;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outgoing_call);

        btnEnCall = (ImageView) findViewById(R.id.btnEndCall);
        btnEnCall.setOnClickListener(this);

        // thiet lap cuoc goi di
        String id = getIntent().getStringExtra(CommonValue.INCOMING_CALL_ID);
        Intent intent = new Intent(CommonValue.ACTION_OUTGOING_CALL);
        intent.putExtra(CommonValue.INCOMING_CALL_ID, id);
        sendBroadcast(intent);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnEndCall:
                Intent intent = new Intent(CommonValue.END_CALL);
                sendBroadcast(intent);
                finish();
                break;
        }
    }
}