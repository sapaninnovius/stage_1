package com.stage1.Adapters;

/*import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import java.util.List;

public class VideoAdapter {
}*/
//package com.stage1.Adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stage1.Activities.ProfileActivity;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.MyViewHolder> {

    private final List<GetAllMediaDetail.Datum> img_list;
    private final int x;
    OnItemClickListener onItemClickListener;

    public VideoAdapter(List<GetAllMediaDetail.Datum> img_list, OnItemClickListener onItemClickListener, int x) {
        this.img_list = img_list;
        this.onItemClickListener = onItemClickListener;
        this.x = x;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new VideoAdapter.MyViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.galary_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
//        if (img_list.get(i).getFileType() == 2) {
////            myViewHolder.shade_video.setVisibility(View.VISIBLE);
//        }
        if (i == 0) {
            myViewHolder.shade_video.setVisibility(View.VISIBLE);
            myViewHolder.img_gallery.setVisibility(View.GONE);
            myViewHolder.ibutton_remove_gallery.setVisibility(View.GONE);
        } else {
            Glide.with(myViewHolder.img_gallery.getContext()).load(new PrefManager(myViewHolder.img_gallery.getContext()).getpath() + img_list.get(i-1).getFilePath()).into(myViewHolder.img_gallery);
        }
    }

    @Override
    public int getItemCount() {
        if (img_list != null) {
            if (img_list.size() > 0)
                return img_list.size()+1;
            else
                return 1;
        } else
            return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_gallery;
        ImageButton ibutton_remove_gallery;
        View shade_video;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            itemView.setOnClickListener(this);
            img_gallery = itemView.findViewById(R.id.img_gallery);
            img_gallery.setOnClickListener(this);
            shade_video = itemView.findViewById(R.id.shade_video);
            shade_video.setOnClickListener(this
            );
            ibutton_remove_gallery = itemView.findViewById(R.id.ibutton_remove_gallery);
            ibutton_remove_gallery.setOnClickListener(this);
            if (x == 1)
                ibutton_remove_gallery.setVisibility(View.GONE);
        }

        @Override
        public void onClick(View v) {
            /*Bitmap bitmap = BitmapFactory.decodeResource(v.getResources(), img_list.get(getLayoutPosition()));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();*/
            switch (v.getId()) {
                case R.id.shade_video:
                    onItemClickListener.onNewItem();
                    break;
                case R.id.img_gallery:
                    onItemClickListener.onItemClick(getLayoutPosition(), v);
                    break;
                case R.id.ibutton_remove_gallery:
                    onItemClickListener.onItemRemove(getLayoutPosition());
                    break;
            }

//            int image = img_list.get(getLayoutPosition());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int item, View v);

        void onNewItem();

        void onItemRemove(int item);
    }
}
