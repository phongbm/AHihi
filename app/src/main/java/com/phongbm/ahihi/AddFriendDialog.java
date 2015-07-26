package com.phongbm.ahihi;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;

public class AddFriendDialog extends Dialog implements
        android.view.View.OnClickListener {
    private Context context;
    private Handler handler;
    private EditText edtPhoneNumber;
    private TextView btnAddFriend;

    public AddFriendDialog(Context context, Handler handler) {
        super(context);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.context = context;
        this.handler = handler;
        this.setContentView(R.layout.dialog_addfriend);
        initializeComponent();
    }

    private void initializeComponent() {
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        btnAddFriend = (TextView) findViewById(R.id.btnAddFriend);
        btnAddFriend.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddFriend:
                break;
        }
    }

}