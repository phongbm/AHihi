package com.phongbm.message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phongbm.ahihi.R;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            if (messageItems.get(position).isCheck()) {
                convertView = layoutInflater.inflate(R.layout.item_message_left, parent, false);
            } else {
                convertView = layoutInflater.inflate(R.layout.item_message_right, parent, false);
            }
            viewHolder = new ViewHolder();
            viewHolder.txtMessage = (TextView) convertView.findViewById(R.id.txtMessage);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtMessage.setText(messageItems.get(position).getContent());
        return convertView;
    }

    private class ViewHolder {
        TextView txtMessage;
    }

    public void addMessage(MessageItem messageItem) {
        messageItems.add(0, messageItem);
        this.notifyDataSetChanged();
    }

}