package com.phongbm.ahihi;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class FriendAdapter extends BaseAdapter {
    private static final String TAG = "FriendAdapter";

    private Handler handler;
    private ArrayList<FriendItem> friends;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context, Handler handler) {
        layoutInflater = LayoutInflater.from(context);
        this.handler = handler;
        friends = new ArrayList<FriendItem>();
        if (MainActivity.isNetworkConnected(context))
            initializeListFriend();
    }

    private void initializeListFriend() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ArrayList<String> listFriendId = (ArrayList<String>) currentUser.get("listFriend");
        if (listFriendId == null || listFriendId.size() == 0) {
            return;
        }
        for (int i = 0; i < listFriendId.size(); i++) {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("objectId", listFriendId.get(i));
            query.getFirstInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(ParseUser parseUser, ParseException e) {
                    friends.add(new FriendItem((String) parseUser.getObjectId(),
                            (String) parseUser.get("fullName")));
                    Message message = new Message();
                    message.what = CommonValue.UPDATE_LIST_FRIEND;
                    message.setTarget(handler);
                    message.sendToTarget();
                }
            });
        }

    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public FriendItem getItem(int position) {
        return friends.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_list_friend, parent, false);
        }
        TextView nameFriend = (TextView) convertView.findViewById(R.id.txtName);
        ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.imgAvatar);
        nameFriend.setText(friends.get(position).getName());
        return convertView;
    }

    public ArrayList<FriendItem> getFriends() {
        return friends;
    }

}