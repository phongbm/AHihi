package com.phongbm.ahihi;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.phongbm.libs.CircleTextView;

import java.util.ArrayList;
import java.util.Random;

public class CallLogAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private ArrayList<CallLogItem> callLogItems;
    private Random random = new Random();

    public CallLogAdapter(Context context, ArrayList<CallLogItem> callLogItems) {
        layoutInflater = LayoutInflater.from(context);
        this.callLogItems = callLogItems;
    }

    @Override
    public int getCount() {
        return callLogItems.size();
    }

    @Override
    public CallLogItem getItem(int position) {
        return callLogItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_call_log, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imgAvatar = (CircleTextView) convertView.findViewById(R.id.imgAvatar);
            viewHolder.txtFullName = (TextView) convertView.findViewById(R.id.txtFullName);
            viewHolder.txtPhoneNumber = (TextView) convertView.findViewById(R.id.txtPhoneNumber);
            viewHolder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            viewHolder.imgState = (ImageView) convertView.findViewById(R.id.imgState);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgAvatar.setCircleColor(Color.rgb(random.nextInt(256), random.nextInt(256),
                random.nextInt(256)));
        viewHolder.imgAvatar.setText(callLogItems.get(position).getFullName().substring(0, 1)
                .toUpperCase());
        viewHolder.txtFullName.setText(callLogItems.get(position).getFullName());
        viewHolder.txtPhoneNumber.setText("Mobile " + callLogItems.get(position).getPhoneNumber());
        viewHolder.txtDate.setText(callLogItems.get(position).getDate());
        String state = callLogItems.get(position).getState();
        switch (state) {
            case "outGoingCall":
                viewHolder.imgState.setImageResource(R.drawable.ic_call_log_outgoing_call);
                break;
            case "inComingCall":
                viewHolder.imgState.setImageResource(R.drawable.ic_call_log_incoming_call);
                break;
            case "missedCall":
                viewHolder.imgState.setImageResource(R.drawable.ic_call_log_missed_call);
                break;
        }
        return convertView;
    }

    private class ViewHolder {
        CircleTextView imgAvatar;
        TextView txtFullName, txtPhoneNumber, txtDate;
        ImageView imgState;
    }

}