package com.phongbm.ahihi;

import android.graphics.Bitmap;

public class AllFriendItem extends FriendItem implements Comparable {
    private String urlAvatar;

    public AllFriendItem() {
    }

    public AllFriendItem(String id, Bitmap avatar, String phoneNumber, String fullName) {
        super(id, avatar, phoneNumber, fullName);
    }

    public AllFriendItem(String id, String urlAvatar, String phoneNumber, String fullName) {
        super.setId(id);
        this.urlAvatar = urlAvatar;
        super.setPhoneNumber(phoneNumber);
        super.setFullName(fullName);
    }

    @Override
    public int compareTo(Object another) {
        return super.getFullName().toLowerCase()
                .compareTo(((AllFriendItem) another).getFullName().toLowerCase());
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

}