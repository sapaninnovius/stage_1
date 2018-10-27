package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressLint({"NewApi", "RestrictedApi"})
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {
    Toolbar toolbar;
    CollapsingToolbarLayout toolbar_layout;
    FloatingActionButton fab, fab_cancel;
    MenuItem img_edit;
    ViewGroup profile_edit, profile_view;
    Button btn_update;
    ImageView expandedImage, img_pp_edit;
    private String userID;
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        fab = findViewById(R.id.fab);
        fab_cancel = findViewById(R.id.fab_cancel);
        fab.setVisibility(View.GONE);
        profile_edit = findViewById(R.id.profile_edit);
        profile_view = findViewById(R.id.profile_show);
        toolbar_layout = findViewById(R.id.toolbar_layout);
        btn_update = profile_edit.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);
//        toolbar_layout.setTitleEnabled(false);
        toolbar_layout.setTitleEnabled(false);
//        toolbar.setTitleTextColor(getResources().getColor(R.color.transparent));
        img_pp_edit = findViewById(R.id.img_pp_edit);
        expandedImage = findViewById(R.id.expandedImage);
        img_pp_edit.setVisibility(View.INVISIBLE);
        setdata();
        img_pp_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CropImage.activity()
                        .setGuidelines(CropImageView.Guidelines.ON)
                        .start(ProfileActivity.this);
            }
        });
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
        try {
            Glide.with(ProfileActivity.this).load(new PrefManager(ProfileActivity.this).getpath() + user_pref.getString(User_Constant.profile_pic, "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png")).into((ImageView) findViewById(R.id.expandedImage));
            userID = user_pref.getString(User_Constant.id, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //view
        ((TextView) profile_view.findViewById(R.id.txt_email)).setText(user_pref.getString(User_Constant.email, ""));
        ((TextView) profile_view.findViewById(R.id.txt_username)).setText(user_pref.getString(User_Constant.name, ""));
        ((TextView) profile_view.findViewById(R.id.txt_phone_number_home)).setText(user_pref.getString(User_Constant.contact_number, ""));
        ((TextView) profile_view.findViewById(R.id.txt_address)).setText(user_pref.getString(User_Constant.apt + "," + User_Constant.street + "," + User_Constant.city + "," + User_Constant.zipcode, ""));
        ((TextView) profile_view.findViewById(R.id.txt_role)).setText(user_pref.getString(User_Constant.role_id, ""));
        ((TextView) profile_view.findViewById(R.id.txt_bar)).setText(user_pref.getString(User_Constant.bar_id, ""));
        //edit
        ((EditText) profile_edit.findViewById(R.id.et_email)).setText(user_pref.getString(User_Constant.email, ""));
        ((EditText) profile_edit.findViewById(R.id.et_username)).setText(user_pref.getString(User_Constant.name, ""));
        ((EditText) profile_edit.findViewById(R.id.et_phone_number)).setText(user_pref.getString(User_Constant.contact_number, ""));
        ((EditText) profile_edit.findViewById(R.id.et_address)).setText(user_pref.getString(User_Constant.apt + "," + User_Constant.street + "," + User_Constant.city + "," + User_Constant.zipcode, ""));
        ((EditText) profile_edit.findViewById(R.id.et_role)).setText(user_pref.getString(User_Constant.role_id, ""));
        ((EditText) profile_edit.findViewById(R.id.et_bar)).setText(user_pref.getString(User_Constant.bar_id, ""));
    }

    /* @Override
     public boolean onCreateOptionsMenu(Menu menu) {
         MenuItem menu_edit = menu.add("");
         menu_edit.setIcon(R.drawable.ic_action_edit);
         menu_edit.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
         return super.onCreateOptionsMenu(menu);
     }*/
    MenuItem img_cancel;


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
                ((View) findViewById(R.id.profile_edit)).setVisibility(View.VISIBLE);
                ((View) findViewById(R.id.profile_show)).setVisibility(View.INVISIBLE);
                img_pp_edit.setVisibility(View.VISIBLE);
                return false;
            }
        });
        img_cancel.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                img_edit.setVisible(true);
                img_cancel.setVisible(false);
                img_pp_edit.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                ((View) findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
                ((View) findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
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
        switch (v.getId()) {
            case R.id.btn_update:
                img_edit.setVisible(true);
                img_cancel.setVisible(false);
                img_pp_edit.setVisibility(View.INVISIBLE);
                Toast.makeText(ProfileActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                ((View) findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
                ((View) findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
                updateProfile();
                break;
        }
    }

    private void updateProfile() {
        String street = "a", city = "a", apt = "a", zipcode = "a", state = "a";
        String deviceid = "adaf";
        File file = new File(filePath.getPath());
        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
        MultipartBody.Part file1 = MultipartBody.Part.createFormData("profile_pic", "profile.jpeg",
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> updateProfileCall = apiInterface.updateProfile(
                ((EditText) profile_edit.findViewById(R.id.et_username)).getText().toString(),
                userID,
                ((EditText) profile_edit.findViewById(R.id.et_phone_number)).getText().toString(),
                deviceid,
                ((EditText) profile_edit.findViewById(R.id.et_role)).getText().toString(),
                ((EditText) profile_edit.findViewById(R.id.et_bar)).getText().toString(),
                street,
                apt,
                city,
                zipcode,
                state,
                file1
        );
        updateProfileCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (!img_edit.isVisible()) {
            img_edit.setVisible(true);
            img_cancel.setVisible(false);
            img_pp_edit.setVisibility(View.INVISIBLE);
            Toast.makeText(ProfileActivity.this, "cancel", Toast.LENGTH_SHORT).show();
            ((View) findViewById(R.id.profile_edit)).setVisibility(View.INVISIBLE);
            ((View) findViewById(R.id.profile_show)).setVisibility(View.VISIBLE);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                filePath = result.getUri();
                Glide.with(ProfileActivity.this).load(resultUri).into((ImageView) findViewById(R.id.expandedImage));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
