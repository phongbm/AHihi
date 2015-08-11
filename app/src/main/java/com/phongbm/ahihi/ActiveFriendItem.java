package com.phongbm.ahihi;

import android.graphics.Bitmap;

public class ActiveFriendItem extends AllFriendItem {

    public ActiveFriendItem(String id, String fullName, Bitmap avatar) {
        super(id, fullName, avatar);
    }

    @Override
    public int compareTo(Object another) {
        return super.compareTo(another);
    }

}