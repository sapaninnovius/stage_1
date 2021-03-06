package com.stage1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.stage1.Models.ResponseBar;
import com.stage1.Models.ResponseLogin;
import com.stage1.Models.ResponseUpdateProfile;
import com.stage1.ResponseRole;

import java.util.List;

public class PrefManager {
    SharedPreferences app_pref, user_pref,role_pref,bar_pref;
    SharedPreferences.Editor app_editor, user_editer,role_editer,bar_editer;
    Context _context;

    // shared app_pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Stage1";
    private static final String USER_DATA = "user_data";
    private static final String ROLE ="user_role";
    private static final String IS_LOG_IN = "islogin";
    private static final String BAR = "bar";

    public PrefManager(Context context) {
        this._context = context;
        app_pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        user_pref = _context.getSharedPreferences(USER_DATA, PRIVATE_MODE);
        role_pref = _context.getSharedPreferences(ROLE,PRIVATE_MODE);
        bar_pref = _context.getSharedPreferences(BAR,PRIVATE_MODE);
        _context.getSharedPreferences("app_config", PRIVATE_MODE).edit().putString("storage_path", "http://ignitiveit.com/club/storage/app/").commit();
        app_editor = app_pref.edit();
        user_editer = user_pref.edit();
        role_editer =role_pref.edit();
        bar_editer = bar_pref.edit();
    }

    public void setLogin(boolean isFirstTime) {
        app_editor.putBoolean(IS_LOG_IN, isFirstTime);
        app_editor.commit();
    }

    public String getpath() {
        return _context.getSharedPreferences("app_config", PRIVATE_MODE).getString("storage_path", "http://ignitiveit.com/club/storage/app/");
    }

    public boolean isLogin() {
        return app_pref.getBoolean(IS_LOG_IN, false);
    }


    public void storeUser(ResponseLogin.Data data) {
        user_editer.putString("id", data.getUserDetail().getId() + "");
        user_editer.putString("name", data.getUserDetail().getName() + "");
        user_editer.putString("email", data.getUserDetail().getEmail() + "");
        user_editer.putString("contact_number", data.getUserDetail().getContactNumber() + "");
        user_editer.putString("password", data.getUserDetail().getPassword() + "");
        user_editer.putString("profile_pic", data.getUserDetail().getProfilePic() + "");
        user_editer.putString("device_id", data.getUserDetail().getDeviceId() + "");
        user_editer.putString("role_id", data.getUserDetail().getRoleId() + "");
        user_editer.putString("bar_id", data.getUserDetail().getBarId() + "");
        user_editer.putString("lat", data.getUserDetail().getLat() + "");
        user_editer.putString("long", data.getUserDetail().getLong() + "");
        user_editer.putString("current_status", data.getUserDetail().getCurrentStatus() + "");
        user_editer.putString("status", data.getUserDetail().getStatus() + "");
        user_editer.putString("gander", data.getUserDetail().getGender() + "");
        user_editer.putString("created_at", data.getUserDetail().getCreatedAt() + "");
        user_editer.putString("updated_at", data.getUserDetail().getUpdatedAt() + "");
        user_editer.putString("street", data.getUserDetail().getStreet() + "");
        user_editer.putString("apt", data.getUserDetail().getApt() + "");
        user_editer.putString("city", data.getUserDetail().getCity() + "");
        user_editer.putString("zipcode", data.getUserDetail().getZipcode() + "");
        user_editer.putString("state", data.getUserDetail().getState() + "");
        user_editer.putString("address",data.getUserDetail().getAddress()+"");
        user_editer.commit();
        Log.d("Pref", user_pref.toString());
    }

    public SharedPreferences getUser_pref() {
        return user_pref;
    }

    public void clearData() {
        user_pref.edit().clear().commit();
        app_editor.putBoolean(IS_LOG_IN, false).commit();

    }

    public void updateUser(ResponseUpdateProfile.Data data) {
        user_editer.putString("id", data.getId() + "");
        user_editer.putString("name", data.getName() + "");
        user_editer.putString("email", data.getEmail() + "");
        user_editer.putString("contact_number", data.getContactNumber() + "");
        user_editer.putString("password", data.getPassword() + "");
        user_editer.putString("profile_pic", data.getProfilePic() + "");
        user_editer.putString("device_id", data.getDeviceId() + "");
        user_editer.putString("role_id", data.getRoleId() + "");
        user_editer.putString("bar_id", data.getBarId() + "");
        user_editer.putString("lat", data.getLat() + "");
        user_editer.putString("long", data.getLong() + "");
        user_editer.putString("current_status", data.getCurrentStatus() + "");
        user_editer.putString("status", data.getStatus() + "");
        user_editer.putString("created_at", data.getCreatedAt() + "");
        user_editer.putString("updated_at", data.getUpdatedAt() + "");
        user_editer.putString("street", data.getStreet() + "");
        user_editer.putString("apt", data.getApt() + "");
        user_editer.putString("city", data.getCity() + "");
        user_editer.putString("zipcode", data.getZipcode() + "");
        user_editer.putString("state", data.getState() + "");
        user_editer.putString("gander", data.getGender() + "");
        user_editer.putString("address",data.getAddress()+"");
        user_editer.commit();
        Log.d("Pref", user_pref.toString());
    }

    public void createList(List<ResponseRole.Data> data) {
        for (ResponseRole.Data row:data)
        {
            role_editer.putString(row.getId().toString(),row.getRole());
        }
        role_editer.commit();
        Log.d("Pref",role_pref.getAll().toString());
    }

    public SharedPreferences.Editor getRole_editer() {
        return role_editer;
    }

    public SharedPreferences getRole_pref() {
        return role_pref;
    }

    public void createBarList(List<ResponseBar.Datum> row) {
        JsonObject jsonObject;

        JsonArray jsonArray = new JsonArray();
        for (ResponseBar.Datum data:row)
        {
            jsonObject = new JsonObject();
            jsonObject.addProperty("id", data.getId() + "");
            jsonObject.addProperty("name", data.getName() + "");
            jsonObject.addProperty("email", data.getEmail() + "");
            jsonObject.addProperty("contact_number", data.getContactNumber() + "");
            jsonObject.addProperty("password", data.getPassword() + "");
            jsonObject.addProperty("profile_pic", data.getProfilePic() + "");
            jsonObject.addProperty("device_id", data.getDeviceId() + "");
            jsonObject.addProperty("role_id", data.getRoleId() + "");
            jsonObject.addProperty("bar_id", data.getBarId() + "");
            jsonObject.addProperty("lat", data.getLat() + "");
            jsonObject.addProperty("long", data.getLong() + "");
            jsonObject.addProperty("current_status", data.getCurrentStatus() + "");
            jsonObject.addProperty("status", data.getStatus() + "");
            jsonObject.addProperty("created_at", data.getCreatedAt() + "");
            jsonObject.addProperty("updated_at", data.getUpdatedAt() + "");
            jsonObject.addProperty("street", data.getStreet() + "");
            jsonObject.addProperty("apt", data.getApt() + "");
            jsonObject.addProperty("city", data.getCity() + "");
            jsonObject.addProperty("zipcode", data.getZipcode() + "");
            jsonObject.addProperty("state", data.getState() + "");
            jsonObject.addProperty("gander", data.getGender() + "");
            jsonObject.addProperty("address",data.getAddress()+"");
            jsonArray.add(jsonObject);
        }
        bar_editer.putString("bar",jsonArray.toString());
        bar_editer.commit();
        Log.d("Pref",bar_pref.getAll().toString());
    }

    public SharedPreferences getBar_Pref() {
        return bar_pref;
    }
}

