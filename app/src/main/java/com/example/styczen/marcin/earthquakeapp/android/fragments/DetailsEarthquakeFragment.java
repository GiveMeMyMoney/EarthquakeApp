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
import android.widget.TextView;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.google.android.gms.plus.PlusOneButton;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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

    private static final int PLUS_ONE_REQUEST_CODE = 0;
    private final String PLUS_ONE_URL = "http://developer.android.com";

    //region VIEW
    private PlusOneButton mPlusOneButton;
    private FancyButton seeMoreButton;

    //endregion VIEW

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_earthquake, container, false);

        mPlusOneButton = (PlusOneButton) view.findViewById(R.id.plus_one_button);
        seeMoreButton = (FancyButton) view.findViewById(R.id.btn_see_more);
        seeMoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seeMoreDetailsBtnCLick();
            }
        });

        TextView geometryTV = (TextView) view.findViewById(R.id.geometry_tv);
        geometryTV.setText(earthquake.getGeometry().toString());
        TextView typeTV = (TextView) view.findViewById(R.id.type_tv);
        typeTV.setText(earthquake.getType());
        TextView titleTV = (TextView) view.findViewById(R.id.title_tv);
        titleTV.setText(earthquake.getTitle());
        TextView placeTV = (TextView) view.findViewById(R.id.place_tv);
        placeTV.setText(earthquake.getPlace());
        TextView codeTV = (TextView) view.findViewById(R.id.code_tv);
        codeTV.setText(earthquake.getCode());
        TextView magnitudeTV = (TextView) view.findViewById(R.id.magnitude_tv);
        magnitudeTV.setText(String.valueOf(earthquake.getMagnitude()) + Earthquake.MAGNITUDE_UNIT);

        return view;
    }

    private void seeMoreDetailsBtnCLick() {
        try {
            URL u = new URL(earthquake.getUrlDetail());
            String urlDetails = u.toURI().toString();
            Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlDetails));
            startActivity(myIntent);
        } catch (URISyntaxException | MalformedURLException e) {
            Toast.makeText(getContext(), "Invalid URL: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } catch (ActivityNotFoundException e) {
            Toast.makeText(getContext(), "No application can handle this request." + " Please install a webbrowser", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
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
}
