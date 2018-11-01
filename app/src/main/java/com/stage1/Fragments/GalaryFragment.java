package com.stage1.Fragments;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.PopupWindow;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.JsonObject;
import com.stage1.Activities.MainActivity;
import com.stage1.Activities.VideoViewActivity;
import com.stage1.Adapters.GalaryAdapter;
import com.stage1.Models.GetAllMediaDetail;

import com.stage1.Models.ResponseUploadMedia;
import com.stage1.Network.ApiClient;
import com.stage1.Network.ApiInterface;
import com.stage1.Utils.ItemDecorationAlbumColumns;
import com.stage1.R;
import com.stage1.Utils.PrefManager;
import com.stage1.Utils.User_Constant;
import com.stage1.Utils.Validators;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass.
 */
public class GalaryFragment extends Fragment {


    private static final int IMAGE_PICKER_SELECT = 1000;
    private RecyclerView galary_list;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.LayoutManager mLayoutManager_linear;
    private ImageButton ibtn_upload;
    private Uri fileupload;
    ProgressDialog pDialog;

    public GalaryFragment() {
        // Required empty public constructor
    }


    /* @Nullable
     @Override
     public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_galary, container, false);
         return view;
     }
 */
    GalaryAdapter galaryAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ImageButton img_btn_grid, img_btn_list;
        DividerItemDecoration itemDecorator = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        final View view = inflater.inflate(R.layout.fragment_galary, container, false);
        galary_list = view.findViewById(R.id.rv_gallery);
//        ibtn_upload = view.findViewById(R.id.ibtn_upload);
//        img_btn_grid = view.findViewById(R.id.img_btn_grid);
//        img_btn_list = view.findViewById(R.id.img_btn_list);
        pDialog = new ProgressDialog(getActivity());
        // Set progressbar title
//        pDialog.setTitle("Android Video Streaming Tutorial");
        // Set progressbar message
//        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        mLayoutManager_linear = new LinearLayoutManager(getActivity().getApplicationContext());
        galary_list.setLayoutManager(mLayoutManager);
        galary_list.setLayoutManager(mLayoutManager);
//        ibtn_upload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               /* Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                pickIntent.setType("image,video/*");
//                startActivityForResult(pickIntent, IMAGE_PICKER_SELECT);*/
//
//                if (ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.CAMERA)
//                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.WRITE_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(getActivity(),
//                        Manifest.permission.READ_EXTERNAL_STORAGE)
//                        != PackageManager.PERMISSION_GRANTED)
//                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
//                else
//                    uploadfile();
//
//            }
//        });
        getImageList();
        medialist = new ArrayList<>();

//        galary_list.addItemDecoration(itemDecorator);
        galary_list.addItemDecoration(new ItemDecorationAlbumColumns(
                getResources().getDimensionPixelSize(R.dimen._3sdp),
                3));
      /*  img_btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                galary_list.setLayoutManager(mLayoutManager_linear);
                galaryAdapter.notifyDataSetChanged();
            }
        });*/
     /*   img_btn_grid.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            }
        });
*/
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            uploadfile();
        }
    }

    private void uploadfile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/*", "video/*"});
        startActivityForResult(intent, IMAGE_PICKER_SELECT);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            Uri selectedMediaUri = data.getData();
            if (selectedMediaUri.toString().contains("image")) {
                //handle image
                fileupload = selectedMediaUri;
            } else if (selectedMediaUri.toString().contains("video")) {
                //handle video
                fileupload = selectedMediaUri;
            }
            uploadMedia();
        }
    }

    private void uploadMedia() {
        String userid = new PrefManager(getActivity()).getUser_pref().getString(User_Constant.id, "");
        //new Random().nextInt((max - min) + 1) + min;
        String title = "tittle";
        int file_type = 0;
        /*if (fileupload.toString().contains("image")) {
            file_type = 1;
        } else*/
        if (fileupload.toString().contains("video")) {
            file_type = 2;
        }
        /*com.stage1/cache/cropped2895647513990812363.jpg*/
