package com.castrec.stephane.androidnotev2.viewmodel;

import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.di.AppComponent;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.repositories.MessageRepository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by sca on 18/09/17.
 */

/**
 * Use AndroidViewModel instead of ViewModel because need
 * application context
 */

public class MessageListViewModel extends ViewModel implements AppComponent.Injectable {

  @Inject
  public MessageRepository repository;

  public MessageListViewModel() {
  }

  @Inject
  public MessageListViewModel(MessageRepository messageRepository) {
    this.repository = messageRepository;
  }

  public LiveData<List<MessageEntity>> getMessages(){
    return repository.loadMessages();
  }

  @Override
  public void inject(AppComponent appComponent) {
    appComponent.inject(this);
  }
}
