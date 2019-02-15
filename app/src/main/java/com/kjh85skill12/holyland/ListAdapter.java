package com.kjh85skill12.holyland;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class ListAdapter extends BaseAdapter {

    ArrayList<PilgrimItem> pilgrimItems;
    LayoutInflater inflater;

    public ListAdapter(ArrayList<PilgrimItem> pilgrimItems, LayoutInflater inflater) {
        this.pilgrimItems = pilgrimItems;
        this.inflater = inflater;
    }

    @Override
    public int getCount() {
        return pilgrimItems.size();
    }

    @Override
    public Object getItem(int position) {
        return pilgrimItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.list_item,parent,false);
        }

        LinearLayout con = convertView.findViewById(R.id.list_contatiner);
        ImageView iv = convertView.findViewById(R.id.list_iv);
        TextView tv = convertView.findViewById(R.id.list_tv);

        PilgrimItem pilgrimItem = pilgrimItems.get(position);

        if(position==0) con.setBackgroundResource(R.drawable.list_back_01);
        else {
            switch (position%4){
                case 0:
                    con.setBackgroundResource(R.drawable.list_back_01);
                    break;
                case 1:
                    con.setBackgroundResource(R.drawable.list_back_02);
                    break;
                case 2:
                    con.setBackgroundResource(R.drawable.list_back_03);
                    break;
                case 3:
                    con.setBackgroundResource(R.drawable.list_back_04);
                    break;
            }
        }

        iv.setImageResource(pilgrimItem.imgId);
        tv.setText(pilgrimItem.mainText);

        return convertView;
    }
}
