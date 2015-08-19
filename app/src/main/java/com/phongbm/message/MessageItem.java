package com.phongbm.message;

public class MessageItem {
    private int type;
    private String content;

    public MessageItem(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

}