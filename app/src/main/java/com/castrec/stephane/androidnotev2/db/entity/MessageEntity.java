package com.castrec.stephane.androidnotev2.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.castrec.stephane.androidnotev2.model.Message;

/**
 * Created by sca on 02/06/15.
 */
@Entity(tableName = "messages", foreignKeys = {
        @ForeignKey(entity = MessageEntity.class,
                parentColumns = "id",
                childColumns = "messageId",
                onDelete = ForeignKey.CASCADE)}, indices = {
        @Index(value = "messageId")
})
public class MessageEntity implements Message {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private int messageId;
    private String message;
    private String username;
    private long date;

    public void setUsername(String username){
        this.username = username;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDate(long date){
        this.date = date;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public long getDate() {
        return date;
    }
}
