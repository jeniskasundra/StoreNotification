package com.tops.storenotification.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.tops.storenotification.model.NotificationModel;

import java.util.List;
@Dao
public interface NotificationDao {

    @Query("SELECT * FROM notifications")
    List<NotificationModel> getAllNotification();

    @Insert
    void insertAll(NotificationModel notificationModel);

    @Query("DELETE FROM notifications")
    void clearAll();

}
