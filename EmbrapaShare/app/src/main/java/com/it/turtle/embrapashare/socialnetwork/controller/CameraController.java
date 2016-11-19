package com.it.turtle.embrapashare.socialnetwork.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CameraController {

    public static int REQUEST_IMAGE_CAPTURE = 1268;

    private static String DIRECTORY_NAME;
    private static String IMAGE_PREFIX;
    private Context mContext;
    private File mStorageDir;
    private String imageFileName;

    private String mCurrentImagePath = "";

    public CameraController (Context context, String directoryName, String imagePrefix) {
        DIRECTORY_NAME = directoryName + "/";
        IMAGE_PREFIX = imagePrefix;
        mContext = context;

        mStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), DIRECTORY_NAME);
        if (!mStorageDir.exists()) {
            if (!mStorageDir.mkdirs()) {
                mStorageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
            }
        }
    }

    public void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
            File photoFile;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                return;
            }

            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

            if(mContext instanceof Activity) {
                ((Activity) mContext).startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        imageFileName = IMAGE_PREFIX + "_" + timeStamp + "_";

        File image =  File.createTempFile(
                imageFileName,
                ".jpg",
                mStorageDir
        );

        mCurrentImagePath = image.getAbsolutePath();
        return image;
    }

    public String getDirectoryName() {
        return  DIRECTORY_NAME;
    }

    public String getImagePath () {
        return mCurrentImagePath;
    }

    public static void loadFileToImageView (String imagePath, ImageView imageView) {
        Glide.with(imageView.getContext()).load(new File(imagePath)).into(imageView);
    }

    public String getImageFileName() {
        return imageFileName + ".jpg";
    }
}
