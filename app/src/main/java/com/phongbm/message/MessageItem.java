package com.phongbm.message;

public class MessageItem {
    private boolean check;
    private String content;

    public MessageItem(boolean check, String content) {
        this.check = check;
        this.content = content;
    }

    public boolean isCheck() {
        return check;
    }

    public String getContent() {
        return content;
    }

}