package com.phongbm.ahihi;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class AddFriendDialog extends Dialog implements
        android.view.View.OnClickListener {
    private static final String TAG = "AddFriendDialog";
    private Context context;
    private Handler handler;
    private EditText edtPhoneNumber;
    private TextView btnAddFriend;
    private TextWatcher textWatcherPhoneNumber;

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
        textWatcherPhoneNumber = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() > 0) {
                    btnAddFriend.setEnabled(true);
                } else {
                    btnAddFriend.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        };
        edtPhoneNumber.addTextChangedListener(textWatcherPhoneNumber);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAddFriend:
                String phoneNumber = edtPhoneNumber.getText().toString().trim();
                final ParseUser currentUser = ParseUser.getCurrentUser();
                ParseQuery<ParseUser> query = ParseUser.getQuery();
                query.whereEqualTo("username", phoneNumber);
                query.findInBackground(new FindCallback<ParseUser>() {
                    @Override
                    public void done(List<ParseUser> list, ParseException e) {
                        if (e == null) {
                            if (list.size() == 0) {
                                Toast.makeText(context, "That account does not exist",
                                        Toast.LENGTH_SHORT).show();
                                AddFriendDialog.this.dismiss();
                                return;
                            }
                            ArrayList<String> listFriend = (ArrayList<String>) currentUser.get("listFriend");
                            if (listFriend == null)
                                listFriend = new ArrayList<String>();
                            listFriend.add(list.get(0).getObjectId());
                            currentUser.put("listFriend", listFriend);
                            currentUser.saveEventually();

                            Message message = new Message();
                            message.what = CommonValue.REQUEST_ADD_FRIEND;
                            message.obj = list.get(0).get("fullName");
                            message.setTarget(handler);
                            message.sendToTarget();
                            AddFriendDialog.this.dismiss();
                        } else {
                        }
                    }
                });
                break;
        }
    }

}