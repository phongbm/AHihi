package com.phongbm.ahihi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class AllFriendAdapter extends BaseAdapter {
    private static final String TAG = "FriendAdapter";

    private Handler handler;
    private ArrayList<AllFriendItem> allFriendItems;
    private ArrayList<ActiveFriendItem> activeFriendItems;
    private LayoutInflater layoutInflater;

    public AllFriendAdapter(Context context, Handler handler) {
        layoutInflater = LayoutInflater.from(context);
        this.handler = handler;
        allFriendItems = new ArrayList<AllFriendItem>();
        activeFriendItems = new ArrayList<ActiveFriendItem>();
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
                    if (parseFile == null) {
                        return;
                    }
                    parseFile.getDataInBackground(new GetDataCallback() {
                        @Override
                        public void done(byte[] bytes, ParseException e) {
                            if (e != null) {
                                return;
                            }
                            Bitmap avatar = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            allFriendItems.add(new AllFriendItem(parseUser.getObjectId(), avatar,
                                    parseUser.getUsername(), parseUser.getString("fullName")));
                            Collections.sort(allFriendItems);

                            if (parseUser.getBoolean("isOnline")) {
                                activeFriendItems.add(new ActiveFriendItem(parseUser.getObjectId(),
                                        avatar, parseUser.getUsername(), parseUser.getString("fullName")));
                            }
                        }
                    });
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
        return allFriendItems.size();
    }

    @Override
    public AllFriendItem getItem(int position) {
        return allFriendItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_all_friend, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.menu = (ImageView) convertView.findViewById(R.id.menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgAvatar.setImageBitmap(allFriendItems.get(position).getAvatar());
        viewHolder.txtName.setText(allFriendItems.get(position).getFullName());
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(parent.getContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_open_chat:
                                Message message = new Message();
                                message.what = CommonValue.WHAT_OPEN_CHAT;
                                message.arg1 = position;
                                message.setTarget(handler);
                                message.sendToTarget();
                                break;
                            case R.id.action_voice_call:
                                break;
                            case R.id.action_view_profile:
                                break;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        CircleImageView imgAvatar;
        TextView txtName;
        ImageView menu;
    }

    public ArrayList<AllFriendItem> getAllFriendItems() {
        return allFriendItems;
    }

    public ArrayList<ActiveFriendItem> getActiveFriendItems() {
        return activeFriendItems;
    }

}