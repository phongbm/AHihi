package com.phongbm.common;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.calling.Call;
import com.sinch.android.rtc.calling.CallClient;
import com.sinch.android.rtc.calling.CallClientListener;
import com.sinch.android.rtc.calling.CallListener;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.List;

public class AHihiService extends Service implements SinchClientListener {
    private static final String TAG = "MyServiceCall";

<<<<<<< HEAD
    public static final int KEY_LENGTH = 13;

=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    private Context context;
    private SinchClient sinchClient;
    private Call outGoingCall = null, inComingCall = null;
    private AHihiBroadcast aHihiBroadcast = null;
    private String outGoingId = null;

    private MessageListener messageListener;
    private MessageClient messageClient;

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
        ParseUser currentUser = ParseUser.getCurrentUser();
        outGoingId = currentUser != null ? currentUser.getObjectId() : null;
        if (intent == null) {
            if (outGoingId != null) {
                this.startSinchService();
            }
            return START_STICKY;
        }
        this.startSinchService();
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
            sinchClient.setSupportMessaging(true);
            sinchClient.setSupportActiveConnectionInBackground(true);
            sinchClient.addSinchClientListener(this);
            sinchClient.checkManifest();
            sinchClient.start();
        }
    }

    @Override
    public void onClientStarted(SinchClient sinchClient) {
        Log.i(TAG, "onClientStarted...");
        if (messageClient == null) {
            messageListener = new MessageListener();
            messageClient = this.sinchClient.getMessageClient();
            messageClient.addMessageClientListener(messageListener);
            this.sinchClient.startListeningOnActiveConnection();
            this.sinchClient.getCallClient().addCallClientListener(new InComingCallListener());
        }
    }

    @Override
    public void onClientStopped(SinchClient sinchClient) {
    }

    @Override
    public void onClientFailed(SinchClient sinchClient, SinchError sinchError) {
    }

    @Override
    public void onRegistrationCredentialsRequired(SinchClient sinchClient, ClientRegistration clientRegistration) {
    }

    @Override
    public void onLogMessage(int i, String s, String s1) {
    }

    private class OutGoingCallListener implements CallListener {
        @Override
        public void onCallProgressing(Call call) {
        }

        @Override
        public void onCallEstablished(Call call) {
            Intent intentPickUp = new Intent();
            intentPickUp.setAction(CommonValue.STATE_PICK_UP);
            AHihiService.this.sendBroadcast(intentPickUp);
        }

        @Override
        public void onCallEnded(Call call) {
            Intent intentEndCall = new Intent();
            intentEndCall.setAction(CommonValue.STATE_END_CALL);
            AHihiService.this.sendBroadcast(intentEndCall);
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {
        }
    }

    private class InComingCallListener implements CallClientListener, CallListener {
        @Override
        public void onIncomingCall(CallClient callClient, Call call) {
            inComingCall = call;
            inComingCall.addCallListener(this);
            Intent intentInComingCall = new Intent();
            intentInComingCall.setClassName(CommonValue.PACKAGE_NAME_MAIN,
                    CommonValue.PACKAGE_NAME_CALL + ".InComingCallActivity");
            intentInComingCall.putExtra(CommonValue.OUTGOING_CALL_ID, call.getRemoteUserId());
            intentInComingCall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intentInComingCall);
        }

        @Override
        public void onCallProgressing(Call call) {
        }

        @Override
        public void onCallEstablished(Call call) {
            Intent intentAnswer = new Intent();
            intentAnswer.setAction(CommonValue.STATE_ANSWER);
            AHihiService.this.sendBroadcast(intentAnswer);
        }

        @Override
        public void onCallEnded(Call call) {
            Intent intentEndCall = new Intent();
            intentEndCall.setAction(CommonValue.STATE_END_CALL);
            AHihiService.this.sendBroadcast(intentEndCall);
        }

        @Override
        public void onShouldSendPushNotification(Call call, List<PushPair> list) {
        }
    }

    private class MessageListener implements MessageClientListener {
        @Override
        public void onIncomingMessage(MessageClient messageClient, Message message) {
<<<<<<< HEAD
            String content = message.getTextBody();
            Intent intentIncoming = new Intent();
            intentIncoming.setAction(CommonValue.STATE_MESSAGE_INCOMING);
            if (!content.contains(CommonValue.AHIHI_KEY)) {
                intentIncoming.putExtra(CommonValue.MESSAGE_CONTENT, content);
                AHihiService.this.sendBroadcast(intentIncoming);
            } else {
                String key = content.substring(0, KEY_LENGTH + 1);
                content = content.substring(KEY_LENGTH + 1);
                switch (key) {
                    case CommonValue.AHIHI_KEY_EMOTICON:
                        intentIncoming.putExtra(CommonValue.AHIHI_KEY, key);
                        intentIncoming.putExtra(CommonValue.MESSAGE_CONTENT, content);
                        AHihiService.this.sendBroadcast(intentIncoming);
                        break;
                }
            }
=======
            Intent intentIncoming = new Intent();
            intentIncoming.setAction(CommonValue.STATE_MESSAGE_INCOMING);
            intentIncoming.putExtra(CommonValue.MESSAGE_CONTENT, message.getTextBody());
            AHihiService.this.sendBroadcast(intentIncoming);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
        }

        @Override
        public void onMessageSent(MessageClient messageClient, Message message, String s) {
            Toast.makeText(AHihiService.this, "onMessageSent...", Toast.LENGTH_SHORT).show();
<<<<<<< HEAD
            String content = message.getTextBody();
            Intent intentSent = new Intent();
            intentSent.setAction(CommonValue.STATE_MESSAGE_SENT);
            if (!content.contains(CommonValue.AHIHI_KEY)) {
                intentSent.putExtra(CommonValue.MESSAGE_CONTENT, content);
                AHihiService.this.sendBroadcast(intentSent);
            } else {
                String key = content.substring(0, KEY_LENGTH + 1);
                content = content.substring(KEY_LENGTH + 1);
                switch (key) {
                    case CommonValue.AHIHI_KEY_EMOTICON:
                        intentSent.putExtra(CommonValue.AHIHI_KEY, key);
                        intentSent.putExtra(CommonValue.MESSAGE_CONTENT, content);
                        AHihiService.this.sendBroadcast(intentSent);
                        break;
                }
            }
=======
            Intent intentSent = new Intent();
            intentSent.setAction(CommonValue.STATE_MESSAGE_SENT);
            AHihiService.this.sendBroadcast(intentSent);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
        }

        @Override
        public void onMessageFailed(MessageClient messageClient, Message message,
                                    MessageFailureInfo messageFailureInfo) {
            Log.i(TAG, "onMessageFailed...");
        }

        @Override
        public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {
            Log.i(TAG, "onMessageDelivered...");
        }

        @Override
        public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {
        }

    }

    private void registerBroadcast() {
        if (aHihiBroadcast == null) {
            aHihiBroadcast = new AHihiBroadcast();
            IntentFilter intentFilter = new IntentFilter();
            // Call
            intentFilter.addAction(CommonValue.ACTION_OUTGOING_CALL);
            intentFilter.addAction(CommonValue.ACTION_END_CALL);
            intentFilter.addAction(CommonValue.ACTION_ANSWER);
            intentFilter.addAction(CommonValue.ACTION_LOGOUT);
            // Message
            intentFilter.addAction(CommonValue.ACTION_SEND_MESSAGE);
            context.registerReceiver(aHihiBroadcast, intentFilter);
        }
    }

    private class AHihiBroadcast extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                // Call
                case CommonValue.ACTION_OUTGOING_CALL:
                    String inComingCallId = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);
                    outGoingCall = sinchClient.getCallClient().callUser(inComingCallId);
                    outGoingCall.addCallListener(new OutGoingCallListener());
                    break;
                case CommonValue.ACTION_END_CALL:
                    if (outGoingCall != null) {
                        outGoingCall.hangup();
                        outGoingCall = null;
                    }
                    if (inComingCall != null) {
                        inComingCall.hangup();
                        inComingCall = null;
                    }
                    break;
                case CommonValue.ACTION_ANSWER:
                    if (inComingCall != null) {
                        inComingCall.answer();
                    }
                    break;
                case CommonValue.ACTION_LOGOUT:
                    messageClient.removeMessageClientListener(messageListener);
                    messageClient = null;

                    sinchClient.stopListeningOnActiveConnection();
                    sinchClient.terminate();
                    sinchClient = null;
                    break;

                // Message
                case CommonValue.ACTION_SEND_MESSAGE:
                    String id = intent.getStringExtra(CommonValue.INCOMING_MESSAGE_ID);
                    String content = intent.getStringExtra(CommonValue.MESSAGE_CONTENT);
<<<<<<< HEAD
                    if (intent.getStringExtra(CommonValue.AHIHI_KEY) == null) {
                        AHihiService.this.sendMessage(id, content);
                    } else {
                        switch (intent.getStringExtra(CommonValue.AHIHI_KEY)) {
                            case CommonValue.AHIHI_KEY_EMOTICON:
                                content = CommonValue.AHIHI_KEY_EMOTICON + content;
                                AHihiService.this.sendMessage(id, content);
                                break;
                        }
                    }
=======
                    AHihiService.this.sendMessage(id, content);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
                    break;
            }
        }
    }

    private synchronized void sendMessage(final String id, final String content) {
        ParseObject message = new ParseObject("Message");
        message.put("senderId", outGoingId);
        message.put("receiverId", id);
        message.put("content", content);
        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    e.printStackTrace();
                    return;
                }
                WritableMessage message = new WritableMessage(id, content);
                messageClient.send(message);
            }
        });
    }

    @Override
    public void onDestroy() {
        this.unregisterReceiver(aHihiBroadcast);
        super.onDestroy();
    }

}