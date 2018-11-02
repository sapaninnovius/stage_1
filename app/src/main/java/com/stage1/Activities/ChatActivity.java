package com.stage1.Activities;

import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.stage1.Adapters.ChatAdapter;
import com.stage1.Models.ChatNode;
import com.stage1.R;
import com.stage1.Utils.Chat_Constant;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private ImageView img_back;
    private RecyclerView rv_chat;
    private Button btn_send;
    private EditText et_msg;
    ChatNode chatNode;
    String chat_node;
    SharedPreferences user_pref;
    List<ChatNode> chatHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

//            chat_node = getIntent().getStringExtra("chatNode");
        rv_chat = findViewById(R.id.rv_chat);
        et_msg = findViewById(R.id.et_msg);
        img_back = findViewById(R.id.img_back);
        btn_send = findViewById(R.id.btn_send);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_chat.setLayoutManager(linearLayoutManager);
//        ChatAdapter chatAdapter = new ChatAdapter();
        initChat();
//        rv_chat.setAdapter(chatAdapter);
        img_back.setOnClickListener(this);
        btn_send.setOnClickListener(this);
    }

    private void initChat() {
        user_pref = new PrefManager(ChatActivity.this).getUser_pref();
        chatNode = new ChatNode();
        chatNode.setSender_id(user_pref.getString(User_Constant.id, "") + "");
        chatNode.setSender_name(user_pref.getString(User_Constant.name, "") + "");
        chatNode.setSender_img(user_pref.getString(User_Constant.profile_pic, "") + "");
        chatNode.setReceiver_id(getIntent().getStringExtra("receiver_id") + "");
        chatNode.setReceiver_name(getIntent().getStringExtra("receiver_name") + "");
        chatNode.setReceiver_img(getIntent().getStringExtra("receiver_img") + "");
//        chat_node = chatNode.getSender_id() + "-" + chatNode.getReceiver_id();
//        chat_node = "2-9";
        if (getIntent().getStringExtra("chatNode").equals("blank"))
        {
           chat_node =  createnode();
        }
        else {
            chat_node = getIntent().getStringExtra("chatNode") + "";
            mDatabase = FirebaseDatabase.getInstance().getReference("MESSAGES");
            chatHistory = new ArrayList<>();
            mDatabase.addValueEventListener(valueEventListener());
        }

//        mDatabase.addChildEventListener(childEventListener());
    }
    boolean flag=false;
    String node;
    private String createnode() {
        if (node==null)
            node = "";
        FirebaseDatabase.getInstance().getReference("MESSAGES").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                for (DataSnapshot row:iterable)
                {
                    if (row.getKey().equals(user_pref.getString(User_Constant.id,"")+"-"+getIntent().getStringExtra("receiver_id")))
                    {
                        node = user_pref.getString(User_Constant.id,"")+"-"+getIntent().getStringExtra("receiver_id");
                        flag=true;
                    }
                    else if (row.getKey().equals(getIntent().getStringExtra("receiver_id")+"-"+user_pref.getString(User_Constant.id,"")))
                    {
                        node = getIntent().getStringExtra("receiver_id")+"-"+user_pref.getString(User_Constant.id,"");
                        flag = true;
                    }
                    else
                    {
                        node = user_pref.getString(User_Constant.id,"")+"-"+getIntent().getStringExtra("receiver_id");
                        flag = false;
                    }
                    mDatabase = FirebaseDatabase.getInstance().getReference("MESSAGES");
                    chatHistory = new ArrayList<>();
                    mDatabase.addValueEventListener(valueEventListener());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return node;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                onBackPressed();
                break;
            case R.id.btn_send:
//                mDatabase.setValue("message");
                HashMap<String, String> chatmsg = null;
                if (et_msg.getText().toString().trim().length() > 0)
                    chatmsg = createChatNode();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference("MESSAGES");
//                HashMap hm = new HashMap();
//                hm.put("text", et_msg.getText().toString());
                if (chatmsg != null)
                    databaseReference.child(chat_node).push().setValue(chatmsg);
                et_msg.setText("");
                et_msg.requestFocus();
                break;
        }
    }

    private HashMap<String, String> createChatNode() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Chat_Constant.sender_id, chatNode.getSender_id());
        hashMap.put(Chat_Constant.sender_name, chatNode.getSender_name());
        hashMap.put(Chat_Constant.sender_img, chatNode.getSender_img());
        hashMap.put(Chat_Constant.receiver_id, chatNode.getReceiver_id());
        hashMap.put(Chat_Constant.receiver_name, chatNode.getReceiver_name());
        hashMap.put(Chat_Constant.receiver_img, chatNode.getReceiver_img());
        hashMap.put(Chat_Constant.time, new Date().getTime() + "");
        hashMap.put(Chat_Constant.message, et_msg.getText().toString());
        return hashMap;
    }



    ValueEventListener valueEventListener() {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                String totalData = dataSnapshot.child(chat_node).toString();
//                hideProgress();
                if (dataSnapshot.hasChildren()) {
                    Iterable<DataSnapshot> messageChildren = dataSnapshot.child(chat_node).getChildren();
//                messageList.clear();
                    Log.d("firebase", "" + chat_node);
                    Log.d("firebase", dataSnapshot.child(chat_node).toString());
                    Log.d("firebase", "" + dataSnapshot.child(chat_node).getChildrenCount());
                    chatHistory.clear();
                    for (DataSnapshot message : messageChildren) {

                        String receiverid = "";
                        String senderid = "";
                        String senderlastname = "";
                        String receiverfirstname = "";
                        String senderphoto = "";
                        String receoverphoto = "";
                        String text = "";
                        String timestamp = "";
                        String temp = message.getValue().toString();
                        String[] stringArray = temp.split(",");
                        Log.d("messages", message.getKey() + "==>" + message.getValue());
                        for (String value : stringArray) {
                            value = value.replace("{", "");
                            value = value.replace("}", "");
                            if (value.contains(Chat_Constant.sender_id))
                                senderid = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.sender_name))
                                senderlastname = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.sender_img))
                                senderphoto = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.receiver_id))
                                receiverid = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.receiver_name))
                                receiverfirstname = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.receiver_img))
                                receoverphoto = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.message))
                                text = value.substring(value.indexOf("=") + 1);
                            if (value.contains(Chat_Constant.time))
                                timestamp = value.substring(value.indexOf("=") + 1);
                        }
                        Log.d("messages", temp);
                        chatHistory.add(new ChatNode(receiverid, receiverfirstname, receoverphoto, senderid, senderlastname, senderphoto, text, timestamp));
                    }
                    Log.d("chathistory", chatHistory.toString());
                    if (chatHistory.size() > 0)
                        bindData();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        };
        return valueEventListener;
    }

    private void bindData() {
        user_pref.getString(User_Constant.id, "");
        if ((user_pref.getString(User_Constant.id, "") + "-").contains(chat_node)) {
            Glide.with(this).load(new PrefManager(this).getpath() + chatNode.getSender_img()).into((ImageView) findViewById(R.id.cr_img_receiver_img));
            ((TextView) findViewById(R.id.txt_receiver_name)).setText(chatNode.getSender_name());
        } else {
            Glide.with(this).load(new PrefManager(this).getpath() + chatNode.getReceiver_img()).into((ImageView) findViewById(R.id.cr_img_receiver_img));
            ((TextView) findViewById(R.id.txt_receiver_name)).setText(chatNode.getReceiver_name());
        }
        ChatAdapter chatAdapter = new ChatAdapter(chatHistory, user_pref.getString(User_Constant.id, ""));
        rv_chat.setAdapter(chatAdapter);
    }
}
