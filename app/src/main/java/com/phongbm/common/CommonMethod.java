package com.phongbm.common;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat.Builder;

import com.phongbm.ahihi.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

public class CommonMethod {
    private static CommonMethod commonMethod;

    public static CommonMethod getInstance() {
        if (commonMethod == null) {
            commonMethod = new CommonMethod();
        }
        return commonMethod;
    }

    public CommonMethod() {
    }

    public void pushNotification(Activity srcActivity, Class destActivity, String content,
                                 int notificationId, int icon, boolean noClear) {
        Builder builder = new Builder(srcActivity).setSmallIcon(icon)
                .setContentTitle(srcActivity.getResources().getString(R.string.app_name))
                .setContentText(content);
        Intent intent;
        intent = new Intent(srcActivity, destActivity);
        PendingIntent pendingIntent = PendingIntent.getActivity(srcActivity, notificationId,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager)
                srcActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        if (noClear) {
            notification.flags |= Notification.FLAG_NO_CLEAR;
        }
        notificationManager.notify(notificationId, notification);
    }

    public String convertTimeToString(int timeCall) {
        int minutes = (int) TimeUnit.MILLISECONDS.toMinutes(timeCall);
        int seconds = (int) TimeUnit.MILLISECONDS.toSeconds(timeCall) - minutes * 60;
        String time = (minutes < 10 ? "0" + minutes : "" + minutes)
                + ":" + (seconds < 10 ? "0" + seconds : "" + seconds);
        return time;
    }

    public String getCurrentDateTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss");
        return simpleDateFormat.format(Calendar.getInstance().getTime());
    }

}