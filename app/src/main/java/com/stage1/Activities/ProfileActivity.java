package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stage1.Models.ResponseUpdateProfile;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.ResponseRole;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    private boolean newimage;
    private ProgressDialog pDialog;
    private EditText et_address;
    int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;
    private EditText et_gander;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        fab = findViewById(R.id.fab);
        fab_cancel = findViewById(R.id.fab_cancel);
        fab.setVisibility(View.GONE);
        profile_edit = findViewById(R.id.profile_edit);
        profile_view = findViewById(R.id.profile_show);
        et_address = (EditText) profile_edit.findViewById(R.id.et_address);
        et_gander = (EditText) profile_edit.findViewById(R.id.et_gender);
/*
        et_address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    PopupWindow popUp = popupWindowsort();
                    popUp.showAsDropDown(v, 0, 0); // show popup like dropdown list
                }
            }
        });*/
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                // TODO: Get info about the selected place.
                Log.i("profile", "Place: " + place.getName());
            }

            @Override
            public void onError(Status status) {
                // TODO: Handle the error.
                Log.i("profile", "An error occurred: " + status);
            }
        });
        et_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent =
                            new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                                    .build(ProfileActivity.this);
                    startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
                } catch (GooglePlayServicesRepairableException e) {
                    // TODO: Handle the error.
                } catch (GooglePlayServicesNotAvailableException e) {
                    // TODO: Handle the error.
                }
            }
        });
        /*et_gander.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final List<String> genderList = new ArrayList<>();
                genderList.add("Male");
                genderList.add("Female");
                PopupWindow popupWindow = popupWindowsort(genderList, et_gander);
                popupWindow.showAsDropDown(v, 0, 0);
            }
        });*/
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


    private PopupWindow popupWindowsort(final List<String> roleList_, final EditText et_view) {

        // initialize a pop up window type
        final PopupWindow popupWindow = new PopupWindow(this);

        ArrayList<String> sortList = new ArrayList<String>();
        sortList.add("A to Z");
        sortList.add("Z to A");
        sortList.add("Low to high price");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                roleList_);
        // the drop down list is a list view
        ListView listViewSort = new ListView(this);

        // set our adapter and pass our pop up window contents
        listViewSort.setAdapter(adapter);

        // set on item selected
        listViewSort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                et_view.setText(roleList_.get(position));
                popupWindow.dismiss();
            }
        });

        // some other visual settings for popup window
        popupWindow.setFocusable(true);

        popupWindow.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        // popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.white));
        popupWindow.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        // set the listview as popup content
        popupWindow.setContentView(listViewSort);

        return popupWindow;
    }

    private void setdata() {
        SharedPreferences user_pref = (new PrefManager(ProfileActivity.this)).getUser_pref();
        SharedPreferences role_pref = (new PrefManager(ProfileActivity.this)).getRole_pref();
        String path = new PrefManager(ProfileActivity.this).getpath() + user_pref.getString(User_Constant.profile_pic, "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png");
        if (user_pref.getString(User_Constant.profile_pic, "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png").equals("null")) {
            path = "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png";
        }
        if (path.length() <= new PrefManager(ProfileActivity.this).getpath().toString().length()) {
            path = "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png";
        }
        try {
            newimage = false;
            Glide.with(ProfileActivity.this).load(path).into((ImageView) findViewById(R.id.expandedImage));
            userID = user_pref.getString(User_Constant.id, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (role_pref.getString(user_pref.getString(User_Constant.role_id, ""), "").toLowerCase().equals("user")) {
            hideBar();
        }
        else
        {
            if (user_pref.getString(User_Constant.bar_id,"").equals("0"))
            {
                hideBar();
            }
        }
        setBar(user_pref);
        //view
        ((TextView) profile_view.findViewById(R.id.txt_email)).setText(user_pref.getString(User_Constant.email, ""));
        ((TextView) profile_view.findViewById(R.id.txt_username)).setText(user_pref.getString(User_Constant.name, ""));
        ((TextView) profile_view.findViewById(R.id.txt_phone_number_home)).setText(user_pref.getString(User_Constant.contact_number, ""));
        ((TextView) profile_view.findViewById(R.id.txt_address)).setText(user_pref.getString(User_Constant.address, ""));
        ((TextView) profile_view.findViewById(R.id.txt_role)).setText(role_pref.getString(user_pref.getString(User_Constant.role_id, ""), ""));
//        ((TextView) profile_view.findViewById(R.id.txt_bar)).setText(user_pref.getString(User_Constant.bar_id, ""));
        ((TextView) profile_view.findViewById(R.id.txt_gender)).setText(user_pref.getString(User_Constant.gander, ""));
        //edit
        ((EditText) profile_edit.findViewById(R.id.et_email)).setText(user_pref.getString(User_Constant.email, ""));
        ((EditText) profile_edit.findViewById(R.id.et_username)).setText(user_pref.getString(User_Constant.name, ""));
        ((EditText) profile_edit.findViewById(R.id.et_phone_number)).setText(user_pref.getString(User_Constant.contact_number, ""));
        ((EditText) profile_edit.findViewById(R.id.et_address)).setText(user_pref.getString(User_Constant.address, ""));
        ((EditText) profile_edit.findViewById(R.id.et_gender)).setText(user_pref.getString(User_Constant.gander, ""));
        ((EditText) profile_edit.findViewById(R.id.et_role)).setText(role_pref.getString(user_pref.getString(User_Constant.role_id, ""), ""));
//        ((EditText) profile_edit.findViewById(R.id.et_bar)).setText(user_pref.getString(User_Constant.bar_id, ""));
        /*Map<String, ?> allEntries = role_pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }*/
    }

    private void setBar(SharedPreferences user_pref) {
        SharedPreferences role_pref = (new PrefManager(ProfileActivity.this)).getBar_Pref();
        JsonParser parser = new JsonParser();
        JsonArray json = (JsonArray) parser.parse(role_pref.getString("bar",""));
        for (JsonElement row:json)
        {
            JsonObject jsonObject = (JsonObject) parser.parse(row.toString());
            String bar_id= jsonObject.get("bar_id").toString().replace("\"","");
            if (user_pref.getString(User_Constant.bar_id, "").equals(bar_id))
            {
                ((EditText) profile_edit.findViewById(R.id.et_bar)).setText(jsonObject.get("name").toString().replace("\"",""));
                ((TextView) profile_view.findViewById(R.id.txt_bar)).setText(jsonObject.get("name").toString().replace("\"",""));
            }
        }

//        Read more: http://www.java67.com/2016/10/3-ways-to-convert-string-to-json-object-in-java.html#ixzz5VUtldz3e
    }


    private void hideBar() {
        ((ImageView) profile_view.findViewById(R.id.img_bar)).setVisibility(View.GONE);
        ((TextView) profile_view.findViewById(R.id.txt_bar)).setVisibility(View.GONE);
        ((EditText) profile_edit.findViewById(R.id.et_bar)).setVisibility(View.GONE);
        ((ImageView) profile_edit.findViewById(R.id.img_edit_bar)).setVisibility(View.GONE);
        ((ConstraintLayout)profile_view.findViewById(R.id.bar_constraint)).setVisibility(View.GONE);
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
                ((EditText) profile_edit.findViewById(R.id.et_bar)).setEnabled(false);
                ((EditText) profile_edit.findViewById(R.id.et_role)).setEnabled(false);
                ((EditText) profile_edit.findViewById(R.id.et_email)).setEnabled(false);
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
                validate();

                break;
        }
    }

    private void validate() {
        EditText et_username = (EditText) profile_edit.findViewById(R.id.et_username);
        EditText et_phone_number = (EditText) profile_edit.findViewById(R.id.et_phone_number);
        EditText et_bar = (EditText) profile_edit.findViewById(R.id.et_bar);
        EditText et_address = (EditText) profile_edit.findViewById(R.id.et_address);
        EditText et_gender = (EditText) profile_edit.findViewById(R.id.et_gender);
        if (et_username.getText().toString().trim().length() == 0) {
            et_username.setError("Please enter username");
        }
        if (et_phone_number.getText().toString().trim().length() == 0) {
            et_phone_number.setError("Please enter phone number");
        }
        if (et_address.getText().toString().trim().length() == 0) {
            et_address.setError("Please enter address");
        }
        if (et_gender.getText().toString().trim().length() == 0) {
            et_gender.setError("Please enter gander");
        }
        if (et_username.getText().toString().trim().length() > 6 &&
                et_phone_number.getText().toString().trim().length() > 6 &&
                et_address.getText().toString().trim().length() > 6 &&
                et_gender.getText().toString().trim().length() > 3
                ) {
            updateProfile();
        }

    }

    private void updateProfile() {
        String street = "a", city = "a", apt = "a", zipcode = "a", state = "a";
        String address = "a, a, a";
        String deviceid = "adaf";
        String roleid = "";

        Map<String, ?> allEntries = new PrefManager(ProfileActivity.this).getRole_pref().getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
            if (entry.getValue().toString().toLowerCase().equals(((EditText) profile_edit.findViewById(R.id.et_role)).getText().toString().toLowerCase())) {
                roleid = entry.getKey();
            }
        }
        File file;
        SharedPreferences user_pref = new PrefManager(ProfileActivity.this).getUser_pref();
        String path = new PrefManager(ProfileActivity.this).getpath() + user_pref.getString(User_Constant.profile_pic, "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png");
        /*if (newimage == false && path.length() == new PrefManager(ProfileActivity.this).getpath().length()) {
            path = "http://ignitiveit.com/club/storage/app/profile_pic/OY0hbOqmxiJxFabzMIgJkCTIDCL9W9cHfEf4mvnM.png";
            file = new File((Uri.parse(path)).toString());
        } else if (newimage == false && path.length() != new PrefManager(ProfileActivity.this).getpath().length()) {
            file=new File(path);
        } else {
            file = new File(filePath.getPath());
        }*/
        if (newimage) {
            file = new File(filePath.getPath());
        } else {
            file = null;
        }
        Call<ResponseUpdateProfile> updateProfileCall;
        if (file != null) {
            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
//        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
            MultipartBody.Part file1 = MultipartBody.Part.createFormData("profile_pic", "profile.jpeg",
                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            updateProfileCall = apiInterface.updateProfile(
                    ((EditText) profile_edit.findViewById(R.id.et_username)).getText().toString(),
                    userID,
                    ((EditText) profile_edit.findViewById(R.id.et_phone_number)).getText().toString(),
                    deviceid,
                    roleid,
                    ((EditText) profile_edit.findViewById(R.id.et_bar)).getText().toString(),
                /*street,
                apt,
                city,
                zipcode,
                state,*/
                    ((EditText) profile_edit.findViewById(R.id.et_address)).getText().toString(),
                    file1,
                    ((EditText) profile_edit.findViewById(R.id.et_gender)).getText().toString()
            );
            pDialog.show();
        } else {
//            RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), file);
////        MultipartBody.Part image = MultipartBody.Part.createFormData("upload", file.getName(), reqFile);
//            MultipartBody.Part file1 = MultipartBody.Part.createFormData("profile_pic", "profile.jpeg",
//                    RequestBody.create(MediaType.parse("multipart/form-data"), file));
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            updateProfileCall = apiInterface.updateProfile(
                    ((EditText) profile_edit.findViewById(R.id.et_username)).getText().toString(),
                    userID,
                    ((EditText) profile_edit.findViewById(R.id.et_phone_number)).getText().toString(),
                    deviceid,
                    roleid,
                    ((EditText) profile_edit.findViewById(R.id.et_bar)).getText().toString(),
                /*street,
                apt,
                city,
                zipcode,
                state,*/
                    ((EditText) profile_edit.findViewById(R.id.et_address)).getText().toString(),
                    ((EditText) profile_edit.findViewById(R.id.et_gender)).getText().toString()
            );
            pDialog.show();
        }
        updateProfileCall.enqueue(new Callback<ResponseUpdateProfile>() {
            @Override
            public void onResponse(Call<ResponseUpdateProfile> call, Response<ResponseUpdateProfile> response) {
                if (response.body().getErrorCode() == 0) {
                    new PrefManager(ProfileActivity.this).updateUser(response.body().getData());
                    setdata();
                    updateFirebase();
                }
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseUpdateProfile> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void updateFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("MESSAGES");

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
                newimage = true;
                Glide.with(ProfileActivity.this).load(resultUri).into((ImageView) findViewById(R.id.expandedImage));
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                et_address.setText(place.getAddress());
                Log.i("profile", "Place: " + place.getName());
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);
                // TODO: Handle the error.
                Log.i("profile", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }
    }
}
