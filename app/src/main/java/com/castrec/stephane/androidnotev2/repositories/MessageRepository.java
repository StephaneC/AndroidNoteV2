package com.castrec.stephane.androidnotev2.repositories;

import com.castrec.stephane.androidnotev2.api.MessagesApi;
import com.castrec.stephane.androidnotev2.db.dao.MessageDao;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.session.Session;

import android.arch.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by SCA on 25/09/2017.
 * In charge of managing data source
 */
@Singleton
public class MessageRepository {

  private final Executor executor;

  private final MessagesApi api;

  private final MessageDao dao;


  @Inject
  public MessageRepository(MessagesApi api, MessageDao dao, Executor executor) {
    this.api = api;
    this.dao = dao;
    this.executor = executor;
  }

  public LiveData<List<Message>> loadMessages() {
    refreshMessages();
    return dao.loadAllMessages();
  }

  private void refreshMessages() {
    executor.execute(() -> {
      // running in a background thread
      // refresh the data
      List<Message> response = api.getMessages(Session.token);
      // TODO check for error etc.
      // Update the database.The LiveData will automatically refresh so
      // we don't need to do anything else here besides updating the database
      dao.insertMessage(response);

    });
  }

}
