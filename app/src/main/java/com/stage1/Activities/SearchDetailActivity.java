package com.stage1.Activities;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.stage1.Adapters.GalaryAdapter;
import com.stage1.Models.GetAllMediaDetail;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.R;
import com.stage1.Utils.ItemDecorationAlbumColumns;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchDetailActivity extends AppCompatActivity {

    private RecyclerView galary_list;
    private ProgressDialog pDialog;
    private RecyclerView.LayoutManager mLayoutManager;
    GalaryAdapter galaryAdapter;
    private String user;
    JsonParser jsonParser;
    JsonObject user_object;
    TextView username, address, role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_detail);
        user = getIntent().getStringExtra("user");
        jsonParser = new JsonParser();
        user_object = (JsonObject) jsonParser.parse(user);
        username = findViewById(R.id.txt_uname_search_detail);
        role = findViewById(R.id.txt_post_search_detail);
        address = findViewById(R.id.txt_address_search_detail);
        setData();
        galary_list = findViewById(R.id.rv_gallery_search_detail);
        pDialog = new ProgressDialog(this);
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        mLayoutManager = new GridLayoutManager(this, 3);
        galary_list.setLayoutManager(mLayoutManager);
        getImageList();
        galary_list.addItemDecoration(new ItemDecorationAlbumColumns(
                getResources().getDimensionPixelSize(R.dimen._3sdp),
                3));
    }

    private void setData() {
        username.setText(user_object.get("name").toString().replace("\"",""));
        address.setText(user_object.get("address").toString().replace("\"",""));
        SharedPreferences role_pref = new PrefManager(this).getRole_pref();
        Map<String, ?> allEntries = role_pref.getAll();
        for (Map.Entry<String, ?> entry : allEntries.entrySet()) {
            if (entry.getKey().equals(user_object.get("roleid").toString().replace("\"","")))
                role.setText(entry.getValue().toString());
            Log.d("map values", entry.getKey() + ": " + entry.getValue().toString());
        }
    }

    private void getImageList() {
        String userid = new PrefManager(this).getUser_pref().getString(User_Constant.id, "");//userid
        userid = user_object.get("id").toString().replace("\"","");
        if (userid.length() != 0) {
            ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<GetAllMediaDetail> getImageListcall = apiInterface.getallmediadetail(userid);

            // Show progressbar
            pDialog.show();
            getImageListcall.enqueue(new Callback<GetAllMediaDetail>() {
                @Override
                public void onResponse(Call<GetAllMediaDetail> call, Response<GetAllMediaDetail> response) {
                    if (response.body().getErrorCode() == 0 && response.body().getData().size() > 0)
                        medialist = response.body().getData();
                    loaddatatoadapter();
                    pDialog.dismiss();
                }

                @Override
                public void onFailure(Call<GetAllMediaDetail> call, Throwable t) {
                    pDialog.dismiss();
                }
            });

        }
    }

    private void loaddatatoadapter() {
        galaryAdapter = new GalaryAdapter(medialist, new GalaryAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int item, View v) {
//                startActivity(new Intent(getActivity(),ImageDialogActivity.class).putExtra("file",item));
//                startActivity(new Intent(getActivity().getApplicationContext(),ImageDialogActivity.class));
                //Toast.makeText(getActivity(), prepareData().get(item), Toast.LENGTH_SHORT).show();

                final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.activity_image_dialog, (ViewGroup) findViewById(R.id.img_dialog_constraint));
                // create a 300px width and 470px height PopupWindow
                Drawable dim = new ColorDrawable(Color.BLACK);
                ImageView img = layout.findViewById(R.id.img_dialog);
                final VideoView videoView = layout.findViewById(R.id.video_dialog);
                dim.setBounds(0, 0, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                dim.setAlpha((int) (255 * 1));
                layout.getOverlay().add(dim);
                final ProgressDialog pDialog = new ProgressDialog(videoView.getContext());


//                img.setImageResource(v.getimage);
                PopupWindow pw = new PopupWindow(layout, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT, true);
                // display the popup in the center
                pw.showAtLocation(v, Gravity.CENTER, 0, 0);
                if (medialist.get(item).getFileType() == 2)/* {
                    // Create a progressbar
//                    pDialog = new ProgressDialog(videoView.getContext());
                    // Set progressbar title
                    //pDialog.setTitle("Android Video Streaming Tutorial");
                    // Set progressbar message
//                    pDialog.setMessage("Buffering...");
                    pDialog.setIndeterminate(false);
//                    pDialog.setCancelable(false);
                    // Show progressbar
                    pDialog.show();
//                    img.setVisibility(View.GONE);
//                    videoView.setVisibility(View.VISIBLE);
                    MediaController mediacontroller = new MediaController(videoView.getContext());
                    mediacontroller.setMediaPlayer(videoView);
//                    mediacontroller.setAnchorView(videoView);
                    // Get the URL from String VideoURL
//                    Uri video = Uri.parse(new PrefManager(videoView.getContext()).getpath()+medialist.get(item).getFilePath());
                    Uri video = Uri.parse("http://ignitiveit.com/club/storage/app/profile_pic/DosIBEkPOB7fenVQ87d56H2LkM97WzAhSe5okgF3.mp4");
//                    MediaController mediacontroller  = new android.widget.MediaController(getContext());
                    videoView.setMediaController(mediacontroller);
                    videoView.setVideoURI(video);
                    videoView.requestFocus();
                    videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                        @Override
                        public boolean onError(MediaPlayer mp, int what, int extra) {
                            return false;
                        }
                    });
                    videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        // Close the progress bar and play the video
                        public void onPrepared(MediaPlayer mp) {
                            pDialog.dismiss();
                            videoView.start();
                        }

                    });
                    videoView.start();
                } */ {
                    pw.dismiss();
                    startActivity(new Intent(SearchDetailActivity.this, VideoViewActivity.class).putExtra("path", medialist.get(item).getFilePath()));
                } else {
                    videoView.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    v.buildDrawingCache();
                    Bitmap bitmap = v.getDrawingCache();
                    img.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onItemRemove(int item) {

            }

            @Override
            public void onNewItem() {

            }
        },1);
        galary_list.setAdapter(galaryAdapter);
    }

    List<GetAllMediaDetail.Datum> medialist;
}
