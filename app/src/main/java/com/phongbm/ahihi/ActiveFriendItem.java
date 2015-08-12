package com.phongbm.ahihi;

import android.graphics.Bitmap;

public class ActiveFriendItem extends AllFriendItem {

    public ActiveFriendItem(String id, String fullName, String phoneNumber, Bitmap avatar) {
        super(id, fullName, phoneNumber, avatar);
    }

    @Override
    public int compareTo(Object another) {
        return super.compareTo(another);
    }

}