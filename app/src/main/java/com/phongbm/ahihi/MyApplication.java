package com.phongbm.ahihi;

import android.app.Application;

import com.parse.Parse;
import com.phongbm.common.ServerInfo;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ServerInfo.PARSE_APPLICATION_ID, ServerInfo.PARSE_CLIENT_KEY);
    }

}