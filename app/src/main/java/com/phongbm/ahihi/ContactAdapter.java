package com.phongbm.ahihi;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<ContactItem> contactItems;
    private Context context;

    public ContactAdapter(Context context) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.initializeArrayListContactItem();
    }

    private void initializeArrayListContactItem() {
        contactItems = new ArrayList<ContactItem>();
        Cursor cursor = context.getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String phoneNumber = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.NUMBER));
            String name = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String photo = cursor.getString(cursor.getColumnIndex(
                    ContactsContract.CommonDataKinds.Phone.PHOTO_THUMBNAIL_URI));
            contactItems.add(new ContactItem(phoneNumber, name, photo));
            cursor.moveToNext();
        }
        cursor.close();
    }

    @Override
    public int getItemCount() {
        return contactItems.size();
    }

    @Override
    public ContactViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        View view = layoutInflater.inflate(R.layout.item_contact, parent, false);
        return new ContactViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ContactViewHolder contactViewHolder, int position) {
        if (contactItems.get(position).getPhoto() != null) {
            contactViewHolder.imgContactIcon.setImageURI(Uri.parse(contactItems.get(position).getPhoto()));
        }
        contactViewHolder.txtContactName.setText(contactItems.get(position).getName());
        contactViewHolder.txtContactDescription.setText(contactItems.get(position).getPhoneNumber());
        return;
    }

    public class ContactViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView imgContactIcon;
        private TextView txtContactName, txtContactDescription;

        public ContactViewHolder(View itemView) {
            super(itemView);
            imgContactIcon = (CircleImageView) itemView.findViewById(R.id.imgContactIcon);
            txtContactName = (TextView) itemView.findViewById(R.id.txtContactName);
            txtContactDescription = (TextView) itemView.findViewById(R.id.txtContactDescription);
        }
    }

}