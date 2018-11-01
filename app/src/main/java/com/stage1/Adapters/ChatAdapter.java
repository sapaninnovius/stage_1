package com.stage1.Adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stage1.Models.ChatNode;
import com.stage1.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {
    private final List<ChatNode> chatHistory;
    private final String me;

    public ChatAdapter(List<ChatNode> chatHistory, String me) {
        this.chatHistory = chatHistory;
        this.me=me;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_item_message, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
            /*if (i%2!=0)
            {
                myViewHolder.chat_sent_item.setVisibility(View.VISIBLE);
                myViewHolder.chat_receive_item.setVisibility(View.GONE);
            }
            else
            {
                myViewHolder.chat_sent_item.setVisibility(View.GONE);
                myViewHolder.chat_receive_item.setVisibility(View.VISIBLE);
            }*/
            if (chatHistory.get(i).getSender_id().toString().trim().equals(me))
            {
                myViewHolder.chat_sent_item.setVisibility(View.VISIBLE);
                myViewHolder.chat_receive_item.setVisibility(View.GONE);
                ((TextView)myViewHolder.chat_sent_item.findViewById(R.id.text_message_body_sent)).setText(chatHistory.get(i).getMessage());
            }
            else
            {
                myViewHolder.chat_sent_item.setVisibility(View.GONE);
                myViewHolder.chat_receive_item.setVisibility(View.VISIBLE);
                ((TextView)myViewHolder.chat_receive_item.findViewById(R.id.text_message_body)).setText(chatHistory.get(i).getMessage());
            }
    }

    @Override
    public int getItemCount() {
        return chatHistory.size();
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
