package com.stage1.Fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stage1.Adapters.InboxAdapter;
import com.stage1.Models.InboxModel;
import com.stage1.R;
import com.stage1.Utils.Chat_Constant;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InboxFragment extends Fragment {
    InboxAdapter inboxAdapter;
    RecyclerView inbox_list;
    RecyclerView.LayoutManager mLayoutManager;
    DatabaseReference databaseReference;
    SharedPreferences user_pref;
    Iterable<DataSnapshot> rowData;
    List<InboxModel> inboxModelList;

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
        inbox_list.addItemDecoration(itemDecorator);
        initControl();
        initInbox();
        return view;
    }

    private void initControl() {
        inboxModelList = new ArrayList<>();
        user_pref = new PrefManager(getActivity()).getUser_pref();
        databaseReference = FirebaseDatabase.getInstance().getReference("MESSAGES");
        databaseReference.addValueEventListener(valueEventListener());
    }

    private ValueEventListener valueEventListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                rowData = dataSnapshot.getChildren();
                inboxModelList.clear();
//                rowData=rowData;
                for (DataSnapshot row : rowData) {
                    InboxModel inboxModel = null;
                    
                    String msg = "";
                    String username = "";
                    String id = "";
                    String img = "";
                    String time = "";
                    if (row.getKey().toString().contains(user_pref.getString(User_Constant.id, ""))) {
                        inboxModel = new InboxModel();
                        for (DataSnapshot column : row.getChildren()) {
                            String receiver_id = column.child(Chat_Constant.receiver_id).getValue().toString();
                            String me = user_pref.getString(User_Constant.id,"");
                            if (!column.child(Chat_Constant.receiver_id).getValue().toString().equals(user_pref.getString(User_Constant.id, ""))) {
                                
                                id = column.child(Chat_Constant.receiver_id).getValue().toString();
                                username = column.child(Chat_Constant.receiver_name).getValue().toString();
                                img = column.child(Chat_Constant.receiver_img).getValue().toString();

                            } else {
                                id = column.child(Chat_Constant.sender_id).getValue().toString();
                                username = column.child(Chat_Constant.sender_name).getValue().toString();
                                img = column.child(Chat_Constant.sender_img).getValue().toString();
//                                msg = column.child(Chat_Constant.message).getValue().toString();
//                                time = column.child(Chat_Constant.time).getValue().toString();
                            }
                            msg = column.child(Chat_Constant.message).getValue().toString();
                            time = column.child(Chat_Constant.time).getValue().toString();
                            Log.d("inbox", "keys" + column.getKey());
                            inboxModel.setName(username);
                            inboxModel.setImg(img);
                            inboxModel.setMessage(msg);
                            inboxModel.setTime(time);
                            inboxModel.setId(id);
                            inboxModel.setNode(row.getKey());
                        }
                       
                        /*if (row.getKey().toString().contains(user_pref.getString(User_Constant.id,"")+"-"))
                        {
                            inboxModel.setId(user_pref.getString(User_Constant.id,""));
                        }
                        else
                        {

                        }*/
                        Log.d("inbox", "{ message:" + msg + ",username:" + username + ",img:" + img + ",time:" + time);
                    }
                    if (inboxModel!=null)
                    inboxModelList.add(inboxModel);
                }
                setData();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        return valueEventListener;
    }

    private void setData() {
        inboxAdapter = new InboxAdapter(inboxModelList);
        inbox_list.setAdapter(inboxAdapter);
    }

    private void initInbox() {
    }

}
