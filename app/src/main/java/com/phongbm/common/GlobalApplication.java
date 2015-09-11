package com.phongbm.common;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.parse.Parse;
import com.sinch.android.rtc.ClientRegistration;
import com.sinch.android.rtc.PushPair;
import com.sinch.android.rtc.Sinch;
import com.sinch.android.rtc.SinchClient;
import com.sinch.android.rtc.SinchClientListener;
import com.sinch.android.rtc.SinchError;
import com.sinch.android.rtc.messaging.Message;
import com.sinch.android.rtc.messaging.MessageClient;
import com.sinch.android.rtc.messaging.MessageClientListener;
import com.sinch.android.rtc.messaging.MessageDeliveryInfo;
import com.sinch.android.rtc.messaging.MessageFailureInfo;
import com.sinch.android.rtc.messaging.WritableMessage;

import java.util.ArrayList;
import java.util.List;

public class GlobalApplication extends Application implements SinchClientListener, MessageClientListener {
    public static int WIDTH_SCREEN, HEIGHT_SCREEN;

    private Bitmap avatar;
    private String fullName, phoneNumber;
    private Bitmap pictureSend;

    private SinchClient sinchClient;
    private MessageClient messageClient;
    private int count = -1;
    private ArrayList<String> idUsers;
    private BroadcastGlobalApplication broadcastGlobalApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ServerInfo.PARSE_APPLICATION_ID, ServerInfo.PARSE_CLIENT_KEY);
        this.initializeComponent();
        this.registerBroadcastGlobalApplication();
        idUsers = new ArrayList<>();
    }

    private void initializeComponent() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        WIDTH_SCREEN = metrics.widthPixels;
        HEIGHT_SCREEN = metrics.heightPixels;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public void setAvatar(Bitmap avatar) {
        this.avatar = avatar;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Bitmap getPictureSend() {
        return pictureSend;
    }

    public void setPictureSend(Bitmap pictureSend) {
        this.pictureSend = pictureSend;
    }

    private void startSinchService(String idUser) {
        if (sinchClient != null) {
            sinchClient.stopListeningOnActiveConnection();
            sinchClient.terminate();
            sinchClient = null;
        }
        sinchClient = Sinch.getSinchClientBuilder()
                .context(this)
                .userId(idUser)
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

    // MessageClientListenr
    @Override
    public void onIncomingMessage(MessageClient messageClient, Message message) {
    }

    @Override
    public void onMessageSent(MessageClient messageClient, Message message, String s) {
        Log.i("Application", "onMessageSent...");
        if (count % 2 == 0) {
            if (count >= 100) count = 0;
            count++;
            if (sinchClient != null) {
                sinchClient.stopListeningOnActiveConnection();
                sinchClient.terminate();
                sinchClient = null;
            }
            // messageClient.removeMessageClientListener(this);
            Intent i = new Intent();
            i.setAction(CommonValue.RESULT_START_SERVICE);
            sendBroadcast(i);
        }
        Log.i("Application", "onMessageSent...finish");
    }

    @Override
    public void onMessageFailed(MessageClient messageClient, Message message, MessageFailureInfo messageFailureInfo) {
    }

    @Override
    public void onMessageDelivered(MessageClient messageClient, MessageDeliveryInfo messageDeliveryInfo) {
    }

    @Override
    public void onShouldSendPushData(MessageClient messageClient, Message message, List<PushPair> list) {
    }

    // SinchClientListener
    @Override
    public void onClientStarted(SinchClient sinchClient) {
        messageClient = this.sinchClient.getMessageClient();
        messageClient.addMessageClientListener(this);
        this.sinchClient.startListeningOnActiveConnection();
        WritableMessage message = new WritableMessage("1cSWKDINiZ", "HELLO ANDROID");
        messageClient.send(message);
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

    private void registerBroadcastGlobalApplication() {
        if (broadcastGlobalApplication == null) {
            broadcastGlobalApplication = new BroadcastGlobalApplication();
            IntentFilter filter = new IntentFilter();
            filter.addAction(CommonValue.START_FIRST_SINCH);
            registerReceiver(broadcastGlobalApplication, filter);
        }
    }

    private class BroadcastGlobalApplication extends BroadcastReceiver {
        @Override
        public synchronized void onReceive(Context context, Intent intent) {
            String id = intent.getStringExtra(CommonValue.ID_START_FIRST_SINCH);
            if (!idUsers.contains(id)) {
                count++;
                Log.i("GlobalApplication", "BroadcastGlobalApplication...");
                idUsers.add(id);
                GlobalApplication.this.startSinchService(id);
            } else {
                Intent i = new Intent();
                i.setAction(CommonValue.RESULT_START_SERVICE);
                sendBroadcast(i);
            }
        }
    }

    public void addIdUser(String idUser) {
        idUsers.add(idUser);
    }

    @Override
    public void onTerminate() {
        if (broadcastGlobalApplication != null) {
            this.unregisterReceiver(broadcastGlobalApplication);
            broadcastGlobalApplication = null;
        }
        super.onTerminate();
    }

}