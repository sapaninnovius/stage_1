package com.stage1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        RecyclerView rv_chat  = findViewById(R.id.rv_chat);
        ImageView img_back = findViewById(R.id.img_back);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_chat.setLayoutManager(linearLayoutManager);
        ChatAdapter chatAdapter = new ChatAdapter();
        rv_chat.setAdapter(chatAdapter);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }
}
