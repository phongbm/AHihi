package com.phongbm.message;

import com.phongbm.common.GlobalApplication;

public class MessagesLogItem {
    private String id, fullName, message, date;
    private boolean isRead;

    public MessagesLogItem(String id, String fullName, String message, String date, boolean isRead) {
        this.id = id;
        this.fullName = fullName;
        this.message = message;
        this.date = date;
        this.isRead = isRead;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null) return false;
        if (o instanceof MessagesLogItem)
            return ((MessagesLogItem) o).id.equals(id);
        else return false;
    }

}