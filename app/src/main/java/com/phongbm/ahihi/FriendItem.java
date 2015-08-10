package com.phongbm.ahihi;

import android.graphics.Bitmap;

public class FriendItem implements Comparable {
    private String id, fullName;
    private Bitmap avatar;

    public FriendItem(String id, String fullName, Bitmap avatar) {
        this.id = id;
        this.fullName = fullName;
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return fullName;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    @Override
    public int compareTo(Object another) {
        return fullName.toLowerCase().compareTo(((FriendItem) another).getName().toLowerCase());
    }
}