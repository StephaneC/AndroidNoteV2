package com.castrec.stephane.androidnotev2.helper;

import com.castrec.stephane.androidnotev2.db.entity.MessageEntity;
import com.castrec.stephane.androidnotev2.model.Message;
import com.castrec.stephane.androidnotev2.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;


/**
 * Created by sca on 03/06/15.
 */
public class JsonParser {

    public static List<MessageEntity> getMessages(String json) throws JSONException {
        List<MessageEntity> messages = new LinkedList<>();
        JSONArray array = new JSONArray(json);
        JSONObject obj;
        MessageEntity msg;
        for (int i = 0; i < array.length(); i++) {
            obj = array.getJSONObject(i);
            msg = new MessageEntity();
            msg.setUsername(obj.optString("username"));
            msg.setMessage(obj.optString("message"));
            msg.setDate(obj.optLong("date"));
            messages.add(msg);
        }

        return messages;
    }

    public static String getToken(String response) throws JSONException {
        return new JSONObject(response).optString("token");
    }

    public static List<User> getUsers(String response) throws JSONException {
        JSONArray array = new JSONArray(response);
        List<User> users = new LinkedList<>();
        for (int i = 0; i < array.length(); i++) {
            users.add(new User(array.getString(i)));
        }
        return users;
    }
}
