package com.phongbm.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.ahihi.R;
import com.phongbm.common.CommonValue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener {
    private ListView listViewMessage;
    private MessageAdapter messageAdapter;
    private String outGoingMessageId, inComingMessageId;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private EditText editText;
    private ImageView btnSend;
    private BroadcastMessage broadcastMessage;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_message);
        this.initializeComponent();
        this.registerBroadcastMessage();

        Intent intent = this.getIntent();
        outGoingMessageId = ParseUser.getCurrentUser().getObjectId();
        inComingMessageId = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);

        messageAdapter = new MessageAdapter(this);
        listViewMessage.setAdapter(messageAdapter);
        this.getData();
    }

    private void initializeComponent() {
        listViewMessage = (ListView) findViewById(R.id.listViewMessage);
        editText = (EditText) findViewById(R.id.edtContent);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s == null || s.length() == 0) {
                    btnSend.setEnabled(false);
                } else {
                    btnSend.setEnabled(true);
                }
            }
        });
        btnSend = (ImageView) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);
    }

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        query.whereContainedIn("senderId",
                Arrays.asList(new String[]{outGoingMessageId, inComingMessageId}));
        query.whereContainedIn("receiverId",
                Arrays.asList(new String[]{outGoingMessageId, inComingMessageId}));
        query.orderByDescending("createAt");
        query.setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    return;
                }
                for (ParseObject message : list) {
                    reentrantLock.lock();
                    boolean check = false;
                    if (outGoingMessageId.equals(message.getString("senderId"))) {
                        check = true;
                    }
                    messageAdapter.addMessage(new MessageItem(check, message.getString("content")));
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                content = editText.getText().toString();
                editText.setText("");
                Intent intentSend = new Intent();
                intentSend.setAction(CommonValue.ACTION_SEND_MESSAGE);
                intentSend.putExtra(CommonValue.INCOMING_MESSAGE_ID, inComingMessageId);
                intentSend.putExtra(CommonValue.MESSAGE_CONTENT, content);
                MessageActivity.this.sendBroadcast(intentSend);
                break;
        }
    }

    private void registerBroadcastMessage() {
        if (broadcastMessage == null) {
            broadcastMessage = new BroadcastMessage();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.STATE_MESSAGE_SENT);
            MessageActivity.this.registerReceiver(broadcastMessage, intentFilter);
        }
    }

    private class BroadcastMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.STATE_MESSAGE_SENT:
                    messageAdapter.addMessage(new MessageItem(true, content));
                    messageAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastMessage);
        super.onDestroy();
    }

}