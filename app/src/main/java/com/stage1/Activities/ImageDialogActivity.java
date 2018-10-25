package com.stage1.Activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.stage1.R;

public class ImageDialogActivity extends AppCompatActivity {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dialog);
//        ImageView img_dialog = findViewById(R.id.img_dialog);
//        img_dialog.setImageResource(R.mipmap.a5);
//        mDialog = (ImageView)findViewById(R.id.your_image);
//        mDialog.setClickable(true);
//        mDialog.setImageResource(R.mipmap.a3);
//        //finish the activity (dismiss the image dialog) if the user clicks
//        //anywhere on the image
//        mDialog.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//        byte[] file =getIntent().getByteArrayExtra("file");
//        Bitmap bmp = BitmapFactory.decodeByteArray(file,0,file.length);
//        mDialog.setImageBitmap(bmp);
//        getIntent().getIntExtra("file",R.mipmap.a3);
//        mDialog.setImageResource(image);
    }
}
/*
public class ImageDialogActivity extends Activity {

    private ImageView mDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_dialog);

        mDialog = (ImageView)findViewById(R.id.your_image);
        mDialog.setClickable(true);
        mDialog.setImageResource(R.mipmap.a3);
        //finish the activity (dismiss the image dialog) if the user clicks
        //anywhere on the image
        mDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
//        byte[] file =getIntent().getByteArrayExtra("file");
//        Bitmap bmp = BitmapFactory.decodeByteArray(file,0,file.length);
//        mDialog.setImageBitmap(bmp);
//        getIntent().getIntExtra("file",R.mipmap.a3);
//        mDialog.setImageResource(image);
    }
}
*/
