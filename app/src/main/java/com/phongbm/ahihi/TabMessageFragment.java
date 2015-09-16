package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phongbm.common.CommonValue;
import com.phongbm.common.GlobalApplication;
import com.phongbm.message.MessagesLogDBManager;
import com.phongbm.message.MessagesLogItem;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class TabMessageFragment extends Fragment {
    private static final String TAG = "TabMessageFragment";

    private View view;
    private ListView listViewMessage;
    private ArrayList<MessagesLogItem> messagesLogItems;
    private MessagesLogDBManager messagesLogDBManager;
    private MessageLogAdapter messageLogAdapter;
    private BroadcastMessageLog broadcastMessageLog;
    private ProgressDialog progressDialog;
    private RelativeLayout layoutNoConversations;

    public TabMessageFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_message, null);
        this.initializeComponent();
        messagesLogDBManager = new MessagesLogDBManager(context);

        messagesLogItems = messagesLogDBManager.getData();
        messageLogAdapter = new MessageLogAdapter();
        listViewMessage.setAdapter(messageLogAdapter);
        this.registerBroadcastMessageLog(context);

        if (messageLogAdapter.getCount() == 0) {
            listViewMessage.setVisibility(View.GONE);
            layoutNoConversations.setVisibility(View.VISIBLE);
        } else {
            listViewMessage.setVisibility(View.VISIBLE);
            layoutNoConversations.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()...TabMessageFragment");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()...");
        return view;
    }

    private void initializeComponent() {
        listViewMessage = (ListView) view.findViewById(R.id.listViewMessage);
        layoutNoConversations = (RelativeLayout) view.findViewById(R.id.layoutNoConversations);
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
                viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
                viewHolder.txtFullName = (TextView) convertView.findViewById(R.id.txtFullName);
                viewHolder.txtConent = (TextView) convertView.findViewById(R.id.txtContent);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.txtFullName.setText(messagesLogItems.get(position).getFullName());
            viewHolder.txtConent.setText(messagesLogItems.get(position).getMessage());

            int index = ((GlobalApplication) getActivity().getApplication()).getListIds()
                    .indexOf(messagesLogItems.get(position).getId());
            if (index > -1) {
                viewHolder.imgAvatar.setImageBitmap(((GlobalApplication) getActivity()
                        .getApplication()).getAllFriendItems().get(index).getAvatar());
            } else {
                viewHolder.imgAvatar.setImageResource(R.drawable.ic_avatar_default);
            }
            return convertView;
        }
    }

    private class ViewHolder {
        private CircleImageView imgAvatar;
        private TextView txtFullName, txtConent;
    }

    private void registerBroadcastMessageLog(Context context) {
        if (broadcastMessageLog == null) {
            broadcastMessageLog = new BroadcastMessageLog();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(CommonValue.UPDATE_MESSAGE_LOG);
            intentFilter.addAction(CommonValue.MESSAGE_LOG_STOP);
            context.registerReceiver(broadcastMessageLog, intentFilter);
        }
    }

    private class BroadcastMessageLog extends BroadcastReceiver {
        @Override
        public synchronized void onReceive(Context context, Intent intent) {
            if (listViewMessage.getVisibility() == View.GONE) {
                listViewMessage.setVisibility(View.VISIBLE);
                layoutNoConversations.setVisibility(View.GONE);
            }
            switch (intent.getAction()) {
                case CommonValue.UPDATE_MESSAGE_LOG:
                    Log.i(TAG, "BroadcastTabMessage... onReceive...");
                    String id = intent.getStringExtra(CommonValue.MESSAGE_LOG_ID);
                    String fullName = intent.getStringExtra(CommonValue.MESSAGE_LOG_FULL_NAME);
                    String content = intent.getStringExtra(CommonValue.MESSAGE_LOG_CONTENT);
                    String date = intent.getStringExtra(CommonValue.MESSAGE_LOG_DATE);

                    int indexSame = messagesLogItems.indexOf(new MessagesLogItem(id, fullName,
                            content, date, false));
                    if (indexSame >= 0) {
                        Log.i(TAG, "indexSame: " + indexSame);
                        messagesLogItems.remove(indexSame);
                    }
                    messagesLogItems.add(0, new MessagesLogItem(id, fullName, content, date, false));

                    messageLogAdapter.notifyDataSetChanged();
                    break;
                case CommonValue.MESSAGE_LOG_STOP:
                    progressDialog = new ProgressDialog(context);
                    progressDialog.setTitle("Welcome");
                    progressDialog.setMessage("Your account first loading. Please waiting after 10 second...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            messagesLogItems = messagesLogDBManager.getData();
                            messageLogAdapter.notifyDataSetChanged();
                            GlobalApplication.checkLoginThisId = true;
                            progressDialog.dismiss();
                        }
                    }, 10000);
                    break;
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "onDestroy... tab message...");
        messagesLogDBManager.closeDatabase();
        this.getActivity().unregisterReceiver(broadcastMessageLog);
        broadcastMessageLog = null;
        super.onDestroy();
    }

}