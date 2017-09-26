package com.castrec.stephane.androidnotev2.db.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import com.castrec.stephane.androidnotev2.model.Message;

/**
 * Created by sca on 02/06/15.
 */
@Entity(tableName = "messages")
public class MessageEntity {

    private int messageId;
    private String message;
    private String username;
    @PrimaryKey
    private long date;

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public void setUsername(String username){
        this.username = username;
    }

    public void setMessage(String message){
        this.message = message;
    }

    public void setDate(long date){
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public long getDate() {
        return date;
    }
}
