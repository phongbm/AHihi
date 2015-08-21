package com.phongbm.message;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
        if (convertView == null) {
            switch (type) {
                case TYPE_OUTGOING:
                    convertView = layoutInflater.inflate(R.layout.item_message_outgoing, parent, false);
                    break;
                case TYPE_INCOMING:
                    convertView = layoutInflater.inflate(R.layout.item_message_incoming, parent, false);
                    break;
            }
           /* if (position > 0 && (type == TYPE_OUTGOING && typePre == TYPE_OUTGOING)) {
                convertView = layoutInflater.inflate(R.layout.item_message_outgoing_2, parent, false);
            } else {
                if (position > 0 && (type == TYPE_INCOMING && typePre == TYPE_INCOMING)) {
                    convertView = layoutInflater.inflate(R.layout.item_message_incoming_2, parent, false);
                } else {
                    switch (type) {
                        case TYPE_OUTGOING:
                            convertView = layoutInflater.inflate(R.layout.item_message_outgoing, parent, false);
                            break;
                        case TYPE_INCOMING:
                            convertView = layoutInflater.inflate(R.layout.item_message_incoming, parent, false);
                            break;
                    }
                }
            }*/
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = (CircleImageView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.imgTriangel = (TriangleShapeView) convertView.findViewById(R.id.imgTriangel);
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        switch (type) {
            case TYPE_OUTGOING:
                viewHolder.imgAvatar.setImageResource(R.drawable.ic_ava_1);
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#4caf50"));
                break;
            case TYPE_INCOMING:
                viewHolder.imgAvatar.setImageResource(R.drawable.ic_ava_2);
                viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#ffffff"));
                break;
        }
        if (position > 0 && (type == TYPE_OUTGOING && typePre == TYPE_OUTGOING)
                || (type == TYPE_INCOMING && typePre == TYPE_INCOMING)) {
            viewHolder.imgAvatar.setImageResource(R.drawable.ic_transparent);
            viewHolder.imgTriangel.setBackgroundColor(Color.parseColor("#00000000"));
        }
        viewHolder.txtMessage.setText(messageItems.get(position).getContent());
        return convertView;
    }

    private class ViewHolder {
        TriangleShapeView imgTriangel;
        CircleImageView imgAvatar;
        TextView txtMessage;
    }

    public void addMessage(int position, MessageItem messageItem) {
        messageItems.add(position, messageItem);
        this.notifyDataSetChanged();
    }

}