//        File file = new File("file:///data/user/0/"+fileupload.getPath());
        String filepath = Validators.FileUtil.getPath(getActivity(), fileupload);
        File file = new File(filepath);
        MultipartBody.Part file1 = MultipartBody.Part.createFormData("file", title,
                RequestBody.create(MediaType.parse("multipart/form-data"), file));
        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> responseMediaUploadCall = apiInterface.uploadMedia(userid, title, file_type, file1);
        pDialog.show();
        responseMediaUploadCall.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                getImageList();
                pDialog.dismiss();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
            }
        });
    }

    private void getImageList() {
        String userid = new PrefManager(getActivity()).getUser_pref().getString(User_Constant.id, "");
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
        medialist.add(medialist.get(0));
        galaryAdapter = new GalaryAdapter(medialist, new GalaryAdapter.OnItemClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onItemClick(int item, View v) {
//                startActivity(new Intent(getActivity(),ImageDialogActivity.class).putExtra("file",item));
//                startActivity(new Intent(getActivity().getApplicationContext(),ImageDialogActivity.class));
                //Toast.makeText(getActivity(), prepareData().get(item), Toast.LENGTH_SHORT).show();

                final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //Inflate the view from a predefined XML layout
                View layout = inflater.inflate(R.layout.activity_image_dialog, (ViewGroup) getActivity().findViewById(R.id.img_dialog_constraint));
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
                    startActivity(new Intent(getContext(), VideoViewActivity.class).putExtra("path", medialist.get(item).getFilePath()));
                } else {
                    videoView.setVisibility(View.GONE);
                    img.setVisibility(View.VISIBLE);
                    v.buildDrawingCache();
                    Bitmap bitmap = v.getDrawingCache();
                    img.setImageBitmap(bitmap);
                }

            }

            @Override
            public void onItemRemove(final int item) {
                AlertDialog.Builder builder= new AlertDialog.Builder(getContext()).setMessage("Are you sure want to delete?").setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
                        Call<JsonObject> removeMedia = apiInterface.removeMedia(medialist.get(item).getId());
                        pDialog.show();
                        removeMedia.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if (Integer.parseInt(response.body().get("error_code").toString())==0)
                                {
                                    removeMedia(item);
                                    AlertDialog.Builder builder= new AlertDialog.Builder(getContext()).setMessage(response.body().get("message").toString()).setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();

                                        }
                                    });
                                    AlertDialog alertDialog = builder.create();
                                    alertDialog.show();
                                }
                                pDialog.dismiss();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                pDialog.dismiss();
                            }
                        });
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();


//                removeMedia(item);
//                Toast.makeText(getActivity(), ""+item, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNewItem() {

            }
        }, 0);
        galary_list.setAdapter(galaryAdapter);
    }

    private void removeMedia(int item) {
        medialist.remove(item);
        galaryAdapter.notifyItemRemoved(item);
/*        ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<JsonObject> removeMedia = apiInterface.removeMedia(item);
        pDialog.show();
        removeMedia.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                pDialog.dismiss();
                if (Integer.parseInt(response.body().get("error_code").toString()) == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext()).setMessage(response.body().get("message").toString());
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                pDialog.dismiss();
            }
        });*/
    }

    List<GetAllMediaDetail.Datum> medialist;

    private List<Integer> prepareData() {
        List<Integer> img_list = new ArrayList<>();
        img_list.add(R.mipmap.a1);
        img_list.add(R.mipmap.a2);
        img_list.add(R.mipmap.a3);
        img_list.add(R.mipmap.a4);
        img_list.add(R.mipmap.a5);
        img_list.add(R.mipmap.a6);
        img_list.add(R.mipmap.a7);
        img_list.add(R.mipmap.a8);
        img_list.add(R.mipmap.a9);
        img_list.add(R.mipmap.a10);
        return img_list;
    }

}
