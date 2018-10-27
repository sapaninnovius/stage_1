package com.stage1.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stage1.Models.ResponseLogin;

public class PrefManager {
    SharedPreferences app_pref, user_pref;
    SharedPreferences.Editor app_editor, user_editer;
    Context _context;

    // shared app_pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "Stage1";
    private static final String USER_DATA = "user_data";

    private static final String IS_LOG_IN = "islogin";

    public PrefManager(Context context) {
        this._context = context;
        app_pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        user_pref = _context.getSharedPreferences(USER_DATA, PRIVATE_MODE);
        _context.getSharedPreferences("app_config",PRIVATE_MODE).edit().putString("storage_path","http://ignitiveit.com/club/storage/app/").commit();
        app_editor = app_pref.edit();
        user_editer = user_pref.edit();
    }

    public void setLogin(boolean isFirstTime) {
        app_editor.putBoolean(IS_LOG_IN, isFirstTime);
        app_editor.commit();
    }

    public String getpath()
    {
        return _context.getSharedPreferences("app_config",PRIVATE_MODE).getString("storage_path","http://ignitiveit.com/club/storage/app/");
    }

    public boolean isLogin() {
        return app_pref.getBoolean(IS_LOG_IN, false);
    }


    public void storeUser(ResponseLogin.Data data) {
        user_editer.putString("id", data.getUserDetail().getId()+"");
        user_editer.putString("name", data.getUserDetail().getName()+"");
        user_editer.putString("email", data.getUserDetail().getEmail()+"");
        user_editer.putString("contact_number", data.getUserDetail().getContactNumber()+"");
        user_editer.putString("password", data.getUserDetail().getPassword()+"");
        user_editer.putString("profile_pic", data.getUserDetail().getProfilePic()+"");
        user_editer.putString("device_id", data.getUserDetail().getDeviceId()+"");
        user_editer.putString("role_id", data.getUserDetail().getRoleId()+"");
        user_editer.putString("bar_id", data.getUserDetail().getBarId()+"");
        user_editer.putString("lat", data.getUserDetail().getLat()+"");
        user_editer.putString("long", data.getUserDetail().getLong()+"");
        user_editer.putString("current_status", data.getUserDetail().getCurrentStatus()+"");
        user_editer.putString("status", data.getUserDetail().getStatus()+"");
        user_editer.putString("created_at", data.getUserDetail().getCreatedAt()+"");
        user_editer.putString("updated_at", data.getUserDetail().getUpdatedAt()+"");
        user_editer.putString("street", data.getUserDetail().getStreet()+"");
        user_editer.putString("apt", data.getUserDetail().getApt()+"");
        user_editer.putString("city", data.getUserDetail().getCity()+"");
        user_editer.putString("zipcode", data.getUserDetail().getZipcode()+"");
        user_editer.putString("state", data.getUserDetail().getState()+"");
        user_editer.commit();
        Log.d("Pref",user_pref.toString());
    }

    public SharedPreferences getUser_pref() {
        return user_pref;
    }

    public void clearData() {
        user_pref.edit().clear().commit();
        app_editor.putBoolean(IS_LOG_IN,false).commit();

    }
}

