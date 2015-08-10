package com.phongbm.ahihi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.phongbm.common.CommonValue;

import java.util.ArrayList;
import java.util.Collections;

public class FriendAdapter extends BaseAdapter {
    private static final String TAG = "FriendAdapter";

    private Handler handler;
    private ArrayList<FriendItem> friendItems;
    private LayoutInflater layoutInflater;

    public FriendAdapter(Context context, Handler handler) {
        layoutInflater = LayoutInflater.from(context);
        this.handler = handler;
        friendItems = new ArrayList<FriendItem>();
        if (MainActivity.isNetworkConnected(context))
            this.initializeListFriend();
    }

    private void initializeListFriend() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        ArrayList<String> listFriendId = (ArrayList<String>) currentUser.get("listFriend");
        if (listFriendId == null || listFriendId.size() == 0) {
            return;
        }
        for (int i = 0; i < listFriendId.size(); i++) {
            ParseQuery<ParseUser> parseQuery = ParseUser.getQuery();
            parseQuery.whereEqualTo("objectId", listFriendId.get(i));
            parseQuery.getFirstInBackground(new GetCallback<ParseUser>() {
                @Override
                public void done(final ParseUser parseUser, ParseException e) {
                    ParseFile parseFile = (ParseFile) parseUser.get("avatar");
                    if (parseFile != null) {
                        parseFile.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] bytes, ParseException e) {
                                if (e == null) {
                                    Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0,
                                            bytes.length);
                                    friendItems.add(new FriendItem(parseUser.getObjectId(),
                                            (String) parseUser.get("fullName"), avatar));
                                    Collections.sort(friendItems);
                                }
                            }
                        });
                    }
                    Message message = new Message();
                    message.what = CommonValue.ACTION_UPDATE_LIST_FRIEND;
                    message.setTarget(handler);
                    message.sendToTarget();
                }
            });
        }
    }

    @Override
    public int getCount() {
        return friendItems.size();
    }

    @Override
    public FriendItem getItem(int position) {
        return friendItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_friend, parent, false);
        }
        TextView txtName = (TextView) convertView.findViewById(R.id.txtName);
        ImageView imgAvatar = (ImageView) convertView.findViewById(R.id.imgAvatar);
        txtName.setText(friendItems.get(position).getName());
        imgAvatar.setImageBitmap(friendItems.get(position).getAvatar());
        return convertView;
    }

    public ArrayList<FriendItem> getFriends() {
        return friendItems;
    }

}