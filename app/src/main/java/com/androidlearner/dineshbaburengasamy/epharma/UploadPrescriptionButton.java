package com.androidlearner.dineshbaburengasamy.epharma;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UploadPrescriptionButton.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UploadPrescriptionButton#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UploadPrescriptionButton extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String FILE_URI_KEY = "fileUri";
   // private static final String MEDIA_TYPE_KEY = "mediaType";
    private static final String CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY = "imageActivityRequestCode";
    private Button b1;

    // TODO: Rename and change types of parameters
    private Uri fileUri;
  //  private String mediaType;
    private int imageRequestCode;

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
        args.putString(FILE_URI_KEY, param1);
        //args.putString(MEDIA_TYPE_KEY, param2);
        args.putInt(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public UploadPrescriptionButton() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            fileUri = Uri.fromFile(new File(getArguments().getString(FILE_URI_KEY)));
            //mediaType = getArguments().getString(MEDIA_TYPE_KEY);
            imageRequestCode = getArguments().getInt(CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_upload_prescription_button, container, false);
        b1=(Button)view.findViewById(R.id.upldPresButton);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //http://developer.android.com/training/camera/photobasics.html
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, imageRequestCode);
                }
                //http://developer.android.com/training/camera/photobasics.html
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MainActivity ma = (MainActivity)getActivity();
        ma.postImageUpload(requestCode,resultCode);
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
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
        public void onFragmentInteraction(Uri uri);
    }

}
