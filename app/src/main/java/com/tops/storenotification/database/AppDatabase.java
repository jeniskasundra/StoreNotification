package com.tops.storenotification.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.tops.storenotification.dao.NotificationDao;
import com.tops.storenotification.model.NotificationModel;

@Database(entities = {NotificationModel.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase{
    public static final String DB_NAME = "app_db";
    public abstract NotificationDao notificationDao();
}
