package com.castrec.stephane.androidnotev2.repositories;

import com.castrec.stephane.androidnotev2.api.MessagesApi;
import com.castrec.stephane.androidnotev2.db.AppDatabase;
import com.castrec.stephane.androidnotev2.db.dao.MessageDao;
import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.session.Session;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by SCA on 25/09/2017.
 * In charge of managing data source
 */
@Singleton
public class MessageRepository {

    public  Executor executor;
    public MessagesApi api;
    public MessageDao dao;

    /*public MessageRepository(){
        executor = Executors.newSingleThreadExecutor();
    }*/

    @Inject
    public MessageRepository(MessagesApi api, MessageDao dao) {
        this.api = api;
        this.dao = dao;
        executor = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<MessageEntity>> loadMessages() {
        refreshMessages();
        return dao.loadAllMessages();
    }

    private void refreshMessages() {
        executor.execute(new Runnable() {

                                   @Override
                                   public void run() {
                                       // running in a background thread
                                       // refresh the data
                                       List<MessageEntity> response = api.getMessages(Session.token);
                                       // TODO check for error etc.
                                       // Update the database.The LiveData will automatically refresh so
                                       // we don't need to do anything else here besides updating the database
                                       if(response != null)
                                        dao.insertMessage(response);
                                   }
                               }

    /* {
      // running in a background thread
      // refresh the data
      List<Message> response = api.getMessages(Session.token);
      // TODO check for error etc.
      // Update the database.The LiveData will automatically refresh so
      // we don't need to do anything else here besides updating the database
      dao.insertMessage(response);

    }*/);
    }

}
