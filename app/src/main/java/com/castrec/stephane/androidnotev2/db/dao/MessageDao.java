package com.castrec.stephane.androidnotev2.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.castrec.stephane.androidnotev2.db.DatabaseHelper;
import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.model.Message;

import java.util.LinkedList;
import java.util.List;


@Dao
public interface MessageDao {
    @Query("SELECT * FROM messages")
    LiveData<List<MessageEntity>> loadAllMessages();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMessage(List<MessageEntity> msg);
}