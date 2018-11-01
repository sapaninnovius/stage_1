package com.stage1.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.stage1.Activities.ChatActivity;
import com.stage1.Models.InboxModel;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import java.util.List;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {
    private final List<InboxModel> inboxModelList;

    public InboxAdapter(List<InboxModel> inboxModelList) {
        this.inboxModelList = inboxModelList;
    }

    @NonNull
    @Override
    public InboxAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new InboxAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.chat_user_name.setText(inboxModelList.get(i).getName());
        myViewHolder.chat_message.setText(inboxModelList.get(i).getMessage());
        Glide.with(myViewHolder.chat_profile.getContext()).load(new PrefManager(myViewHolder.chat_profile.getContext()).getpath()+inboxModelList.get(i).getImg()).into(myViewHolder.chat_profile);
    }

    @Override
    public int getItemCount() {
        return inboxModelList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView chat_message,chat_user_name;
        ImageView chat_profile;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            chat_message =itemView.findViewById(R.id.chat_message);
            chat_user_name = itemView.findViewById(R.id.chat_user_name);
            chat_profile = itemView.findViewById(R.id.chat_profile);

        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            intent.putExtra("receiver_id", inboxModelList.get(getLayoutPosition()).getId());
            intent.putExtra("receiver_name", inboxModelList.get(getLayoutPosition()).getName());
            intent.putExtra("receiver_img", inboxModelList.get(getLayoutPosition()).getImg());
            v.getContext().startActivity(intent);
        }
    }

}
