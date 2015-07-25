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

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

}