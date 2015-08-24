package com.phongbm.message;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
<<<<<<< HEAD
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
=======
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
<<<<<<< HEAD
import android.widget.LinearLayout;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.ahihi.R;
<<<<<<< HEAD
import com.phongbm.common.AHihiService;
import com.phongbm.common.CommonMethod;
import com.phongbm.common.CommonValue;

import java.util.ArrayList;
=======
import com.phongbm.common.CommonValue;

>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class MessageActivity extends AppCompatActivity implements View.OnClickListener,
        ViewTreeObserver.OnGlobalLayoutListener {
    private static final String TAG = "MessageActivity";

<<<<<<< HEAD
    private static final int NUMBER_COLLECTION_EMOTICON = 20;

    private RelativeLayout layoutMain, menu;
    private LinearLayout emoticons;
=======
    private RelativeLayout layoutMain, menu;
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    private InputMethodManager inputMethodManager;
    private ListView listViewMessage;
    private MessageAdapter messageAdapter;
    private String outGoingMessageId, inComingMessageId;
    private ReentrantLock reentrantLock = new ReentrantLock();
    private EditText edtContent;
<<<<<<< HEAD
    private ImageView btnSend, imgEmoticon;
    private BroadcastMessage broadcastMessage;
    private String inComingFullName, content;
    private CommonMethod commonMethod;
    private boolean isOpenEmoticons = false;
    private TabLayout tabs;
    private ViewPager viewPager;

    private int[] emoticonIds;
    private EmoticonAdapter[] emoticonAdapters = new EmoticonAdapter[NUMBER_COLLECTION_EMOTICON];
    private ArrayList<CollectionEmoticonItem> collectionEmoticonItems;
    private CollectionEmoticonAdapter collectionEmoticonAdapter;
=======
    private ImageView btnSend;
    private BroadcastMessage broadcastMessage;
    private String content, inComingFullName;
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_message);

<<<<<<< HEAD
        commonMethod = CommonMethod.getInstance();

        Intent intent = this.getIntent();
        outGoingMessageId = ParseUser.getCurrentUser().getObjectId();
        inComingMessageId = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);
        inComingFullName = intent.getStringExtra(CommonValue.INCOMING_MESSAGE_FULL_NAME);
=======
        Intent intent = this.getIntent();
        outGoingMessageId = ParseUser.getCurrentUser().getObjectId();
        inComingMessageId = intent.getStringExtra(CommonValue.INCOMING_CALL_ID);
        inComingFullName = intent.getStringExtra("NAME");
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5

        this.initializeToolbar();
        this.initializeComponent();
        this.registerBroadcastMessage();

<<<<<<< HEAD
        messageAdapter = new MessageAdapter(this, inComingMessageId);
=======
        messageAdapter = new MessageAdapter(this);
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
        emoticons = (LinearLayout) findViewById(R.id.emoticons);
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
        imgEmoticon = (ImageView) findViewById(R.id.imgEmoticon);
        imgEmoticon.setOnClickListener(this);

        emoticonIds = new int[]{R.drawable.finch_1, R.drawable.finch_2, R.drawable.finch_3,
                R.drawable.finch_4, R.drawable.finch_5, R.drawable.finch_6, R.drawable.finch_7,
                R.drawable.finch_8, R.drawable.finch_9, R.drawable.finch_10, R.drawable.finch_11,
                R.drawable.finch_12, R.drawable.finch_13, R.drawable.finch_14, R.drawable.finch_15,
                R.drawable.finch_16};
        ArrayList<EmoticonItem> emoticonItems0 = new ArrayList<EmoticonItem>();
        for (int i = 0; i < emoticonIds.length; i++) {
            emoticonItems0.add(new EmoticonItem(emoticonIds[i]));
        }
        emoticonAdapters[0] = new EmoticonAdapter(this, emoticonItems0, inComingMessageId);

        emoticonIds = new int[]{R.drawable.sallyfrien_1, R.drawable.sallyfrien_2, R.drawable.sallyfrien_3,
                R.drawable.sallyfrien_4, R.drawable.sallyfrien_5, R.drawable.sallyfrien_6, R.drawable.sallyfrien_7,
                R.drawable.sallyfrien_8, R.drawable.sallyfrien_9, R.drawable.sallyfrien_10, R.drawable.sallyfrien_11,
                R.drawable.sallyfrien_12, R.drawable.sallyfrien_13, R.drawable.sallyfrien_14, R.drawable.sallyfrien_15,
                R.drawable.sallyfrien_16, R.drawable.sallyfrien_17, R.drawable.sallyfrien_18, R.drawable.sallyfrien_19,
                R.drawable.sallyfrien_20, R.drawable.sallyfrien_21, R.drawable.sallyfrien_22, R.drawable.sallyfrien_23,
                R.drawable.sallyfrien_24, R.drawable.sallyfrien_25, R.drawable.sallyfrien_26, R.drawable.sallyfrien_27,
                R.drawable.sallyfrien_28, R.drawable.sallyfrien_29, R.drawable.sallyfrien_30, R.drawable.sallyfrien_31,
                R.drawable.sallyfrien_32, R.drawable.sallyfrien_33, R.drawable.sallyfrien_34, R.drawable.sallyfrien_35,
                R.drawable.sallyfrien_36, R.drawable.sallyfrien_37, R.drawable.sallyfrien_38, R.drawable.sallyfrien_39,
                R.drawable.sallyfrien_40};
        ArrayList<EmoticonItem> emoticonItems1 = new ArrayList<EmoticonItem>();
        for (int i = 0; i < emoticonIds.length; i++) {
            emoticonItems1.add(new EmoticonItem(emoticonIds[i]));
        }
        emoticonAdapters[1] = new EmoticonAdapter(this, emoticonItems1, inComingMessageId);

        collectionEmoticonItems = new ArrayList<CollectionEmoticonItem>();
        collectionEmoticonItems.add(new CollectionEmoticonItem(emoticonAdapters[0]));
        collectionEmoticonItems.add(new CollectionEmoticonItem(emoticonAdapters[1]));

        collectionEmoticonAdapter = new CollectionEmoticonAdapter(this, collectionEmoticonItems);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(collectionEmoticonAdapter);

        tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.getTabAt(0).setIcon(R.drawable.finch_1);
        tabs.getTabAt(1).setIcon(R.drawable.sallyfrien_1);
