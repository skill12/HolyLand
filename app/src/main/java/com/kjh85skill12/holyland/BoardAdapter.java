package com.kjh85skill12.holyland;

import android.content.ClipData;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class BoardAdapter extends RecyclerView.Adapter {

    ArrayList<BoardItem> items;
    Context context;

    public BoardAdapter(ArrayList<BoardItem> items, Context context) {
        this.items = items;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.recycler_item,viewGroup,false);
        VH holder = new VH(itemView);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {

        BoardItem item = items.get(position);

        VH holder = (VH) viewHolder;
        holder.tvName.setText(item.tvName);
        holder.tvMsg.setText(item.tvMsg);
        holder.tvDate.setText(item.tvDate);
        Picasso.with(context).load(item.imgMain).into(holder.imgMain);
        Picasso.with(context).load(item.imgSelfi).into(holder.imgSelfi);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class VH extends RecyclerView.ViewHolder{

        ImageView imgMain;
        CircleImageView imgSelfi;
        TextView tvName;
        TextView tvMsg;
        TextView tvDate;

        public VH(@NonNull View itemView) {
            super(itemView);

            imgMain = itemView.findViewById(R.id.img_main);
            imgSelfi = itemView.findViewById(R.id.img_selfi);
            tvName = itemView.findViewById(R.id.tv_name);
            tvMsg = itemView.findViewById(R.id.tv_msg);
            tvDate = itemView.findViewById(R.id.tv_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    LayoutInflater inflater = LayoutInflater.from(context);
                    View layout = inflater.inflate(R.layout.dialog_show,null);

                    ImageView showIV = layout.findViewById(R.id.dialog_show_iv);

                    Picasso.with(context).load(items.get(getLayoutPosition()).imgMain).into(showIV);

                    builder.setView(layout);
                    builder.show();
                }
            });
        }
    }
}
