package com.phongbm.message;

<<<<<<< HEAD
import android.text.SpannableString;

public class MessageItem {
    private int type;
    private SpannableString content;
    private int mode;

    public MessageItem(int type, SpannableString content, int mode) {
        this.type = type;
        this.content = content;
        this.mode = mode;
=======
public class MessageItem {
    private int type;
    private String content;

    public MessageItem(int type, String content) {
        this.type = type;
        this.content = content;
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    }

    public int getType() {
        return type;
    }

<<<<<<< HEAD
    public SpannableString getContent() {
        return content;
    }

    public int getMode() {
        return mode;
    }

=======
    public String getContent() {
        return content;
    }

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
}