package com.kjh85skill12.holyland;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<MessageItem> messageItems;
    LayoutInflater inflater;
    Context context;

    public ChatAdapter(ArrayList<MessageItem> messageItems, LayoutInflater inflater, Context context) {
        this.messageItems = messageItems;
        this.inflater = inflater;
        this.context = context;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MessageItem item = messageItems.get(position);

        View itemView = null;

        if(item.getName().equals(G.chatName)){
            itemView = inflater.inflate(R.layout.my_msgbox,parent,false);
        }else{
            itemView = inflater.inflate(R.layout.other_msgbox,parent,false);
        }

        CircleImageView iv = itemView.findViewById(R.id.iv);
        TextView tvName = itemView.findViewById(R.id.tv_name);
        TextView tvMsg = itemView.findViewById(R.id.tv_msg);
        TextView tvTime = itemView.findViewById(R.id.tv_time);

        tvName.setText(item.getName());
        tvMsg.setText(item.getMessage());
        tvTime.setText(item.getTime());

        Picasso.with(context).load(item.getProfileUrl()).into(iv);

        return itemView;
    }
}
