package com.androidlearner.dineshbaburengasamy.epharma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity {

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private ImageView iv;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
        UploadPrescriptionButton uploadButton = UploadPrescriptionButton.newInstance(fileUri.getPath(), CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        /*
        //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
        MyFragment myFragment = new MyFragment();

        Bundle args = new Bundle();
        args.putInt("someInt", someInt);
        myFragment.setArguments(args);

        return myFragment;
        //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
        */
        //http://developer.android.com/training/basics/fragments/fragment-ui.html
        if(!(findViewById(R.id.main_container)==null)) {
            if(!(savedInstanceState==null))
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, uploadButton)
                        .commit();
        }

        //iv=(ImageView)findViewById(R.id.presImageView);
        //http://developer.android.com/training/basics/fragments/fragment-ui.html

        /*
        //http://developer.android.com/guide/topics/media/camera.html
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name

        // start the image capture Intent
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        //http://developer.android.com/guide/topics/media/camera.html
        */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    /*private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }*/
    public void postImageUpload(int requestCode, int resultCode) {

        //super.onActivityResult(requestCode, resultCode, data);

      //  Bitmap bp = (Bitmap) data.getExtras().get("data");
        //iv.setImageBitmap(bp);
        Log.v(LOG_TAG,"Inside onactivityresult");
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Image captured and saved to fileUri specified in the Intent
                /*Toast.makeText(this, "Image saved to:\n" +
                        data.getData(), Toast.LENGTH_LONG).show();
                Log.v(LOG_TAG,"Image saved to:\n" +
                        data.getData());*/
                Toast.makeText(this, "Image saved to:\n" +
                        fileUri.getPath(), Toast.LENGTH_LONG).show();
                Log.v(LOG_TAG,"Image saved to:\n" +
                        fileUri.getPath());
               /*
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iv.setImageBitmap(imageBitmap);
               */
                //http://stackoverflow.com/questions/6224710/set-imageview-to-show-image-in-sdcard
                Bitmap bmp = BitmapFactory.decodeFile(fileUri.getPath());
                //iv.setImageBitmap(bmp);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.main_container, new AddInfo());
                ft.addToBackStack(null);
                ft.commit();
                //http://stackoverflow.com/questions/6224710/set-imageview-to-show-image-in-sdcard
                //refer http://stackoverflow.com/questions/9890757/android-camera-data-intent-returns-null
            } else if (resultCode == RESULT_CANCELED) {
                Log.v(LOG_TAG,"Image capture cancelled");
            } else {
                Log.v(LOG_TAG,"Image capture failed");
            }
        }

        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
               // Toast.makeText(this, "Video saved to:\n" +
                   //     data.getData(), Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }

    //http://developer.android.com/guide/topics/media/camera.html#saving-media
    /** Create a file Uri for saving an image or video */
    private Uri getOutputMediaFileUri(int type){
        Log.v(LOG_TAG, Uri.fromFile(getOutputMediaFile(type)).toString());
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), getString(R.string.app_name));
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
