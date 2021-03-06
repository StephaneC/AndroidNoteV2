package com.castrec.stephane.androidnotev2.db.dao;

import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.model.Message;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;


@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    LiveData<List<MessageEntity>> loadAllMessages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(List<MessageEntity> msg);
}