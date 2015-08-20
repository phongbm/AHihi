package com.phongbm.ahihi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActiveFriendAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<ActiveFriendItem> activeFriendItems;

    public ActiveFriendAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        activeFriendItems = new ArrayList<ActiveFriendItem>();
    }

    public void setActiveFriendItems(ArrayList<ActiveFriendItem> activeFriendItems) {
        this.activeFriendItems = activeFriendItems;
    }

    @Override
    public int getCount() {
        return activeFriendItems.size();
    }

    @Override
    public ActiveFriendItem getItem(int position) {
        return activeFriendItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_active_friend, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            viewHolder.menu = (ImageView) convertView.findViewById(R.id.menu);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgAvatar.setImageBitmap(activeFriendItems.get(position).getAvatar());
        viewHolder.txtName.setText(activeFriendItems.get(position).getFullName());
        viewHolder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(parent.getContext(), view);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_open_chat:
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

    public ArrayList<ActiveFriendItem> getActiveFriendItems() {
        return activeFriendItems;
    }

}
