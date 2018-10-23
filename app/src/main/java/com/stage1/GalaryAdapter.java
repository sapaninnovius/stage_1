package com.stage1;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

public class GalaryAdapter extends RecyclerView.Adapter<GalaryAdapter.MyViewHolder> {

    private final List<Integer> img_list;

    public GalaryAdapter(List<Integer> img_list) {
        this.img_list=img_list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new GalaryAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.img_gallery.setImageResource(img_list.get(i));
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView img_gallery;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            img_gallery=itemView.findViewById(R.id.img_gallery);
        }
    }
}
