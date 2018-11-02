package com.stage1.Activities;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stage1.Models.ResponseBar;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.ResponseRole;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    SharedPreferences user_pref;
    DatabaseReference firebase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        user_pref = new PrefManager(this).getUser_pref();
        firebase = FirebaseDatabase.getInstance().getReference("MESSAGES");
        firebase.addChildEventListener(childEventListener());
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        preparerolelist();
        if (!(new PrefManager(this)).isLogin())
            startActivity(new Intent(this, AuthActivity.class));
        else
            startActivity(new Intent(this, MainActivity.class));
    }
    private void preparerolelist() {

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<ResponseRole> getRole = apiService.getRole();
        Call<ResponseBar> getBar = apiService.getBar();
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        getRole.enqueue(new Callback<ResponseRole>() {

            @Override
            public void onResponse(Call<ResponseRole> call, Response<ResponseRole> response) {
                if (response.body().getErrorCode() == 0 && response.body().getData().size() > 0) {
//                        roleList = response.body().getData();
                    new  PrefManager(SplashActivity.this).createList(response.body().getData());
                } else
                    Log.e(getClass().getName(), "something went wrong==>" + response.body().getErrorCode() + "==>" + response.body().getMessage() + "==>" + response.body().getData());
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<ResponseRole> call, Throwable t) {
                Log.e(getClass().getName(), t.getStackTrace().toString());
                pDialog.dismiss();
            }
        });
        getBar.enqueue(new Callback<ResponseBar>() {
            @Override
            public void onResponse(Call<ResponseBar> call, Response<ResponseBar> response) {
                if (response.body().getErrorCode() == 0 && response.body().getData().size() > 0) {
//                        roleList = response.body().getData();
                    new  PrefManager(SplashActivity.this).createBarList(response.body().getData());
                } else
                    Log.e(getClass().getName(), "something went wrong==>" + response.body().getErrorCode() + "==>" + response.body().getMessage() + "==>" + response.body().getData());
            }

            @Override
            public void onFailure(Call<ResponseBar> call, Throwable t) {
                Log.e(getClass().getName(), t.getStackTrace().toString());
            }
        });
    }


    ChildEventListener childEventListener(){
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                /*if (dataSnapshot.getKey().contains(user_pref.getString(User_Constant.id,""))){
                    Iterable<DataSnapshot> iterable = dataSnapshot.getChildren();
                }*/
                if (dataSnapshot.getKey().contains(user_pref.getString(User_Constant.id,"")))
                {
                    showNotificationMessage("Stage 1","You have a new message","12",new Intent(SplashActivity.this,MainActivity.class).putExtra("notification",true),"");
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        return childEventListener;
    }

    private void sendNotification() {

    }

    public void showNotificationMessage(final String title, final String message, final String timeStamp, Intent intent, String imageUrl) {
        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;


        // notification icon
        final int icon = R.mipmap.ic_launcher;

        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        final PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT
                );

        final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                this);

        final Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + this.getPackageName() + "/raw/notification");

        if (!TextUtils.isEmpty(imageUrl)) {

           /* if (imageUrl != null && imageUrl.length() > 4 && Patterns.WEB_URL.matcher(imageUrl).matches()) {

                Bitmap bitmap = getBitmapFromURL(imageUrl);*/

                /*if (bitmap != null) {
                    showBigNotification(bitmap, mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                } else */{
                    showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
                }
            /*}*/
        } else {
            showSmallNotification(mBuilder, icon, title, message, timeStamp, resultPendingIntent, alarmSound);
//            playNotificationSound();
        }
    }
    private void showSmallNotification(NotificationCompat.Builder mBuilder, int icon, String title, String message, String timeStamp, PendingIntent resultPendingIntent, Uri alarmSound) {

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.addLine(message);

        Notification notification;
        notification = mBuilder.setSmallIcon(icon).setTicker(title).setWhen(0)
                .setAutoCancel(true)
                .setContentTitle(title)
                .setContentIntent(resultPendingIntent)
                .setSound(alarmSound)
                .setStyle(inboxStyle)
//                .setWhen(getTimeMilliSec(timeStamp))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), icon))
                .setContentText(message)
                .build();

        NotificationManager notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1000, notification);
    }


}
