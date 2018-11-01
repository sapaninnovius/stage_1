package com.stage1.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.stage1.Models.ResponseBar;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.ResponseRole;
import com.stage1.Utils.PrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
}
