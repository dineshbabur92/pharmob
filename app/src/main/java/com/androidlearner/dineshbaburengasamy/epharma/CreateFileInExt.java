package com.androidlearner.dineshbaburengasamy.epharma;

import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by dineshbabu.rengasamy on 7/17/2015.
 */
public class CreateFileInExt {

    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;

    private static final String LOG_TAG = CreateFileInExt.class.getSimpleName();
    public static Uri getOutputMediaFileUri(int type, Context context){
        Log.v(LOG_TAG, Uri.fromFile(getOutputMediaFile(type, context)).toString());
        return Uri.fromFile(getOutputMediaFile(type, context));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type, Context context){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), context.getString(R.string.app_name));
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d(LOG_TAG, "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
    }
    //http://developer.android.com/guide/topics/media/camera.html#saving-media
}