=======
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
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
                    String content = message.getString("content");
                    if (!content.contains(CommonValue.AHIHI_KEY)) {
                        messageAdapter.addMessage(0, new MessageItem(type,
                                SpannableString.valueOf(content), 0));
                    } else {
                        content = content.substring(AHihiService.KEY_LENGTH + 1);
                        int emoticonId = Integer.parseInt(content);
                        SpannableString emoticon = commonMethod.toSpannableString(MessageActivity.this, emoticonId);
                        messageAdapter.addMessage(0, new MessageItem(type, emoticon, 1));
                    }
=======
                    messageAdapter.addMessage(0, new MessageItem(type, message.getString("content")));
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
            case R.id.imgEmoticon:
                if (!isOpenEmoticons) {
                    isOpenEmoticons = true;
                    emoticons.setVisibility(View.VISIBLE);
                    menu.setVisibility(View.GONE);
                    listViewMessage.setSelection(messageAdapter.getCount());
                } else {
                    isOpenEmoticons = false;
                    emoticons.setVisibility(View.GONE);
                    menu.setVisibility(View.VISIBLE);
                }
                break;
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
        Rect rect = new Rect();
        layoutMain.getWindowVisibleDisplayFrame(rect);
        int heightDiff = layoutMain.getRootView().getHeight() - (rect.bottom + rect.top);
        if (heightDiff > 100) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(View.GONE);
                    emoticons.setVisibility(View.GONE);
                    isOpenEmoticons = false;
                    listViewMessage.setSelection(messageAdapter.getCount());
                }
            });
        } else if (heightDiff <= 100 && emoticons.getVisibility() == View.GONE) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    menu.setVisibility(View.VISIBLE);
=======
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
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
                }
            });
        }
    }

    private class BroadcastMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.STATE_MESSAGE_SENT:
<<<<<<< HEAD
                    String key = intent.getStringExtra(CommonValue.AHIHI_KEY);
                    if (key == null) {
                        messageAdapter.addMessage(messageAdapter.getCount(), new MessageItem(
                                MessageAdapter.TYPE_OUTGOING, SpannableString.valueOf(intent
                                .getStringExtra(CommonValue.MESSAGE_CONTENT)), 0));
                    } else {
                        switch (key) {
                            case CommonValue.AHIHI_KEY_EMOTICON:
                                int emoticonId = Integer.parseInt(intent.getStringExtra(
                                        CommonValue.MESSAGE_CONTENT));
                                messageAdapter.addMessage(messageAdapter.getCount(),
                                        new MessageItem(MessageAdapter.TYPE_OUTGOING, commonMethod
                                                .toSpannableString(MessageActivity.this, emoticonId), 1));
                                break;
                        }
                    }
                    break;
                case CommonValue.STATE_MESSAGE_INCOMING:
                    String keyIncoming = intent.getStringExtra(CommonValue.AHIHI_KEY);
                    if (keyIncoming == null) {
                        messageAdapter.addMessage(messageAdapter.getCount(),
                                new MessageItem(MessageAdapter.TYPE_INCOMING, SpannableString
                                        .valueOf(intent.getStringExtra(CommonValue.MESSAGE_CONTENT)), 0));
                    } else {
                        switch (keyIncoming) {
                            case CommonValue.AHIHI_KEY_EMOTICON:
                                int emoticonId = Integer.parseInt(intent.getStringExtra(
                                        CommonValue.MESSAGE_CONTENT));
                                messageAdapter.addMessage(messageAdapter.getCount(),
                                        new MessageItem(MessageAdapter.TYPE_INCOMING, commonMethod
                                                .toSpannableString(MessageActivity.this, emoticonId), 1));
                                break;
                        }
                    }
=======
                    messageAdapter.addMessage(messageAdapter.getCount(),
                            new MessageItem(MessageAdapter.TYPE_OUTGOING, content));
                    break;
                case CommonValue.STATE_MESSAGE_INCOMING:
                    messageAdapter.addMessage(messageAdapter.getCount(),
                            new MessageItem(MessageAdapter.TYPE_INCOMING,
                                    intent.getStringExtra(CommonValue.MESSAGE_CONTENT)));
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
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
<<<<<<< HEAD
    public void onBackPressed() {
        if (isOpenEmoticons) {
            emoticons.setVisibility(View.GONE);
            isOpenEmoticons = false;
            listViewMessage.setSelection(messageAdapter.getCount());
            return;
        }
        super.onBackPressed();
    }

    @Override
=======
>>>>>>> 450878eed81f9005e0caa0c7701ceac98ca996e5
    protected void onDestroy() {
        this.unregisterReceiver(broadcastMessage);
        super.onDestroy();
    }

}