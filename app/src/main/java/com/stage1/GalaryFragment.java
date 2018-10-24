package com.stage1;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalaryFragment extends Fragment {


    private RecyclerView galary_list;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager_linear;

    public GalaryFragment() {
        // Required empty public constructor
    }


   /* @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_galary, container, false);
        return view;
    }
*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageButton img_btn_grid,img_btn_list;
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        final View view = inflater.inflate(R.layout.fragment_galary, container, false);
        galary_list = view.findViewById(R.id.rv_gallery);
        img_btn_grid = view.findViewById(R.id.img_btn_grid);
        img_btn_list = view.findViewById(R.id.img_btn_list);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager_linear = new LinearLayoutManager(getActivity().getApplicationContext());
        galary_list.setLayoutManager(mLayoutManager);
        galary_list.setLayoutManager(mLayoutManager);
        final GalaryAdapter galaryAdapter = new GalaryAdapter(prepareData(), new GalaryAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int item,View v) {
//                startActivity(new Intent(getActivity(),ImageDialogActivity.class).putExtra("file",item));
//                startActivity(new Intent(getActivity().getApplicationContext(),ImageDialogActivity.class));
                //Toast.makeText(getActivity(), prepareData().get(item), Toast.LENGTH_SHORT).show();

                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.activity_image_dialog,(ViewGroup) getActivity().findViewById(R.id.img_dialog_constraint));
                // create a 300px width and 470px height PopupWindow
                Drawable dim = new ColorDrawable(Color.BLACK);
                ImageView img = layout.findViewById(R.id.img_dialog);
                dim.setBounds(0, 0, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dim.setAlpha((int) (255 * 1));
                layout.getOverlay().add(dim);
                img.setImageResource(prepareData().get(item));
                PopupWindow pw = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                // display the popup in the center
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            }
        });
        galary_list.setAdapter(galaryAdapter);
//        galary_list.addItemDecoration(itemDecorator);
        galary_list.addItemDecoration(new ItemDecorationAlbumColumns(
                getResources().getDimensionPixelSize(R.dimen._3sdp),
                3));
        img_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galary_list.setLayoutManager(mLayoutManager_linear );
                galaryAdapter.notifyDataSetChanged();
            }
        });
        img_btn_grid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private List<Integer> prepareData() {
        List<Integer> img_list = new ArrayList<>();
        img_list.add(R.mipmap.a1);
        img_list.add(R.mipmap.a2);
        img_list.add(R.mipmap.a3);
        img_list.add(R.mipmap.a4);
        img_list.add(R.mipmap.a5);
        img_list.add(R.mipmap.a6);
        img_list.add(R.mipmap.a7);
        img_list.add(R.mipmap.a8);
        img_list.add(R.mipmap.a9);
        img_list.add(R.mipmap.a10);
        return img_list;
    }

}
