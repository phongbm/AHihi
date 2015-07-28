package com.phongbm.ahihi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FriendAdapter extends BaseAdapter {
    private ArrayList<Friend> friends;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);


        friends = new ArrayList<Friend>();
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Friend getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item, parent, false);
        }
        TextView nameFriend = (TextView) convertView.findViewById(R.id.nameFriend);
        nameFriend.setText(friends.get(position).getName());
        return convertView;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

}