package com.castrec.stephane.androidnotev2.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.castrec.stephane.androidnotev2.db.dao.MessageDao;
import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.model.Message;

/**
 * Created by sca on 18/09/17.
 */

@Database(entities = {MessageEntity.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DATABASE_NAME = "notes-db";

    public abstract MessageDao messageDao();
}