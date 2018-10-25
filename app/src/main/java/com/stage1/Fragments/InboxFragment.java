package com.stage1.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.stage1.Adapters.InboxAdapter;
import com.stage1.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {
    InboxAdapter inboxAdapter;
    RecyclerView inbox_list;
    RecyclerView.LayoutManager mLayoutManager;
    public InboxFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        View view = inflater.inflate(R.layout.fragment_inbox, container, false);
        inbox_list = view.findViewById(R.id.rv_inbox);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        inbox_list.setLayoutManager(mLayoutManager);
        inboxAdapter = new InboxAdapter();
        inbox_list.setAdapter(inboxAdapter);
        inbox_list.addItemDecoration(itemDecorator);
        return view;
    }

}
