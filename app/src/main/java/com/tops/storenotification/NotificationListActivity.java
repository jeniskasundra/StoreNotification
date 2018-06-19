package com.tops.storenotification;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tops.storenotification.adapter.NotificationAdapter;

public class NotificationListActivity extends AppCompatActivity {
    private RecyclerView rvNotificationList;
    private NotificationAdapter notificationAdapter;
    private Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        onBindView();
    }

    private void onBindView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        rvNotificationList = (RecyclerView) findViewById(R.id.rvNotificationList);
        rvNotificationList.setLayoutManager(new LinearLayoutManager(this));

    }

    public void setRecyclerView() {
        notificationAdapter = new NotificationAdapter(NotificationListActivity.this, MyApplication.getInstance().getDB().notificationDao().getAllNotification());
        rvNotificationList.setAdapter(notificationAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_notification, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_clear_all_notification:
                clearAllNotification();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void clearAllNotification() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(
                NotificationListActivity.this, R.style.AppAlertDialog);
        dialog.setTitle("Clear All");
        dialog.setMessage("Do you want to clear all notifications?");
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyApplication.getInstance().getDB().notificationDao().clearAll();
                setRecyclerView();
                Toast.makeText(NotificationListActivity.this, "All Notifications cleared successfully.",
                        Toast.LENGTH_SHORT).show();
            }
        });
        dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }
}
