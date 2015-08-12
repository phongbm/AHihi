package com.phongbm.call;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.parse.ParseUser;
import com.phongbm.common.CommonValue;
import com.phongbm.common.ServerInfo;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;

import java.util.List;

public class MyServiceCall extends Service {
    private static final String TAG = "MyServiceCall";
    private String outGoingId = null;
    private Context context;
    private SinchClient sinchClient;
    private Call outGoingCall = null, inComingCall = null;
    private BroadcastReceiverCall receiverCall = null;

    @Override
    public void onCreate() {
        super.onCreate();
        if (context == null) {
            context = this;
        }
        this.registerBroadcast();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand...");
        Log.i(TAG, "onStartCommand..." + outGoingId);
        if (ParseUser.getCurrentUser() != null) {
            outGoingId = ParseUser.getCurrentUser().getObjectId();
        } else {
            outGoingId = null;
        }
        if (intent == null) {
            if (sinchClient == null)
                Log.i(TAG, "null SInchCLient");
            if (outGoingId != null) {
                Log.i(TAG, "not null outGoingId");
                startSinchService();
            }
            return START_STICKY;
        }
        startSinchService();
        return Service.START_STICKY;
    }

    private void startSinchService() {
        if (sinchClient == null) {
            sinchClient = Sinch.getSinchClientBuilder()
                    .context(this)
                    .userId(outGoingId)
                    .applicationKey(ServerInfo.SINCH_APPLICATION_KEY)
                    .applicationSecret(ServerInfo.SINCH_SECRET)
                    .environmentHost(ServerInfo.SINCH_ENVIROMENT)
                    .build();
            sinchClient.setSupportCalling(true);
            sinchClient.startListeningOnActiveConnection();
            sinchClient.setSupportPushNotifications(true);
            sinchClient.checkManifest();
            sinchClient.setSupportActiveConnectionInBackground(true);
            sinchClient.getCallClient().addCallClientListener(new InComingCallListener());
            sinchClient.start();
        }
    }

    private class OutGoingCallListener implements CallListener {
        @Override
        public void onCallProgressing(Call call) {
            Log.i(TAG, "onCallProgressing...");
        }

        @Override
        public void onCallEstablished(Call call) {
        }

        @Override
        public void onCallEnded(Call call) {
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {
        }
    }

    private class InComingCallListener implements CallClientListener, CallListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            // bat cuoc goi den
            inComingCall = call;
            inComingCall.addCallListener(this);
            Intent intentInComingCall = new Intent();
            intentInComingCall.setClassName("com.phongbm.ahihi",
                    "com.phongbm.call.InComingCallActivity");
            intentInComingCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentInComingCall);
        }

        @Override
        public void onCallProgressing(Call call) {
            // do chuong
        }

        @Override
        public void onCallEstablished(Call call) {
            // cuoc goi den duoc nhac len
        }

        @Override
        public void onCallEnded(Call call) {
            // ket thuoc cuoc goi boi 1 trong 2 ben
            Log.i(TAG, "onCallEnded...");
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {
        }
    }

    private void registerBroadcast() {
        if (receiverCall == null) {
            receiverCall = new BroadcastReceiverCall();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.ACTION_ANSWER);
            intentFilter.addAction(CommonValue.ACTION_HANGUP);
            intentFilter.addAction(CommonValue.ACTION_OUTGOING_CALL);
            intentFilter.addAction(CommonValue.ACTION_LOGOUT);
            intentFilter.addAction(CommonValue.END_CALL);
            context.registerReceiver(receiverCall, intentFilter);
        }
    }

    private class BroadcastReceiverCall extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                // dong y nghe cuoc goi
                case CommonValue.ACTION_ANSWER:
                    if (inComingCall != null) {
                        inComingCall.answer();
                    }
                    break;
                case CommonValue.ACTION_HANGUP:
                    if (inComingCall != null) {
                        inComingCall.hangup();
                        inComingCall = null;
                    }
                    break;
                // thiet lap cuoc goi di
                case CommonValue.ACTION_OUTGOING_CALL:
                    outGoingCall = sinchClient.getCallClient()
                            .callUser(intent.getStringExtra(CommonValue.INCOMING_CALL_ID));
                    outGoingCall.addCallListener(new OutGoingCallListener());
                    break;
                case CommonValue.END_CALL:
                    if (outGoingCall != null) {
                        Log.i(TAG, "END_CALL ... outGoingCall");
                        outGoingCall.hangup();
                        outGoingCall = null;
                    }
                    if (inComingCall != null) {
                        Log.i(TAG, "END_CALL ... inComingCall");
                        inComingCall.hangup();
                        inComingCall = null;
                    }
                    break;
                case CommonValue.ACTION_LOGOUT:
                    sinchClient.stopListeningOnActiveConnection();
                    sinchClient.terminate();
                    sinchClient = null;
            }
        }
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(receiverCall);
        super.onDestroy();
    }


}