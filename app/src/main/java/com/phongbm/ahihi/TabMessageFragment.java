package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.phongbm.common.CommonValue;
import com.phongbm.message.MessagesLogDBManager;
import com.phongbm.message.MessagesLogItem;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class TabMessageFragment extends Fragment {
    private static final String TAG = "TabOneFragment";
    private View view;
    private ListView listViewMessage;
    private ArrayList<MessagesLogItem> messagesLogItems;
    private MessagesLogDBManager messagesLogDBManager;
    private MessageLogAdapter messageLogAdapter;
    private BroadcastMessageLog broadcastMessageLog;

    public TabMessageFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_message, null);
        initializeComponent();
        messagesLogDBManager = new MessagesLogDBManager(context);
        messagesLogItems = messagesLogDBManager.getData();
        messageLogAdapter = new MessageLogAdapter();
        listViewMessage.setAdapter(messageLogAdapter);
        this.registerBroadcastMessageLog(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()...");
        return view;
    }

    private void initializeComponent() {
        listViewMessage = (ListView) view.findViewById(R.id.listViewMessage);
    }

    /**
     * MessageLogAdapter
     */
    private class MessageLogAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return messagesLogItems.size();
        }

        @Override
        public MessagesLogItem getItem(int position) {
            return messagesLogItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(TabMessageFragment.this.getContext())
                        .inflate(R.layout.item_nearest_message, parent, false);
                viewHolder = new ViewHolder();
                // viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
                viewHolder.txtFullName = (TextView) convertView.findViewById(R.id.txtFullName);
                viewHolder.txtConent = (TextView) convertView.findViewById(R.id.txtContent);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txtFullName.setText(messagesLogItems.get(position).getFullName());
            viewHolder.txtConent.setText(messagesLogItems.get(position).getMessage());
            return convertView;
        }
    }

    private class ViewHolder {
        // private CircleImageView imgAvatar;
        private TextView txtFullName, txtConent;
    }

    private void registerBroadcastMessageLog(Context context) {
        if (broadcastMessageLog == null) {
            broadcastMessageLog = new BroadcastMessageLog();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.UPDATE_MESSAGE_LOG);
            context.registerReceiver(broadcastMessageLog, intentFilter);
        }
    }

    private class BroadcastMessageLog extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()) {
                case CommonValue.UPDATE_MESSAGE_LOG:
                    Log.i(TAG, "BroadcastTabMessage_ onReceive");
                    String id = intent.getStringExtra(CommonValue.MESSAGE_LOG_ID);
                    String fullName = intent.getStringExtra(CommonValue.MESSAGE_LOG_FULL_NAME);
                    String content = intent.getStringExtra(CommonValue.MESSAGE_LOG_CONTENT);
                    String date = intent.getStringExtra(CommonValue.MESSAGE_LOG_DATE);
                    /*int position = hasIdMessagesLogItem(id);
                    if (position > -1) {
                        messagesLogItems.get(position).setMessage("You: " + content);
                    } else {
                        messagesLogItems.add(0, new MessagesLogItem(id, fullName, content, date, false));
                    }*/
                    messagesLogItems.add(0, new MessagesLogItem(id, fullName, content, date, false));
                    messageLogAdapter.notifyDataSetChanged();
                    break;
            }
        }
    }

    /*private int hasIdMessagesLogItem(String id) {
        for (int i = 0; i < messagesLogItems.size(); i++) {
            if (id.equals(messagesLogItems.get(i).getId())) {
                return i;
            }
        }
        return -1;
    }*/

    @Override
    public void onDestroy() {
        messagesLogDBManager.closeDatabase();
        this.getActivity().unregisterReceiver(broadcastMessageLog);
        super.onDestroy();
    }

}