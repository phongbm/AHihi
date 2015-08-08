package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.phongbm.call.OutgoingCallActivity;

@SuppressLint("ValidFragment")
public class TabFriendFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    private static final String TAG = "TabFriendFragment";

    private View view;
    private ListView listViewFriend;
    private FriendAdapter friendAdapter;
    private TextView btnTabActive, btnTabAllFriends;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommonValue.UPDATE_LIST_FRIEND:
                    friendAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public TabFriendFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_friend, null);
        initializeComponent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()...");
        registerUpdateListFriend();
        friendAdapter = new FriendAdapter(getActivity(), handler);
        listViewFriend.setAdapter(friendAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView...");
        return view;
    }

    private void initializeComponent() {
        listViewFriend = (ListView) view.findViewById(R.id.listViewFriend);
        listViewFriend.setOnItemClickListener(this);
        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            listViewFriend.setNestedScrollingEnabled(true);
        }*/
        btnTabActive = (TextView) view.findViewById(R.id.btnTabActive);
        btnTabActive.setOnClickListener(this);
        btnTabAllFriends = (TextView) view.findViewById(R.id.btnTabAllFriends);
        btnTabAllFriends.setOnClickListener(this);
    }

    private UpdateListFriend updateListFriend = new UpdateListFriend();

    public void registerUpdateListFriend() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("UPDATE_LIST_FRIEND");
        getActivity().registerReceiver(updateListFriend, filter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intentCall = new Intent(getActivity(), OutgoingCallActivity.class);
        String inComingId = friendAdapter.getItem(position).getId();
        intentCall.putExtra(CommonValue.INCOMING_CALL_ID, inComingId);
        intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getActivity().startActivity(intentCall);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTabActive:
                changeStateShow(btnTabActive);
                changeStateHide(btnTabAllFriends);
                break;
            case R.id.btnTabAllFriends:
                changeStateShow(btnTabAllFriends);
                changeStateHide(btnTabActive);
                break;
        }
    }

    private void changeStateShow(TextView txt) {
        txt.setBackgroundResource(R.color.green_500);
        txt.setTextColor(Color.WHITE);
    }

    private void changeStateHide(TextView txt) {
        txt.setBackgroundResource(R.drawable.bg_rect_stroke_green);
        txt.setTextColor(this.getActivity().getResources().getColor(R.color.green_500));
    }


    private class UpdateListFriend extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("UPDATE_LIST_FRIEND")) {
                friendAdapter.getFriends().add(((MainActivity) getActivity()).getFriend());
                friendAdapter.notifyDataSetChanged();
            }
        }
    }

}