package com.example.styczen.marcin.earthquakeapp.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.adapters.EarthquakeRecyclerViewAdapter;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AllEarthquakeFragment extends Fragment {
    private static final String ARG_EARTHQUAKE_LIST = "earthquake-list";
    private List<Earthquake> earthquakeList = new ArrayList<>();
    private EarthquakeRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllEarthquakeFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static AllEarthquakeFragment newInstance(List<Earthquake> earthquakeList) {
        AllEarthquakeFragment fragment = new AllEarthquakeFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_EARTHQUAKE_LIST, new ArrayList<>(earthquakeList));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            earthquakeList = getArguments().getParcelableArrayList(ARG_EARTHQUAKE_LIST);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_all_earthquake, container, false);

        // Set the adapter
        //TODO refactor
        if (rootView instanceof RecyclerView) {
            Context context = rootView.getContext();
            RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.earthquake_list);

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);

            recyclerView.setHasFixedSize(true);

            mAdapter = new EarthquakeRecyclerViewAdapter(earthquakeList, mListener);
            recyclerView.setAdapter(new EarthquakeRecyclerViewAdapter(earthquakeList, mListener));

            prepareEarthquakeData();
        }
        return rootView;
    }

    private void prepareEarthquakeData() {
        Earthquake earthquake = new Earthquake("Mad Max: Fury Road", "Action & Adventure", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Inside Out", "Animation, Kids & Family", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Star Wars: Episode VII - The Force Awakens", "Action", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Shaun the Sheep", "Animation", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("The Martian", "Science Fiction & Fantasy", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Mission: Impossible Rogue Nation", "Action", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Up", "Animation", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Star Trek", "Science Fiction", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("The LEGO Movie", "Animation", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Iron Man", "Action & Adventure", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Aliens", "Science Fiction", 1986.0);
        earthquakeList.add(earthquake);

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
