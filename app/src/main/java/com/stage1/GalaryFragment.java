package com.stage1;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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
        View view = inflater.inflate(R.layout.fragment_galary, container, false);
        galary_list = view.findViewById(R.id.rv_gallery);
        img_btn_grid = view.findViewById(R.id.img_btn_grid);
        img_btn_list = view.findViewById(R.id.img_btn_list);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager_linear = new LinearLayoutManager(getActivity().getApplicationContext());
        galary_list.setLayoutManager(mLayoutManager);
        galary_list.setLayoutManager(mLayoutManager);
        final GalaryAdapter galaryAdapter = new GalaryAdapter(prepareData());
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
