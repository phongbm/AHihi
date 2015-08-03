package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

@SuppressLint("ValidFragment")
public class TabContactFragment extends Fragment {
    private static final String TAG = "TabContactFragment";

    private Context context;
    private View view;
    private RecyclerView recyclerViewContact;

    public TabContactFragment(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_contact, null);
        initializeComponent();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerViewContact.setAdapter(new ContactAdapter(context));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return view;
    }

    private void initializeComponent() {
        recyclerViewContact = (RecyclerView) view.findViewById(R.id.recyclerViewContact);
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(context));
        recyclerViewContact.setHasFixedSize(true);
    }

}