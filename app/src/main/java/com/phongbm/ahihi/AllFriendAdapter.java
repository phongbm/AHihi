package com.phongbm.ahihi;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phongbm.common.GlobalApplication;
import com.phongbm.common.OnShowPopupMenu;

import java.util.ArrayList;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllFriendAdapter extends BaseAdapter {
    private static final String TAG = "FriendAdapter";

    private ArrayList<AllFriendItem> allFriendItems;
    private LayoutInflater layoutInflater;
    private OnShowPopupMenu onShowPopupMenu;
    private HashMap<String, Integer> alphaIndexer;

    public AllFriendAdapter(Context context, Activity activity) {
        layoutInflater = LayoutInflater.from(context);

        allFriendItems = (ArrayList<AllFriendItem>) (((GlobalApplication)
                activity.getApplication()).getAllFriendItems().clone());

        alphaIndexer = new HashMap<>();
        for (int i = 0; i < allFriendItems.size(); i++) {
            String firstLetter = allFriendItems.get(i).getFullName().substring(0, 1).toUpperCase();
            if (!alphaIndexer.containsKey(firstLetter)) {
                alphaIndexer.put(firstLetter, i);
                allFriendItems.add(i, new AllFriendItem(firstLetter, 0));
            }
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
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (allFriendItems.get(position).getType() == 1) {
            return 1;
        }
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            if (getItemViewType(position) == 1) {
                convertView = layoutInflater.inflate(R.layout.item_all_friend, parent, false);
                viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
                viewHolder.menu = (ImageView) convertView.findViewById(R.id.menu);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_all_friend_header, parent, false);
            }
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.txtName);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (getItemViewType(position) == 1) {
            viewHolder.imgAvatar.setImageBitmap(allFriendItems.get(position).getAvatar());
            viewHolder.txtName.setText(allFriendItems.get(position).getFullName());
            viewHolder.menu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onShowPopupMenu.onShowPopupMenuListener(position, viewHolder.menu);
                }
            });
        } else {
            viewHolder.txtName.setText(allFriendItems.get(position).getId());
        }
        return convertView;
    }

    private class ViewHolder {
        CircleImageView imgAvatar;
        TextView txtName;
        ImageView menu;
    }

    public void setOnShowPopupMenu(OnShowPopupMenu onShowPopupMenu) {
        this.onShowPopupMenu = onShowPopupMenu;
    }

    public ArrayList<AllFriendItem> getAllFriendItems() {
        return allFriendItems;
    }

}