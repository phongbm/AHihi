package com.phongbm.ahihi;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ContactItem> contactItems;
    private HashMap<String, Integer> alphaIndexer;
    private ArrayList<String> sections;

    public ContactAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        initializeListContact();

        alphaIndexer = new HashMap<String, Integer>();
        for (int i = 0; i < contactItems.size(); i++) {
            String firstLetter = contactItems.get(i).getName().substring(0, 1).toUpperCase();
            if (!alphaIndexer.containsKey(firstLetter)) {
                alphaIndexer.put(firstLetter, i);
            }
        }
        Set<String> sectionLetters = alphaIndexer.keySet();
        sections = new ArrayList<String>(sectionLetters);
        Collections.sort(sections);
    }

    private void initializeListContact() {
        contactItems = new ArrayList<ContactItem>();
        Cursor cursor = context.getContentResolver().query(Phone.CONTENT_URI, null, null, null,
                Phone.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(Phone.NUMBER));
            String name = cursor.getString(cursor.getColumnIndex(Phone.DISPLAY_NAME));
            String photo = cursor.getString(cursor.getColumnIndex(Phone.PHOTO_THUMBNAIL_URI));
            contactItems.add(new ContactItem(phoneNumber, name, photo));
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
        //if (convertView == null) {
        convertView = layoutInflater.inflate(R.layout.item_contact, null);
        viewHolder = new ViewHolder();
        viewHolder.imgContactIcon = (CircleImageView) convertView.findViewById(R.id.imgContactIcon);
        viewHolder.txtContactName = (TextView) convertView.findViewById(R.id.txtContactName);
        viewHolder.txtContactDescription = (TextView) convertView.findViewById(R.id.txtContactDescription);
        convertView.setTag(viewHolder);
        //} else {
        //viewHolder = (ViewHolder) convertView.getTag();
        //}
        if (contactItems.get(position).getPhoto() != null) {
            viewHolder.imgContactIcon.setImageURI(Uri.parse(contactItems.get(position).getPhoto()));
        }
        viewHolder.txtContactName.setText(contactItems.get(position).getName());
        viewHolder.txtContactDescription.setText(contactItems.get(position).getPhoneNumber());
        return convertView;
    }

    @Override
    public Object[] getSections() {
        return sections.toArray();
    }

    @Override
    public int getPositionForSection(int sectionIndex) {
        return alphaIndexer.get(sections.get(sectionIndex));
    }

    @Override
    public int getSectionForPosition(int position) {
        return 0;
    }

    private class ViewHolder {
        CircleImageView imgContactIcon;
        TextView txtContactName, txtContactDescription;
    }

}