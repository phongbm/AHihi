package com.phongbm.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.phongbm.ahihi.R;
import com.phongbm.libs.TriangleShapeView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageAdapter extends BaseAdapter {
    public static final String TAG = "MessageAdapter";

    public static final int TYPE_OUTGOING = 0;
    public static final int TYPE_INCOMING = 1;

    private ArrayList<MessageItem> messageItems;
    private LayoutInflater layoutInflater;

    public MessageAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.messageItems = new ArrayList<MessageItem>();
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public MessageItem getItem(int position) {
        return messageItems.get(position);
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
        return messageItems.get(position).getType();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        int typePre = 0;
        if (position > 0) {
            typePre = getItemViewType(position - 1);
        }
        ViewHolder viewHolder;
        // if (convertView == null) {
        if (type == TYPE_OUTGOING) {
            convertView = layoutInflater.inflate(R.layout.item_message_outgoing, parent, false);
        } else {
            convertView = layoutInflater.inflate(R.layout.item_message_incoming, parent, false);
        }
        viewHolder = new ViewHolder();
        viewHolder.space = convertView.findViewById(R.id.space);
        viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
        viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
        viewHolder.imgTriangel = (TriangleShapeView) convertView.findViewById(R.id.imgTriangel);
        //convertView.setTag(viewHolder);
        // } else {
        //viewHolder = (ViewHolder) convertView.getTag();
        // }
        if (position > 0 && (type == TYPE_OUTGOING && typePre == TYPE_OUTGOING)
                || (type == TYPE_INCOMING && typePre == TYPE_INCOMING)) {
            viewHolder.imgAvatar.setVisibility(RelativeLayout.GONE);
            viewHolder.imgTriangel.setVisibility(RelativeLayout.GONE);
        }
        if (position > 0 && (type == TYPE_OUTGOING && typePre == TYPE_INCOMING)
                || (type == TYPE_INCOMING && typePre == TYPE_OUTGOING)) {
            viewHolder.space.setVisibility(RelativeLayout.VISIBLE);
        }
        viewHolder.txtMessage.setText(messageItems.get(position).getContent());
        return convertView;
    }

    private class ViewHolder {
        View space;
        TextView txtMessage;
        CircleImageView imgAvatar;
        TriangleShapeView imgTriangel;
    }

    public void addMessage(int position, MessageItem messageItem) {
        messageItems.add(position, messageItem);
        this.notifyDataSetChanged();
    }

}