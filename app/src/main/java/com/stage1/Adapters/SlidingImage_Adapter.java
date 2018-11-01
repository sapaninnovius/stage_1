package com.stage1.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.R;
import com.stage1.Utils.PrefManager;

import java.util.ArrayList;
import java.util.List;


public class SlidingImage_Adapter extends PagerAdapter {


    private final List<GetAllMediaDetail.Datum> medialist;
    private ArrayList<Integer> IMAGES;
    private LayoutInflater inflater;
    private Context context;

/*
    public SlidingImage_Adapter(Context context,ArrayList<Integer> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
        medialist = null;
    }*/

    public SlidingImage_Adapter(Context context, List<GetAllMediaDetail.Datum> medialist) {
        this.context = context;
        this.medialist = medialist;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return medialist.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);


//        imageView.setImageResource(IMAGES.get(position));
        Glide.with(context).load(new PrefManager(context).getpath()+
                            medialist.get(position).getFilePath()).into(imageView);

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}