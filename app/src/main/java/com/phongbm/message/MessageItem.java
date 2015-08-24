package com.phongbm.message;

import android.text.SpannableString;

public class MessageItem {
    private int type;
    private SpannableString content;
    private int mode;

    public MessageItem(int type, SpannableString content, int mode) {
        this.type = type;
        this.content = content;
        this.mode = mode;
    }

    public int getType() {
        return type;
    }

    public SpannableString getContent() {
        return content;
    }

    public int getMode() {
        return mode;
    }

}