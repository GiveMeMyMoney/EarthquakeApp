package com.example.styczen.marcin.earthquakeapp.android.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.adapters.EarthquakeRecyclerViewAdapter;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.android.receivers.EarthquakeDownloadReceiver;
import com.example.styczen.marcin.earthquakeapp.android.services.EarthquakeDownloadService;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.DbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.interfaces.IDbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;

import java.util.ArrayList;
import java.util.List;

import static com.example.styczen.marcin.earthquakeapp.utils.Utils.checkListValid;

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
    private static final String LOG_TAG = AllEarthquakeFragment.class.getSimpleName();

    private static final String ARG_EARTHQUAKE_LIST = "earthquake-list";
    private List<Earthquake> earthquakeList = new ArrayList<>();
    private EarthquakeRecyclerViewAdapter mAdapter;
    private OnListFragmentInteractionListener mListener;

    private IDbEarthquakeManager earthquakeService;

    private View messageContainer;
    private RecyclerView recyclerView;

    private EarthquakeDownloadReceiver earthquakeDownloadReceiver;

    //VIEW
    private SwipeRefreshLayout swipeRefreshLayout;


    //region Construct

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllEarthquakeFragment() {
        try {
            earthquakeDownloadReceiver = new EarthquakeDownloadReceiver() {
                @Override
                protected void onReceiveSend(String JSON) {
                    Toast.makeText(getContext(), JSON.toString(), Toast.LENGTH_LONG).show();

                    swipeRefreshLayout.setRefreshing(false);
                }
            };
            earthquakeService = DbEarthquakeManager.getEarthquakeService(getContext());
        } catch (DataBaseException e) {
            //TODO ErrorDialog
            e.printStackTrace();
        }
    }

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

        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        setSwipeRefreshLayout();

        messageContainer = rootView.findViewById(R.id.add_favorites_msg);
        setMessageContainerVisibility();

        // Set the adapter
        //TODO refactor
        recyclerView = (RecyclerView) rootView.findViewById(R.id.earthquake_list);
        if (recyclerView != null) {
            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            llm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(llm);
            recyclerView.setHasFixedSize(true);

            mAdapter = new EarthquakeRecyclerViewAdapter(earthquakeList, mListener);
            recyclerView.setAdapter(mAdapter);
        }
        return rootView;
    }

    private void setSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeColors(Color.BLUE, Color.RED, Color.GREEN, Color.BLACK);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.d(LOG_TAG, "swipeRefreshLayout: onRefresh");
                doService();
            }
        });
    }

    private void doService() {
        Intent i = new Intent(getContext(), EarthquakeDownloadService.class);
        getContext().startService(i);
        Log.e(LOG_TAG, "start Service ------------------------------------------");
    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPause() {
        super.onPause();
        getContext().unregisterReceiver(earthquakeDownloadReceiver);
    }

    @Override
    public void onResume() {
        super.onResume();
        getContext().registerReceiver(earthquakeDownloadReceiver, new IntentFilter(EarthquakeDownloadService.NOTIFICATION));
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    //endregion Construct
    private void setMessageContainerVisibility() {
        if (checkListValid(earthquakeList)) {
            messageContainer.setVisibility(View.GONE);
        } else {
            messageContainer.setVisibility(View.VISIBLE);
        }
    }

    public void changeContentEarthquakeList(List<Earthquake> eList) {
        if (checkListValid(eList)) {
            earthquakeList = eList;
        } else {
            earthquakeList = new ArrayList<>();
        }
        mAdapter.changeDataListAndNotify(earthquakeList);
        setMessageContainerVisibility();
    }


}
