package com.project.my.cameratest;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;

public class MainActivity extends Activity {
    private ImageView mImageView;
    private Button mButtonCamera;
    private Button mButtonPhoto;
    private Bitmap bitMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = (ImageView) this.findViewById(R.id.imageview_preview);
        mButtonCamera = (Button) this.findViewById(R.id.button_cameraButton);
        mButtonPhoto = (Button) this.findViewById(R.id.button_photoButton);

        mButtonCamera.setOnClickListener(new OnClickListener() { //打开Camera
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(new File(Environment
                                .getExternalStorageDirectory(), "camera.jpg")));
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                startActivityForResult(intent, 10);
            }
        });

        mButtonPhoto.setOnClickListener(new OnClickListener() {  //获取相册
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image*//*");
                intent.putExtra("crop", "true");
                intent.putExtra("aspectX",1);
                intent.putExtra("aspectY",1);
                intent.putExtra("outputX", 80);
                intent.putExtra("outputY", 80);
                intent.putExtra("return-data",true);*/
                Intent localIntent = new Intent();
                localIntent.setType("image/*");
                localIntent.setAction("android.intent.action.GET_CONTENT");
                Intent intent = Intent.createChooser(localIntent, "选择图片");
                startActivityForResult(intent, 11);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10 && resultCode == Activity.RESULT_OK) {
            this.mImageView.setImageDrawable(Drawable.createFromPath(new File(
                    Environment.getExternalStorageDirectory(), "camera.jpg")
                    .getAbsolutePath()));
            System.out.println("data-->"+data);
        }else if (requestCode == 11 && resultCode ==Activity.RESULT_OK) {
            Uri uri=data.getData();
            if(uri!=null){
                try {
                    bitMap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                }catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                //int scale = ImageThumbnail.reckonThumbnail(bitMap.getWidth(), bitMap.getHeight(), 500, 600);
                //bitMap = ImageThumbnail.PicZoom(bitMap, (int) (bitMap.getWidth() / scale), (int) (bitMap.getHeight() / scale));
                mImageView.setImageBitmap(bitMap);
            }
            System.out.println("data2-->"+data);
        }
    }
}
