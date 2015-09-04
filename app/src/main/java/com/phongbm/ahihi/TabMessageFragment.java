package com.phongbm.ahihi;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.phongbm.message.MessagesLogDBManager;
import com.phongbm.message.MessagesLogItem;

import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class TabMessageFragment extends Fragment {
    private static final String TAG = "TabOneFragment";
    private View view;
    private ListView listViewMessage;
    private ArrayList<MessagesLogItem> messagesLogItems;
    private MessagesLogDBManager messagesLogDBManager;

    public TabMessageFragment(Context context) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        view = layoutInflater.inflate(R.layout.tab_message, null);
        initializeComponent();
        messagesLogDBManager = new MessagesLogDBManager(context);
    }

//    public static TabMessageFragment getInstance() {
//        TabMessageFragment tabMessageFragment = new TabMessageFragment();
//
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "onCreate()...");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView()...");
        /*messagesLogItems = messagesLogDBManager.getData();
        Log.i(TAG, "LOG");
        for (int i = 0; i < messagesLogItems.size(); i++) {
            Log.i(TAG, messagesLogItems.get(i).getMessage());
        }*/
        return view;
    }

    private void initializeComponent() {
        listViewMessage = (ListView) view.findViewById(R.id.listViewMessage);
    }

}