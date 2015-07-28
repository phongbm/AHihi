package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TabThreeFragment extends Fragment {
    private View view;
    private ListView listViewFriend;
    private FriendAdapter friendAdapter;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_three, null);

        listViewFriend = (ListView) view.findViewById(R.id.listViewFriend);
        friendAdapter = new FriendAdapter(getActivity());
        listViewFriend.setAdapter(friendAdapter);
        registerUpdateListFriend();
        return view;
    }

    private UpdateListFriend updateListFriend = new UpdateListFriend();

    public void registerUpdateListFriend() {
        IntentFilter filter = new IntentFilter();
        filter.addAction("UPDATE_LIST_FRIEND");

        getActivity().registerReceiver(updateListFriend, filter);
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