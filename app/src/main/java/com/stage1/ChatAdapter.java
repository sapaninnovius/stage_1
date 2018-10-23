package com.stage1;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            if (i%2!=0)
            {
                myViewHolder.chat_sent_item.setVisibility(View.VISIBLE);
                myViewHolder.chat_receive_item.setVisibility(View.GONE);
            }
            else
            {
                myViewHolder.chat_sent_item.setVisibility(View.GONE);
                myViewHolder.chat_receive_item.setVisibility(View.VISIBLE);
            }
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout chat_sent_item,chat_receive_item;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            chat_sent_item = itemView.findViewById(R.id.chat_item_sent);
            chat_receive_item = itemView.findViewById(R.id.chat_item_receive);

        }


    }
}
