package com.stage1.Activities;

import android.app.ProgressDialog;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.stage1.Adapters.ClubAdapter;
import com.stage1.Models.ResponseGetAllClub;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClubListActivity extends AppCompatActivity {
    List<ResponseGetAllClub.Datum> clublist;
    private Toolbar toolbar;
    private RecyclerView rv_club;
    private ClubAdapter clubAdapter;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_list);
        init();
        getallclub();
    }

    private void init() {
        toolbar = findViewById(R.id.my_club_toolbar);
        rv_club = findViewById(R.id.rec_club_list);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back_arrow);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        clublist = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_club.setLayoutManager(linearLayoutManager);
    }

    void getallclub() {
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseGetAllClub> clubCall = apiInterface.getClubs();
        pDialog.show();
        clubCall.enqueue(new Callback<ResponseGetAllClub>() {
            @Override
            public void onResponse(Call<ResponseGetAllClub> call, Response<ResponseGetAllClub> response) {
                pDialog.dismiss();
                if (response.body().getErrorCode() == 0) {
                    clublist = response.body().getData();
                    setData();
                } else
                    Toast.makeText(ClubListActivity.this, "" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseGetAllClub> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void setData() {
        clubAdapter = new ClubAdapter(clublist);
        rv_club.setAdapter(clubAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
