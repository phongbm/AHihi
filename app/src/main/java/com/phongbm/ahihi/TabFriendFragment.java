package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.phongbm.call.OutgoingCallActivity;
import com.phongbm.common.CommonValue;

import java.io.ByteArrayOutputStream;
import java.util.Collections;

@SuppressLint("ValidFragment")
public class TabFriendFragment extends Fragment implements AdapterView.OnItemClickListener,
        View.OnClickListener {
    private static final String TAG = "TabFriendFragment";

    private View view;
    private ListView listViewFriend;
    private AllFriendAdapter allFriendAdapter;
    private ActiveFriendAdapter activeFriendAdapter;
    private TextView btnTabActive, btnTabAllFriends;
    private BroadcastUpdateListFriend broadcastUpdateListFriend = new BroadcastUpdateListFriend();
    private boolean activeFriendAdapterVisible;

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CommonValue.ACTION_UPDATE_LIST_FRIEND:
                    allFriendAdapter.notifyDataSetChanged();
                    activeFriendAdapter.setActiveFriendItems(
                            allFriendAdapter.getActiveFriendItems());
                    activeFriendAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    public TabFriendFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_friend, null);
        this.initializeComponent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.registerUpdateListFriend();
        allFriendAdapter = new AllFriendAdapter(this.getActivity(), handler);
        activeFriendAdapter = new ActiveFriendAdapter(this.getActivity());
        listViewFriend.setAdapter(activeFriendAdapter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    private void initializeComponent() {
        listViewFriend = (ListView) view.findViewById(R.id.listViewFriend);
        listViewFriend.setOnItemClickListener(this);
        btnTabActive = (TextView) view.findViewById(R.id.btnTabActive);
        btnTabActive.setOnClickListener(this);
        btnTabAllFriends = (TextView) view.findViewById(R.id.btnTabAllFriends);
        btnTabAllFriends.setOnClickListener(this);
    }

    public void registerUpdateListFriend() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(CommonValue.ACTION_ADD_FRIEND);
        this.getActivity().registerReceiver(broadcastUpdateListFriend, filter);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String inComingId, inComingName;
        ByteArrayOutputStream avatar = new ByteArrayOutputStream();
        if (activeFriendAdapterVisible) {
            inComingId = activeFriendAdapter.getItem(position).getId();
            inComingName = activeFriendAdapter.getItem(position).getName();
            activeFriendAdapter.getItem(position).getAvatar()
                    .compress(Bitmap.CompressFormat.PNG, 80, avatar);
        } else {
            inComingId = allFriendAdapter.getItem(position).getId();
            inComingName = allFriendAdapter.getItem(position).getName();
            allFriendAdapter.getItem(position).getAvatar()
                    .compress(Bitmap.CompressFormat.PNG, 80, avatar);
        }

        Intent intentCall = new Intent(getActivity(), OutgoingCallActivity.class);
        intentCall.putExtra(CommonValue.INCOMING_CALL_ID, inComingId);
        intentCall.putExtra(CommonValue.INCOMING_CALL_NAME, inComingName);
        intentCall.putExtra(CommonValue.INCOMING_CALL_AVATAR, avatar.toByteArray());

        intentCall.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        this.getActivity().startActivity(intentCall);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnTabActive:
                changeStateShow(btnTabActive);
                changeStateHide(btnTabAllFriends);
                listViewFriend.setAdapter(activeFriendAdapter);
                activeFriendAdapterVisible = true;
                break;
            case R.id.btnTabAllFriends:
                changeStateShow(btnTabAllFriends);
                changeStateHide(btnTabActive);
                listViewFriend.setAdapter(allFriendAdapter);
                activeFriendAdapterVisible = false;
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


    private class BroadcastUpdateListFriend extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(CommonValue.ACTION_ADD_FRIEND)) {
                ActiveFriendItem newFriend = ((MainActivity) getActivity()).getNewFriend();
                allFriendAdapter.getFriends().add(newFriend);
                Collections.sort(allFriendAdapter.getFriends());
                allFriendAdapter.notifyDataSetChanged();

                if (intent.getBooleanExtra("isOnline", true)) {
                    activeFriendAdapter.getActiveFriendItems().add(newFriend);
                    activeFriendAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        this.getActivity().unregisterReceiver(broadcastUpdateListFriend);
        super.onDestroy();
    }

    public AllFriendAdapter getAllFriendAdapter() {
        return allFriendAdapter;
    }

    public ActiveFriendAdapter getActiveFriendAdapter() {
        return activeFriendAdapter;
    }

}