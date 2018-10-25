package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.stage1.R;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    FloatingActionButton fab,fab_cancel;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fab = findViewById(R.id.fab);
        fab_cancel = findViewById(R.id.fab_cancel);
        fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.VISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                fab_cancel.setVisibility(View.VISIBLE);
            }
        });
        fab_cancel.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
                fab.setVisibility(View.VISIBLE);
                fab_cancel.setVisibility(View.INVISIBLE);
            }
        });
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        /*toolbar = findViewById(R.id.my_toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.transparent));
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);*/
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem menu_edit = menu.add("");
        menu_edit.setIcon(R.drawable.ic_action_edit);
        menu_edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }
}
