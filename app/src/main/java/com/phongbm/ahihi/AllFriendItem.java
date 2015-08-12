package com.phongbm.ahihi;

import android.graphics.Bitmap;

public class AllFriendItem implements Comparable {
    private String id, fullName, phoneNumber;
    private Bitmap avatar;

    public AllFriendItem(String id, String fullName, String phoneNumber, Bitmap avatar) {
        this.id = id;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    @Override
    public int compareTo(Object another) {
        return fullName.toLowerCase().compareTo(((AllFriendItem) another).getName().toLowerCase());
    }

}