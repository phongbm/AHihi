package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class TabContactFragment extends Fragment {
    private View view;
    private ListView listViewContact;
    private ContactAdapter contactAdapter;

    @SuppressLint("InflateParams")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_contact, null);
        initializeComponent();

        contactAdapter = new ContactAdapter(TabContactFragment.this.getActivity());
        listViewContact.setAdapter(contactAdapter);

        return view;
    }

    private void initializeComponent() {
        listViewContact = (ListView) view.findViewById(R.id.listViewContact);
    }


}