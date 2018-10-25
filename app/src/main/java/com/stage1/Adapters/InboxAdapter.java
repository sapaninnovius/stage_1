package com.stage1.Adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.stage1.Activities.ChatActivity;
import com.stage1.R;

public class InboxAdapter extends RecyclerView.Adapter<InboxAdapter.MyViewHolder> {
    @NonNull
    @Override
    public InboxAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return  new InboxAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inbox_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull InboxAdapter.MyViewHolder myViewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            v.getContext().startActivity(new Intent(v.getContext(),ChatActivity.class));
        }
    }

}
