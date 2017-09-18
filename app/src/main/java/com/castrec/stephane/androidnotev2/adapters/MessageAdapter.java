package com.castrec.stephane.androidnotev2.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.castrec.stephane.androidnotev2.R;
import com.castrec.stephane.androidnotev2.model.Message;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by sca on 02/06/15.
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

  private final Context context;

  public MessageAdapter(Context ctx) {
    this.context = ctx;
  }

  List<Message> messages = new LinkedList<Message>();

  public void setMessages(List<Message> messages) {
    this.messages = messages;
    this.notifyDataSetChanged();
  }

  @Override
  public ViewHolder onCreateViewHolder(final ViewGroup parent, final int i) {
    LayoutInflater inflater = ((Activity) context).getLayoutInflater();
    View convertView = inflater.inflate(R.layout.item_message, parent, false);
    ViewHolder vh = new ViewHolder(convertView);
    return vh;
  }

  @Override
  public void onBindViewHolder(final ViewHolder vh, final int position) {
    vh.username.setText(messages.get(position).getUsername());
    vh.message.setText(messages.get(position).getMessage());
    /*try {
      vh.date.setText(DateHelper.getFormattedDate(messages.get(position).getDate()));
    } catch (ParseException e) {
      e.printStackTrace();
    }*/
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public int getItemCount() {
    if (messages == null) {
      return 0;
    }
    return messages.size();
  }


  public static class ViewHolder extends RecyclerView.ViewHolder{
    TextView username;
    TextView message;
    TextView date;

    public ViewHolder(final View itemView) {
      super(itemView);
      username = (TextView) itemView.findViewById(R.id.msg_user);
      message = (TextView) itemView.findViewById(R.id.msg_message);
      date = (TextView) itemView.findViewById(R.id.msg_date);
    }
  }
}
