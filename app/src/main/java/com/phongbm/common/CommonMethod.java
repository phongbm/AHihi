package com.phongbm.common;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
<<<<<<< HEAD
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;

import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
=======
import android.support.v4.app.NotificationCompat.Builder;

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
        if (noClear) {
            // outGoingCall, inComingCall
            Builder builder = new Builder(srcActivity).setSmallIcon(icon).setContentTitle("AHihi")
                    .setContentText(content).setAutoCancel(false);
            Intent intent = new Intent(srcActivity, destActivity);
            PendingIntent pendingIntent = PendingIntent.getService(srcActivity, notificationId,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager)
                    srcActivity.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            notification.flags |= Notification.FLAG_NO_CLEAR;
            notificationManager.notify(notificationId, notification);
        } else {
            // missedCall
            Builder builder = new Builder(srcActivity).setSmallIcon(icon).setContentTitle("AHihi")
                    .setContentText(content).setAutoCancel(true);
            Intent intent = new Intent(srcActivity, destActivity);
            PendingIntent pendingIntent = PendingIntent.getActivity(srcActivity, notificationId,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            NotificationManager notificationManager = (NotificationManager)
                    srcActivity.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, builder.build());
        }
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

<<<<<<< HEAD
    public static void uploadAvatar(ParseUser parseUser, Bitmap avatar) {
        if (parseUser == null) return;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        avatar.compress(Bitmap.CompressFormat.JPEG, 80, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        if (bytes != null) {
            ParseFile parseFile = new ParseFile(bytes);
            parseUser.put("avatar", parseFile);
            parseUser.saveInBackground();
        }
        try {
            byteArrayOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SpannableString toSpannableString(Context context, int emoticonId) {
        SpannableString spannableString = new SpannableString(String.valueOf(emoticonId));
        Drawable drawable = context.getResources().getDrawable(emoticonId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth() / 2, drawable.getIntrinsicHeight() / 2);
        ImageSpan imageSpan = new ImageSpan(drawable, ImageSpan.ALIGN_BASELINE);
        spannableString.setSpan(imageSpan, 0, spannableString.length(),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
}