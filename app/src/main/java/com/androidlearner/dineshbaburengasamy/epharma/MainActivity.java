package com.androidlearner.dineshbaburengasamy.epharma;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity implements UploadPrescriptionButton.OnFragmentInteractionListener, UploadClickable, ProceedClickable, ProceedButton.OnProceedInteractionListener, AddInfo.OnFragmentInteractionListener{

    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    private Uri fileUri;
    private ImageView iv;
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private UploadPrescriptionButton uploadButton;
    private ProceedButton proceedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //UploadPrescriptionButton uploadButton = UploadPrescriptionButton.newInstance(fileUri.getPath(), CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);

        //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
        uploadButton = new UploadPrescriptionButton();
        /*
        Bundle args = new Bundle();
        args.putString(UploadPrescriptionButton.FILE_URI_KEY, fileUri.getPath());
        args.putInt(UploadPrescriptionButton.CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        uploadButton.setArguments(args);
        //http://stackoverflow.com/questions/9245408/best-practice-for-instantiating-a-new-android-fragment
        */
        //http://developer.android.com/training/basics/fragments/fragment-ui.html
        if(!(findViewById(R.id.main_container)==null)) {
            if(savedInstanceState==null)
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.main_container, uploadButton)
                        .commit();
        }

        iv=(ImageView)findViewById(R.id.presImageView);
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

    @Override
    public void onFragmentInteraction(Uri fileUri, int requestCode, int resultCode) {

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
                iv.setImageBitmap(bmp);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                proceedButton = new ProceedButton();
                ft.replace(R.id.main_container, proceedButton);
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


    public void uploadPrescriptionClicked(View v){
        uploadPrescriptionClickedIM();
    }

    public void proceedClicked(View v){
        onProceedClickedIM();
    }

    @Override
    public void uploadPrescriptionClickedIM(){
        uploadButton.uploadPrescriptionClickedIM();
    }

    @Override
    public void onProceedClickedIM(){
        proceedButton.onProceedClickedIM();
    }
    //http://developer.android.com/guide/topics/media/camera.html#saving-media
    /** Create a file Uri for saving an image or video */

    @Override
    public void onProceedInteraction(){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      //  ft.remove(getSupportFragmentManager().findFragmentById(R.id.uploadButtonContainer));
      //  ft.remove(getSupportFragmentManager().findFragmentById(R.id.proceedButtonContainer));
      //  ft.replace(R.id.main_container, new AddInfo());
        ft.replace(R.id.main_container, new AddInfo());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void addInfo(Uri uri){}

}
