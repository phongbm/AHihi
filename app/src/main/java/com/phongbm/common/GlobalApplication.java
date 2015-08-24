package com.phongbm.common;

import android.app.Application;
<<<<<<< HEAD
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.WindowManager;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

import com.parse.Parse;

public class GlobalApplication extends Application {
<<<<<<< HEAD
    public static int WIDTH_SCREEN, HEIGHT_SCREEN;

    private Bitmap avatar;

=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, ServerInfo.PARSE_APPLICATION_ID, ServerInfo.PARSE_CLIENT_KEY);
<<<<<<< HEAD
        initializeComponent();
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
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    }

}