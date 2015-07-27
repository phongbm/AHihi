package com.phongbm.ahihi;

public class ContactItem {
    private String phoneNumber, name, photo;

    public ContactItem(String phoneNumber, String name, String photo) {
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.photo = photo;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getPhoto() {
        return photo;
    }

}