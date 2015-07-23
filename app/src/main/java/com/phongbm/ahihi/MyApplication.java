package com.phongbm.ahihi;

import android.app.Application;

import com.parse.Parse;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(this, "L3tNbRlz4GUUy7UXE65tiEQwVNYcHDX5GBXa2bBG",
                "dLQjhoiFJiifs820COIac5Oih6fV2QU7X90QVoe8");
    }

}