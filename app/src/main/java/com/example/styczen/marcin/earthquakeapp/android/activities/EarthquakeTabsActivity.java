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
import android.widget.Toast;

import com.example.styczen.marcin.earthquakeapp.R;
import com.example.styczen.marcin.earthquakeapp.android.fragments.AllEarthquakeFragment;
import com.example.styczen.marcin.earthquakeapp.android.listeners.OnListFragmentInteractionListener;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.EarthquakeService;
import com.example.styczen.marcin.earthquakeapp.businessLogicLayer.interfaces.IEartquakeService;
import com.example.styczen.marcin.earthquakeapp.core.cos.Earthquake;
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
    //TODO res/layout-sw600dp
    //TODO dla LAND mniejszy bottombar
    //TODO dodac LOGI!
    //TODO Exceptions
    //TODO sortowanie
    //TODO robic pushe
    //TODO brzydki pasek u dołu
    //TODO .gitignore

    private AllEarthquakeFragment fragment;
    private List<Earthquake> earthquakeList;
    private DBAdapter dbAdapter;
    private IEartquakeService earthquakeService;
    private BottomBar bottomBar;

    public EarthquakeTabsActivity() {
        earthquakeList = new ArrayList<>();
        try {
            earthquakeService = EarthquakeService.getEarthquakeService(this);
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

        prepareEarthquakeData1();
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
                    Toast.makeText(EarthquakeTabsActivity.this, "1", Toast.LENGTH_SHORT).show();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    prepareEarthquakeData1();
                    //
                    fragment.changeContentEarthquakeList(earthquakeList);
                }
                if (tabId == R.id.tab_contributors) {
                    Toast.makeText(EarthquakeTabsActivity.this, "2", Toast.LENGTH_SHORT).show();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    prepareEarthquakeData2();
                    //
                    fragment.changeContentEarthquakeList(earthquakeList);
                }
                if (tabId == R.id.tab_favorites) {
                    Toast.makeText(EarthquakeTabsActivity.this, "3", Toast.LENGTH_SHORT).show();
                    // The tab with id R.id.tab_favorites was selected,
                    // change your content accordingly.
                    prepareEarthquakeData3();
                    //
                    fragment.changeContentEarthquakeList(earthquakeList);
                }
            }
        });
    }

    private boolean createDataBase() {
        this.dbAdapter = DBAdapter.getDbAdapter(getApplicationContext());
        return true;
    }

    private void prepareEarthquakeData1() {
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
    }

    private void prepareEarthquakeData3() {
        try {
            //TODO on change
            earthquakeList = earthquakeService.selectAll();
        } catch (DataBaseException e) {
            //TODO
            e.printStackTrace();
        }
    }

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

}
