package com.androidlearner.dineshbaburengasamy.epharma;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {link UploadPrescriptionButton.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadPrescriptionButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPrescriptionButton extends Fragment implements UploadClickable {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
   // private static final String FILE_URI_KEY = "fileUri";//uncomment for new instance
   // private static final String MEDIA_TYPE_KEY = "mediaType";
   // public static final String CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY = "imageActivityRequestCode"; //uncomment for new instance
    public static final String LOG_TAG = UploadPrescriptionButton.class.getSimpleName();
    private Button b1;

    // TODO: Rename and change types of parameters
    private Uri fileUri;
  //  private String mediaType;
    private int imageRequestCode = 100; //need to be changed as static
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UploadPrescriptionButton.
     */
    // TODO: Rename and change types and number of parameters
    public static UploadPrescriptionButton newInstance(String param1, int param2) {
        UploadPrescriptionButton fragment = new UploadPrescriptionButton();
        Bundle args = new Bundle();
       // args.putString(FILE_URI_KEY, param1); //uncomment for new instance
        //args.putString(MEDIA_TYPE_KEY, param2);
       // args.putInt(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY, param2);//uncomment for new instance
        fragment.setArguments(args);
        Log.v(LOG_TAG, "inside new instance set");
        return fragment;
    }

    public UploadPrescriptionButton() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // fileUri = Uri.fromFile(new File(getArguments().getString(FILE_URI_KEY))); //uncomment for new instance
            //mediaType = getArguments().getString(MEDIA_TYPE_KEY);
           // imageRequestCode = getArguments().getInt(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY); //uncomment for new instance
          //  Log.v(LOG_TAG, "inside oncreate arguements set, arguements: " + fileUri + ", " + imageRequestCode);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_prescription_button, container, false);
        b1=(Button)view.findViewById(R.id.upldPresButton);
       /* b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://developer.android.com/training/camera/photobasics.html
                fileUri = CreateFileInExt.getOutputMediaFileUri(MediaType.MEDIA_TYPE_IMAGE, getActivity());
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Log.v(LOG_TAG, "click working?");
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
              //  if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, imageRequestCode);
              //  }
                //http://developer.android.com/training/camera/photobasics.html
            }
        }); */
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(mListener!=null)
            mListener.onFragmentInteraction(fileUri, requestCode, resultCode);
    }



    // TODO: Rename method, update argument and hook method into UI event
  /*  public void uploadPrescriptionClicked() {
        //if (mListener != null) {
       //     mListener.onFragmentInteraction(uri);
       // }
        //http://developer.android.com/training/camera/photobasics.html
        fileUri = CreateFileInExt.getOutputMediaFileUri(MediaType.MEDIA_TYPE_IMAGE, getActivity());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.v(LOG_TAG, "click working?");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
          if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, imageRequestCode);
          }
        //http://developer.android.com/training/camera/photobasics.html
    }*/

    @Override
    public void uploadPrescriptionClickedIM(){
        fileUri = CreateFileInExt.getOutputMediaFileUri(MediaType.MEDIA_TYPE_IMAGE, getActivity());
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Log.v(LOG_TAG, "click working?");
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, imageRequestCode);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
   public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri, int requestCode, int ResultCode);
    }


}
