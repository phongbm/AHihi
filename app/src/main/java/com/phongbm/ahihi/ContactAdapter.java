package com.phongbm.ahihi;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ContactItem> contactItems;

    public ContactAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        initializeListContact();
    }

    private void initializeListContact() {
        contactItems = new ArrayList<ContactItem>();
        Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null,
                Phone.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        String phoneNumber, name;
        while (!cursor.isAfterLast()) {
            phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            contactItems.add(new ContactItem(phoneNumber, name));
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public int getCount() {
        return contactItems.size();
    }

    @Override
    public ContactItem getItem(int position) {
        return contactItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.item_contact, null);
            viewHolder = new ViewHolder();
            viewHolder.txtContactName = (TextView) convertView.findViewById(R.id.txtContactName);
            viewHolder.txtContactDescription = (TextView) convertView.findViewById(R.id.txtContactDescription);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.txtContactName.setText(contactItems.get(position).getName());
        viewHolder.txtContactDescription.setText(contactItems.get(position).getPhoneNumber());
        return convertView;
    }

    private class ViewHolder {
        TextView txtContactName, txtContactDescription;
    }

}