package com.stage1.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.stage1.R;

import java.util.List;

public class GalaryAdapter extends RecyclerView.Adapter<GalaryAdapter.MyViewHolder> {

    private final List<Integer> img_list;
    OnItemClickListener onItemClickListener;

    public GalaryAdapter(List<Integer> img_list, OnItemClickListener onItemClickListener) {
        this.img_list=img_list;
        this.onItemClickListener =onItemClickListener;
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

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_gallery;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            img_gallery=itemView.findViewById(R.id.img_gallery);
        }

        @Override
        public void onClick(View v) {
            /*Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), img_list.get(getLayoutPosition()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();*/
            onItemClickListener.onItemClick(getLayoutPosition(),v);
//            int image = img_list.get(getLayoutPosition());
        }
    }
    public interface OnItemClickListener {
        void onItemClick(int item,View v);
    }
}