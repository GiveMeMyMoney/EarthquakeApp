package com.example.styczen.marcin.earthquakeapp.android.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.google.android.gms.plus.PlusOneButton;

import mehdi.sakout.fancybuttons.FancyButton;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link DetailsEarthquakeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DetailsEarthquakeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsEarthquakeFragment extends Fragment implements View.OnClickListener {
    private static final String ARG_EARTHQUAKE = "earthquake";

    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";

    //VIEW
    private PlusOneButton mPlusOneButton;
    private FancyButton seeMoreButton;


    private OnFragmentInteractionListener mListener;

    private Earthquake earthquake;

    public DetailsEarthquakeFragment() {
        // Required empty public constructor
    }

    public static DetailsEarthquakeFragment newInstance(Earthquake earthquake) {
        DetailsEarthquakeFragment fragment = new DetailsEarthquakeFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_EARTHQUAKE, earthquake);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            earthquake = getArguments().getParcelable(ARG_EARTHQUAKE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_details_earthquake, container, false);

       /* FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        //Find the +1 button
        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
        seeMoreButton = (FancyButton) view.findViewById(R.id.btn_see_more);
        seeMoreButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and title
        void onFragmentInteraction(Uri uri);
    }

    /*SMIECI<*/
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_see_more:
                seeMoreDetailsBtnCLick();
        }
    }

    private void seeMoreDetailsBtnCLick() {
        try {
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
            startActivity(myIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request."
                    + " Please install a webbrowser",  Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
