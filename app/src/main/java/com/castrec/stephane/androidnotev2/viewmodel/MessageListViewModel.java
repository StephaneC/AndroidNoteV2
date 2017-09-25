package com.castrec.stephane.androidnotev2.viewmodel;

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
public class MessageListViewModel extends ViewModel {

  private MessageRepository repository;

  @Inject
  public MessageListViewModel(MessageRepository messageRepository) {
    this.repository = messageRepository;
  }

  public LiveData<List<Message>> getMessages(){
    return repository.loadMessages();
  }
}
