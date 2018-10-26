package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.stage1.R;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    FloatingActionButton fab, fab_cancel;
    MenuItem img_edit;
    ViewGroup profile_edit,profile_view;
    Button btn_update;
    @SuppressLint({"NewApi", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fab = findViewById(R.id.fab);
        fab_cancel = findViewById(R.id.fab_cancel);
        fab.setVisibility(View.INVISIBLE);
        profile_edit = findViewById(R.id.profile_edit);
        profile_view = findViewById(R.id.profile_show);
        btn_update = profile_edit.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
        setdata();
        /*fab.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onClick(View v) {
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.VISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.INVISIBLE);
                fab.setVisibility(View.INVISIBLE);
                img_edit.setVisible(true);
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
                img_edit.setVisible(false);
                fab_cancel.setVisibility(View.INVISIBLE);
            }
        });*/
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

    private void setdata() {
        SharedPreferences user_pref = (new PrefManager(ProfileActivity.this)).getUser_pref();
        ((TextView)profile_view.findViewById(R.id.txt_email)).setText(user_pref.getString(User_Constant.email,""));
        ((TextView)profile_view.findViewById(R.id.txt_username)).setText(user_pref.getString(User_Constant.name,""));
        ((TextView)profile_view.findViewById(R.id.txt_phone_number_home)).setText(user_pref.getString(User_Constant.contact_number,""));
        ((TextView)profile_view.findViewById(R.id.txt_address)).setText(user_pref.getString(User_Constant.apt+","+User_Constant.street+","+User_Constant.city+","+User_Constant.zipcode,""));
        ((TextView)profile_view.findViewById(R.id.txt_role)).setText(user_pref.getString(User_Constant.role_id,""));
        ((TextView)profile_view.findViewById(R.id.txt_bar)).setText(user_pref.getString(User_Constant.bar_id,""));
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuItem menu_edit = menu.add("");
         menu_edit.setIcon(R.drawable.ic_action_edit);
         menu_edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
         return super.onCreateOptionsMenu(menu);
     }*/
    MenuItem img_cancel;

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        img_edit = menu.add("edit");
        img_cancel = menu.add("cancel");
        img_edit.setIcon(getResources().getDrawable(R.drawable.ic_action_edit));
        img_cancel.setIcon(getResources().getDrawable(R.drawable.places_ic_clear));
        img_cancel.setVisible(false);
        img_edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        img_cancel.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        img_edit.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                img_edit.setVisible(false);
                img_cancel.setVisible(true);
                Toast.makeText(ProfileActivity.this, "edit", Toast.LENGTH_SHORT).show();
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.VISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.INVISIBLE);
                fab.setVisibility(View.VISIBLE);
                return false;
            }
        });
        img_cancel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                img_edit.setVisible(true);
                img_cancel.setVisible(false);
                fab.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_update:
                img_edit.setVisible(true);
                img_cancel.setVisible(false);
                fab.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                ((View)findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
                ((View)findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
                break;
        }
    }
}
