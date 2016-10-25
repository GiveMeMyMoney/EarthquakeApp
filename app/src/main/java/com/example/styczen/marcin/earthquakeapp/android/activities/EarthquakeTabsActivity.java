package com.example.styczen.marcin.earthquakeapp.android.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.AllEarthquakeFragment;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.DbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.dbManager.interfaces.IDbEarthquakeManager;
import com.example.styczen.marcin.earthquakeapp.core.Earthquake;
import com.example.styczen.marcin.earthquakeapp.database.DBAdapter;
import com.example.styczen.marcin.earthquakeapp.exceptions.DataBaseException;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Marcin on 2016-10-13.
 */

public class EarthquakeTabsActivity extends AppCompatActivity implements OnListFragmentInteractionListener {
    private static final String LOG_TAG = EarthquakeTabsActivity.class.getSimpleName();
    //TODO res/layout-sw600dp
    //TODO dla LAND mniejszy bottombar
    //TODO dodac LOGI!
    //TODO Exceptions
    //TODO sortowanie
    //TODO robic pushe
    //TODO brzydki pasek u dołu
    //TODO paczki services, receivers...

    private AllEarthquakeFragment fragment;
    private List<Earthquake> earthquakeList;
    private DBAdapter dbAdapter;
    private IDbEarthquakeManager earthquakeManager;
    private BottomBar bottomBar;
    //private EarthquakeDownloadReceiver earthquakeDownloadReceiver;

    public EarthquakeTabsActivity() {
        earthquakeList = new ArrayList<>();
        try {
            earthquakeManager = DbEarthquakeManager.getEarthquakeManager(this);
            //earthquakeDownloadReceiver = new EarthquakeDownloadReceiver();
        } catch (DataBaseException e) {
            //TODO ErrorDialog
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake_tabs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        boolean dataBase = createDataBase();

        //TODO refac
        FragmentManager fm = getSupportFragmentManager();
        fragment = (AllEarthquakeFragment) fm.findFragmentById(R.id.contentContainer);
        if (fragment == null) {
            FragmentTransaction ft = fm.beginTransaction();
            fragment = AllEarthquakeFragment.newInstance(earthquakeList);
            ft.add(R.id.contentContainer, fragment);
            ft.commit();
        }

        //TODO onCreateView
        bottomBar = (BottomBar) findViewById(R.id.bottom_bar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    protected void onStart() {
        super.onStart();
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.tab_earthquakes) {
                    fragment.getEarthquakesFromAPIByService();
                }
                if (tabId == R.id.tab_contributors) {
                    fragment.getEarthquakesFromAPIByService(); //TODO
                }
                if (tabId == R.id.tab_favorites) {
                    //TODO problem leci ladowanie listy z API gdy sie szybko przekliknie
                    getFavoriteEarthquakesFromDB();
                    fragment.changeContentEarthquakeList(earthquakeList);
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //registerReceiver(earthquakeDownloadReceiver, new IntentFilter(EarthquakeDownloadService.NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        //unregisterReceiver(earthquakeDownloadReceiver);
    }

    private boolean createDataBase() {
        this.dbAdapter = DBAdapter.getDbAdapter(getApplicationContext());
        return true;
    }

    /*private void prepareEarthquakeData1() {
        earthquakeList = new ArrayList<>();
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
    }

    private void prepareEarthquakeData2() {
        earthquakeList = new ArrayList<>();
        Earthquake earthquake = new Earthquake("Mad Max: Fury Road", "Action & Adventure", 1986.0);
        earthquakeList.add(earthquake);

        earthquake = new Earthquake("Inside Out", "Animation, Kids & Family", 1986.0);
        earthquakeList.add(earthquake);
    }*/

    private void getFavoriteEarthquakesFromDB() {
        try {
            //TODO on change
            earthquakeList = earthquakeManager.selectAll();
        } catch (DataBaseException e) {
            //TODO
            e.printStackTrace();
        }
    }

    /**
     * After click on card_list with earthquake
     * @param earthquake
     */
    @Override
    public void onListFragmentInteraction(Earthquake earthquake) {
        Intent detailsIntent = DetailsEarthquakeActivity.getStartActivityIntent(this, earthquake);
        startActivity(detailsIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_earthquake_tabs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                //doService();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
