package com.stage1.Fragments;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import com.google.gson.JsonObject;
import com.stage1.Adapters.GalaryAdapter;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.Utils.ItemDecorationAlbumColumns;
import com.stage1.R;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
   GalaryAdapter galaryAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageButton img_btn_grid, img_btn_list;
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
        getImageList();
        medialist = new ArrayList<>();

//        galary_list.addItemDecoration(itemDecorator);
        galary_list.addItemDecoration(new ItemDecorationAlbumColumns(
                getResources().getDimensionPixelSize(R.dimen._3sdp),
                3));
        img_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galary_list.setLayoutManager(mLayoutManager_linear);
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

    private void getImageList() {
        String userid = new PrefManager(getActivity()).getUser_pref().getString(User_Constant.id, "");
        if (userid.length() != 0) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<GetAllMediaDetail> getImageListcall = apiInterface.getallmediadetail(userid);
            getImageListcall.enqueue(new Callback<GetAllMediaDetail>() {
                @Override
                public void onResponse(Call<GetAllMediaDetail> call, Response<GetAllMediaDetail> response) {
                    if (response.body().getErrorCode() == 0 && response.body().getData().size() > 0)
                        medialist = response.body().getData();
                    loaddatatoadapter();
                }

                @Override
                public void onFailure(Call<GetAllMediaDetail> call, Throwable t) {

                }
            });

        }
    }

    private void loaddatatoadapter() {
        galaryAdapter = new GalaryAdapter(medialist, new GalaryAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int item, View v) {
//                startActivity(new Intent(getActivity(),ImageDialogActivity.class).putExtra("file",item));
//                startActivity(new Intent(getActivity().getApplicationContext(),ImageDialogActivity.class));
                //Toast.makeText(getActivity(), prepareData().get(item), Toast.LENGTH_SHORT).show();
                LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.activity_image_dialog, (ViewGroup) getActivity().findViewById(R.id.img_dialog_constraint));
                // create a 300px width and 470px height PopupWindow
                Drawable dim = new ColorDrawable(Color.BLACK);
                ImageView img = layout.findViewById(R.id.img_dialog);
                dim.setBounds(0, 0, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dim.setAlpha((int) (255 * 1));
                layout.getOverlay().add(dim);
                v.buildDrawingCache();
                Bitmap bitmap = v.getDrawingCache();
                img.setImageBitmap(bitmap);
//                img.setImageResource(v.getimage);
                PopupWindow pw = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                // display the popup in the center
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);


            }
        });
        galary_list.setAdapter(galaryAdapter);
    }

    List<GetAllMediaDetail.Datum> medialist;

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
