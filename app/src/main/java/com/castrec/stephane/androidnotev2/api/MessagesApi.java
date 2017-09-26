package com.castrec.stephane.androidnotev2.api;

import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.helper.JsonParser;
import com.castrec.stephane.androidnotev2.helper.NetworkHelper;
import com.castrec.stephane.androidnotev2.model.HttpResult;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.utils.Constants;

import android.util.Log;

import java.util.List;

/**
 * Created by sca on 18/09/17.
 */

public class MessagesApi {

  private String URL = "http://cesi.cleverapps.io/messages";

  public List<MessageEntity> getMessages(final String token) {
    try {
      HttpResult result = NetworkHelper.doGet(URL, null, token);

      if (result.status == 200) {
        // Convert the InputStream into a string
        return JsonParser.getMessages(result.json);
      }
      return null;
    } catch (Exception e) {
      Log.d(Constants.TAG, "Error occured in your AsyncTask : ", e);
      return null;
    }
  }
}
