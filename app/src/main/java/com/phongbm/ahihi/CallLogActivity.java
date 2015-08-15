package com.phongbm.ahihi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class CallLogActivity extends AppCompatActivity {
    private CallLogsDBManager callLogsDBManager;
    private ListView listViewCallLog;
    private CallLogAdapter callLogAdapter;
    private ArrayList<CallLogItem> callLogItems;
    private RelativeLayout layoutNoCallLogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_call_log);
        this.initializeToolbar();
        this.initializeComponent();
        callLogsDBManager = new CallLogsDBManager(this);
        callLogItems = callLogsDBManager.getData();
        if (callLogItems.size() == 0) {
            listViewCallLog.setVisibility(RelativeLayout.GONE);
            layoutNoCallLogs.setVisibility(RelativeLayout.VISIBLE);
        } else {
            callLogAdapter = new CallLogAdapter(this, callLogItems);
            listViewCallLog.setAdapter(callLogAdapter);
        }
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initializeComponent() {
        listViewCallLog = (ListView) findViewById(R.id.listViewCallLog);
        layoutNoCallLogs = (RelativeLayout) findViewById(R.id.layoutNoCallLogs);
    }

    @Override
    protected void onDestroy() {
        callLogsDBManager.closeDatabase();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_call_log, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                CallLogActivity.this.finish();
                return true;
            case R.id.action_delete:
                final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("Delete");
                alertDialog.setMessage("All call logs will be deleted. Delete?");
                alertDialog.setCanceledOnTouchOutside(false);
                alertDialog.setCancelable(false);

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                alertDialog.dismiss();
                            }
                        });

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                callLogsDBManager.deleteAllData();
                                callLogItems.clear();
                                callLogAdapter.notifyDataSetChanged();
                                alertDialog.dismiss();
                                listViewCallLog.setVisibility(RelativeLayout.GONE);
                                layoutNoCallLogs.setVisibility(RelativeLayout.VISIBLE);
                            }
                        });

                alertDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}