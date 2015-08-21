package com.phongbm.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

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

public class MessageActivity extends AppCompatActivity implements View.OnClickListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "MessageActivity";

    private RelativeLayout layoutMain, menu;
    private InputMethodManager inputMethodManager;
    private ListView listViewMessage;
    private MessageAdapter messageAdapter;
    private String outGoingMessageId, inComingMessageId;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private EditText edtContent;
    private ImageView btnSend;
    private BroadcastMessage broadcastMessage;
    private String content, inComingFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_message);

        Intent intent = this.getIntent();
        outGoingMessageId = ParseUser.getCurrentUser().getObjectId();
        inComingMessageId = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);
        inComingFullName = intent.getStringExtra("NAME");

        this.initializeToolbar();
        this.initializeComponent();
        this.registerBroadcastMessage();

        messageAdapter = new MessageAdapter(this);
        listViewMessage.setAdapter(messageAdapter);
        this.getData();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.setTitle(inComingFullName);
    }

    private void initializeComponent() {
        inputMethodManager = (InputMethodManager) this.
                getSystemService(Context.INPUT_METHOD_SERVICE);
        layoutMain = (RelativeLayout) findViewById(R.id.layoutMain);
        layoutMain.getViewTreeObserver().addOnGlobalLayoutListener(this);
        menu = (RelativeLayout) findViewById(R.id.menu);
        listViewMessage = (ListView) findViewById(R.id.listViewMessage);
        listViewMessage.setSelected(false);
        btnSend = (ImageView) findViewById(R.id.btnSend);
        btnSend.setEnabled(false);
        btnSend.setOnClickListener(this);
        edtContent = (EditText) findViewById(R.id.edtContent);
        edtContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s == null || s.length() == 0) {
                    btnSend.setEnabled(false);
                    btnSend.setImageResource(R.drawable.ic_sent_negative);
                } else {
                    btnSend.setEnabled(true);
                    btnSend.setImageResource(R.drawable.ic_sent_active);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        edtContent.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    Log.i(TAG, "onFocusChange...");
                    menu.setVisibility(RelativeLayout.GONE);
                } else {
                    menu.setVisibility(RelativeLayout.VISIBLE);
                }
            }
        });
    }

    private void getData() {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Message");
        String[] ids = new String[]{outGoingMessageId, inComingMessageId};
        query.whereContainedIn("senderId", Arrays.asList(ids));
        query.whereContainedIn("receiverId", Arrays.asList(ids));
        query.orderByDescending("createdAt");
        query.setLimit(100);
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    return;
                }
                for (ParseObject message : list) {
                    reentrantLock.lock();
                    int type = MessageAdapter.TYPE_INCOMING;
                    if (outGoingMessageId.equals(message.getString("senderId"))) {
                        type = MessageAdapter.TYPE_OUTGOING;
                    }
                    messageAdapter.addMessage(0, new MessageItem(type, message.getString("content")));
                    reentrantLock.unlock();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSend:
                content = edtContent.getText().toString();
                edtContent.setText("");
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
            intentFilter.addAction(CommonValue.STATE_MESSAGE_INCOMING);
            MessageActivity.this.registerReceiver(broadcastMessage, intentFilter);
        }
    }

    @Override
    public void onGlobalLayout() {
        Rect r = new Rect();
        //r will be populated with the coordinates of your view that area still visible.
        layoutMain.getWindowVisibleDisplayFrame(r);
        int heightDiff = layoutMain.getRootView().getHeight() - (r.bottom + r.top);
        if (heightDiff > 100) { // if more than 100 pixels, its probably a keyboard...
            Log.i(TAG, "keyboard show");
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(RelativeLayout.GONE);
                }
            });
        } else if (heightDiff <= 100) {
            Log.i(TAG, "keyboard hide");
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(RelativeLayout.VISIBLE);
                }
            });
        }
    }

    private class BroadcastMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.STATE_MESSAGE_SENT:
                    messageAdapter.addMessage(messageAdapter.getCount(),
                            new MessageItem(MessageAdapter.TYPE_OUTGOING, content));
                    break;
                case CommonValue.STATE_MESSAGE_INCOMING:
                    messageAdapter.addMessage(messageAdapter.getCount(),
                            new MessageItem(MessageAdapter.TYPE_INCOMING,
                                    intent.getStringExtra(CommonValue.MESSAGE_CONTENT)));
                    break;
            }
            listViewMessage.setSelection(messageAdapter.getCount());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_message, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        this.unregisterReceiver(broadcastMessage);
        super.onDestroy();
    }

}